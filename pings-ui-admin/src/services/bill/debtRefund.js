import request from '@/utils/request';

/**
 *********************************************************
 * @desc ： 查询还款单列表
 * @author Pings
 * @date   2019/3/28
 * @return ApiResponse
 * *******************************************************
 */
export async function queryList(params) {
  return request('/bill/debtRefund/list', params);
}

/**
 *********************************************************
 * @desc ： 保存
 * @author Pings
 * @date   2019/3/28
 * @return ApiResponse
 * *******************************************************
 */
export async function save(params) {
  return request('/bill/debtRefund/save', {
    method: 'POST',
    body: params,
  });
}

/**
 *********************************************************
 * @desc ： 根据id删除
 * @author Pings
 * @date   2019/3/28
 * @param  id  编号
 * @return ApiResponse
 * *******************************************************
 */
export async function deleteById(id) {
  return request(`/bill/debtRefund/deleteById/${id}`, {
    method: 'DELETE',
  });
}
