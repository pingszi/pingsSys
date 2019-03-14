import request from '@/utils/request';

/**
 *********************************************************
 * @desc ： 查询用户列表
 * @author Pings
 * @date   2019/1/18
 * @return ApiResponse
 * *******************************************************
 */
export async function queryList(params) {
  return request('/api/user/list', params);
}

/**
 *********************************************************
 * @desc ： 查询当前用户
 * @author Pings
 * @date   2019/1/18
 * @return ApiResponse
 * *******************************************************
 */
export async function queryCurrent() {
  return request('/api/user/currentUser');
}

/**
 *********************************************************
 * @desc ： 验证用户名称是否唯一
 * @author Pings
 * @date   2019/1/18
 * @param  userName  用户名称
 * @return ApiResponse
 * *******************************************************
 */
export async function validateUserNameUnique(userName) {
  return request(`/api/user/validateUserNameUnique/${userName}`);
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
  return request('/api/user/save', {
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
  return request(`/api/user/deleteById/${id}`, {
    method: 'DELETE',
  });
}

/**
 *********************************************************
 * @desc ： 分配角色
 * @author Pings
 * @date   2019/3/12
 * @return ApiResponse
 * *******************************************************
 */
export async function allotRole(params) {
  return request('/api/user/allotRole', {
    method: 'POST',
    body: params,
  });
}

/**
 *********************************************************
 * @desc ： 修改密码
 * @author Pings
 * @date   2019/3/13
 * @return ApiResponse
 * *******************************************************
 */
export async function updatePassword(params) {
  return request('/api/user/updatePassword', {
    method: 'POST',
    body: params,
  });
}
