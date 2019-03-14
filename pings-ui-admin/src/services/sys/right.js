import request from '@/utils/request';

/**
 *********************************************************
 * @desc ： 查询所有权限
 * @author Pings
 * @date   2019/3/8
 * @return ApiResponse
 * *******************************************************
 */
export async function queryAll() {
  return request('/api/right/findAll');
}

/**
 *********************************************************
 * @desc ： 根据角色id查询权限
 * @author Pings
 * @date   2019/3/13
 * @return ApiResponse
 * *******************************************************
 */
export async function queryByRoleId(roleId) {
  return request(`/api/right/findByRoleId/${roleId}`);
}

/**
 *********************************************************
 * @desc ： 验证编码是否唯一
 * @author Pings
 * @date   2019/3/8
 * @param  code  编码
 * @return ApiResponse
 * *******************************************************
 */
export async function validateCodeUnique(code) {
  return request(`/api/right/validateCodeUnique/${code}`);
}

/**
 *********************************************************
 * @desc ： 保存
 * @author Pings
 * @date   2019/3/8
 * @return ApiResponse
 * *******************************************************
 */
export async function save(params) {
  return request('/api/right/save', {
    method: 'POST',
    body: params,
  });
}

/**
 *********************************************************
 * @desc ： 根据id删除
 * @author Pings
 * @date   2019/3/8
 * @param  id  编号
 * @return ApiResponse
 * *******************************************************
 */
export async function deleteById(id) {
  return request(`/api/right/deleteById/${id}`, {
    method: 'DELETE',
  });
}
