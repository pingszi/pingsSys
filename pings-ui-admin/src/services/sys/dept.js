import request from '@/utils/request';

/**
 *********************************************************
 * @desc ： 查询所有部门
 * @author Pings
 * @date   2019/1/20
 * @return ApiResponse
 * *******************************************************
 */
export async function queryAll() {
  return request('/api/dept/findAll');
}

/**
 *********************************************************
 * @desc ： 查询部门列表
 * @author Pings
 * @date   2019/2/20
 * @return ApiResponse
 * *******************************************************
 */
export async function queryList() {
  return request('/api/dept/list');
}
