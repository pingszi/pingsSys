import { stringify } from 'qs';
import request from '@/utils/request';

//**登录*/
export async function accountLogin(params) {
  return request('/api/login/account', {
    method: 'POST',
    body: params,
  });
}
