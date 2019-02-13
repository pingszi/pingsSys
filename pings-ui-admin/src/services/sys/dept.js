import request from '@/utils/request';

//**查询所有部门*/
export async function queryAll() {
  return request('/api/dept/findAll');
}
