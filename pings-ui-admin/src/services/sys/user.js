import request from '@/utils/request';

//**查询用户列表*/
export async function queryList(params) {
  return request('/api/user/list', params);
}

//**查询当前用户*/
export async function queryCurrent() {
  return request('/api/user/currentUser');
}
