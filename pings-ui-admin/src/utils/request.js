import fetch from 'dva/fetch';
import { notification } from 'antd';
import router from 'umi/router';

import { setAuthorization, getAuthorization } from '@/utils/authority';

const codeMessage = {
  200: '服务器成功返回请求的数据。',
  201: '新建或修改数据成功。',
  202: '一个请求已经进入后台排队（异步任务）。',
  204: '删除数据成功。',
  400: '发出的请求有错误，服务器没有进行新建或修改数据的操作。',
  401: '用户没有权限（令牌、用户名、密码错误）。',
  403: '用户得到授权，但是访问是被禁止的。',
  404: '发出的请求针对的是不存在的记录，服务器没有进行操作。',
  406: '请求的格式不可得。',
  410: '请求的资源被永久删除，且不会再得到的。',
  422: '当创建一个对象时，发生一个验证错误。',
  500: '服务器发生错误，请检查服务器。',
  502: '网关错误。',
  503: '服务不可用，服务器暂时过载或维护。',
  504: '网关超时。',
};

const checkStatus = response => {
  if (response.status >= 200 && response.status < 300) {
    return response;
  }
  const errortext = codeMessage[response.status] || response.statusText;
  notification.error({
    message: `请求错误 ${response.status}: ${response.url}`,
    description: errortext,
  });
  const error = new Error(errortext);
  error.name = response.status;
  error.response = response;
  throw error;
};

/**
 *********************************************************
 ** @desc ： json对象转换成FormData，方便后台接收
 ** @author Pings
 ** @date   2019/1/26
 ** @param  json      需要转换的json对象
 ** @return FormData
 * *******************************************************
 */
const toFormData = json => {
  const formData = new FormData();
  Object.keys(json).forEach(key => {
    formData.append(key, json[key]);
  });
  return formData;
};

/**
 * Requests a URL, returning a promise.
 *
 * @param  {string} url       The URL we want to request
 * @param  {object} [option] The options we want to pass to "fetch"
 * @return {object}           An object containing either "data" or "err"
 */
export default function request(url, option) {
  /**
   * Produce fingerprints based on url and parameters
   * Maybe url has the same parameters
   */
  if (
    option &&
    (option.method === 'POST' || option.method === 'PUT' || option.method === 'DELETE')
  ) {
    //**转换参数为FormData */
    if (option.body && !(option.body instanceof FormData)) {
      option.body = toFormData(option.body);
    }
  } else if (option) {
    //**转换参数为test?a=1&b=2*/
    option =
      typeof option === 'object'
        ? Object.keys(option)
            .filter(key => option[key])
            .map(key => `${key}=${option[key]}`)
            .join('&')
        : option;
    url = `${url}?${option}`;
    option = null;
  }

  if (option) {
    option.headers = { Accept: 'application/json', ...option.headers };
  } else {
    option = { headers: { Accept: 'application/json' } };
  }

  //**添加权限标记token */
  let token = getAuthorization();
  if (token) option.headers.Authorization = token;

  return fetch(url, option)
    .then(checkStatus)
    .then(response => {
      token = response.headers.get('Authorization');
      if (token) {
        //**设置token权限标记
        setAuthorization(token);
      }

      return response.json();
    })
    .catch(e => {
      const status = e.name;
      if (status === 401) {
        // @HACK
        /* eslint-disable no-underscore-dangle */
        window.g_app._store.dispatch({
          type: 'login/logout',
        });
        return;
      }
      // environment should not be used
      if (status === 403) {
        router.push('/exception/403');
        return;
      }
      if (status <= 504 && status >= 500) {
        router.push('/exception/500');
        return;
      }
      if (status >= 404 && status < 422) {
        router.push('/exception/404');
      }
    });
}
