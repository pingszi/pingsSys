import { routerRedux } from 'dva/router';
import { stringify } from 'qs';
import { accountLogin, logout } from '@/services/login';
import { setAuthority, removeAuthorization } from '@/utils/authority';
import { getPageQuery } from '@/utils/utils';
import { reloadAuthorized } from '@/utils/Authorized';

export default {
  namespace: 'login',

  state: {
    status: undefined,
  },

  effects: {
    *login({ payload }, { call, put }) {
      const response = yield call(accountLogin, payload);
      yield put({
        type: 'changeLoginStatus',
        payload: { ...response, status: response.code === 200 ? true : false },
      });
      // Login successfully
      if (response.code === 200) {
        reloadAuthorized();

        const urlParams = new URL(window.location.href);
        const params = getPageQuery();
        let { redirect } = params;
        if (redirect) {
          const redirectUrlParams = new URL(redirect);
          if (redirectUrlParams.origin === urlParams.origin) {
            redirect = redirect.substr(urlParams.origin.length);
            if (redirect.match(/^\/.*#/)) {
              redirect = redirect.substr(redirect.indexOf('#') + 1);
            }
          } else {
            window.location.href = redirect;
            return;
          }
        }
        yield put(routerRedux.replace(redirect || '/'));
      }
    },

    *logout(_, { call, put }) {
      yield call(logout);
      yield put({
        type: 'changeLoginStatus',
        payload: {
          status: false,
        },
      });

      reloadAuthorized();
      //**清空token权限标记
      removeAuthorization();

      yield put(
        routerRedux.push({
          pathname: '/user/login',
          search: stringify({
            redirect: window.location.href,
          }),
        })
      );
    },
  },

  reducers: {
    changeLoginStatus(state, { payload }) {
      const currentAuthority = payload.status ? payload.data : 'guest';
      setAuthority(currentAuthority);

      return {
        ...state,
        status: payload.status ? 'ok' : payload.code ? 'error' : '',
      };
    },
  },
};
