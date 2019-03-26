import request from '@/utils/request';

/**
 *********************************************************
 * @desc ： 查询基础数据列表
 * @author Pings
 * @date   2019/3/26
 * @return ApiResponse
 * *******************************************************
 */
export async function queryList(params) {
  return request('/bill/basData/list', params);
}

/**
 *********************************************************
 * @desc ： 根据类型查询基础数据
 * @author Pings
 * @date   2019/3/26
 * @param  type  类型
 * @return ApiResponse
 * *******************************************************
 */
export async function queryByType(type) {
  return request(`/bill/basData/findByType/${type}`);
}

/**
 *********************************************************
 * @desc ： 验证编码是否唯一
 * @author Pings
 * @date   2019/3/26
 * @param  code  用户名称
 * @return ApiResponse
 * *******************************************************
 */
export async function validateCodeUnique(code) {
  return request(`/bill/basData/validateCodeUnique/${code}`);
}

/**
 *********************************************************
 * @desc ： 保存
 * @author Pings
 * @date   2019/3/26
 * @return ApiResponse
 * *******************************************************
 */
export async function save(params) {
  return request('/bill/basData/save', {
    method: 'POST',
    body: params,
  });
}

/**
 *********************************************************
 * @desc ： 根据id删除
 * @author Pings
 * @date   2019/3/26
 * @param  id  编号
 * @return ApiResponse
 * *******************************************************
 */
export async function deleteById(id) {
  return request(`/bill/basData/deleteById/${id}`, {
    method: 'DELETE',
  });
}
