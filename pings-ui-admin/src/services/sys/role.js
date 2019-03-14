import request from '@/utils/request';

/**
 *********************************************************
 * @desc ： 查询角色列表
 * @author Pings
 * @date   2019/3/8
 * @return ApiResponse
 * *******************************************************
 */
export async function queryList(params) {
  return request('/api/role/list', params);
}

/**
 *********************************************************
 * @desc ： 查询所有角色
 * @author Pings
 * @date   2019/3/12
 * @return ApiResponse
 * *******************************************************
 */
export async function queryAll() {
  return request('/api/role/findAll');
}

/**
 *********************************************************
 * @desc ： 根据用户id查询角色
 * @author Pings
 * @date   2019/3/12
 * @return ApiResponse
 * *******************************************************
 */
export async function queryByUserId(userId) {
  return request(`/api/role/findByUserId/${userId}`);
}

/**
 *********************************************************
 * @desc ： 验证编码是否唯一
 * @author Pings
 * @date   2019/3/8
 * @param  code  用户名称
 * @return ApiResponse
 * *******************************************************
 */
export async function validateCodeUnique(code) {
  return request(`/api/role/validateCodeUnique/${code}`);
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
  return request('/api/role/save', {
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
  return request(`/api/role/deleteById/${id}`, {
    method: 'DELETE',
  });
}

/**
 *********************************************************
 * @desc ： 分配权限
 * @author Pings
 * @date   2019/3/13
 * @return ApiResponse
 * *******************************************************
 */
export async function allotRight(params) {
  return request('/api/role/allotRight', {
    method: 'POST',
    body: params,
  });
}
