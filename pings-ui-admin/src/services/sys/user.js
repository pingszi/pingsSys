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
