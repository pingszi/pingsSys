import request from '@/utils/request';

//**登录*/
export async function accountLogin(params) {
  return request('/api/login/account', {
    method: 'POST',
    body: params,
  });
}

/**
 *********************************************************
 * @desc ： 登出
 * @author Pings
 * @date   2019/3/26
 * @return ApiResponse
 * *******************************************************
 */
export async function logout() {
  return request('/api/login/logout');
}
