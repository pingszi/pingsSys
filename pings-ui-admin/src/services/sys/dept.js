import request from '@/utils/request';

/**
 *********************************************************
 * @desc ： 查询所有部门
 * @author Pings
 * @date   2019/1/20
 * @return ApiResponse
 * *******************************************************
 */
export async function queryAll(params) {
  return request('/api/dept/findAll', params);
}

/**
 *********************************************************
 * @desc ： 验证部门编码是否唯一
 * @author Pings
 * @date   2019/1/18
 * @param  code  部门编码
 * @return ApiResponse
 * *******************************************************
 */
export async function validateCodeUnique(code) {
  return request(`/api/dept/validateCodeUnique/${code}`);
}

/**
 *********************************************************
 * @desc ： 保存
 * @author Pings
 * @date   2019/1/18
 * @return ApiResponse
 * *******************************************************
 */
export async function save(params) {
  return request('/api/dept/save', {
    method: 'POST',
    body: params,
  });
}

/**
 *********************************************************
 * @desc ： 根据id删除
 * @author Pings
 * @date   2019/2/22
 * @param  id  编号
 * @return ApiResponse
 * *******************************************************
 */
export async function deleteById(id) {
  return request(`/api/dept/deleteById/${id}`, {
    method: 'DELETE',
  });
}
