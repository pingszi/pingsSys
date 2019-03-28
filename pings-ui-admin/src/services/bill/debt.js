import request from '@/utils/request';

/**
 *********************************************************
 * @desc ： 查询欠款单列表
 * @author Pings
 * @date   2019/3/28
 * @return ApiResponse
 * *******************************************************
 */
export async function queryList(params) {
  return request('/bill/debt/list', params);
}

/**
 *********************************************************
 * @desc ： 查询没有还清的欠款单
 * @author Pings
 * @date   2019/3/28
 * @return ApiResponse
 * *******************************************************
 */
export async function queryAllNotRefundDebt() {
  return request('/bill/debt/findAllNotRefundDebt');
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
  return request('/bill/debt/save', {
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
  return request(`/bill/debt/deleteById/${id}`, {
    method: 'DELETE',
  });
}
