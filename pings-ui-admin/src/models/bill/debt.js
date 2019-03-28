import { queryList, save, deleteById, queryAllNotRefundDebt } from '@/services/bill/debt';

import { notification } from 'antd';

export default {
  namespace: 'debt',

  state: {
    data: {
      list: [],
      pagination: {},
    },

    allNotRefundDebt: [], //**没有还清的欠款单
  },

  effects: {
    /**查询列表 */
    *fetch({ payload }, { call, put }) {
      const response = yield call(queryList, payload);
      yield put({
        type: 'save',
        payload: response.data,
      });
    },
    /**查询没有还清的欠款单*/
    *fetchAllNotRefundDebt(_, { call, put }) {
      const response = yield call(queryAllNotRefundDebt);
      yield put({ type: 'saveAllNotRefundDebt', payload: response.data });
    },
    /**保存 */
    *saveObj({ payload, callback }, { call }) {
      const response = yield call(save, payload);
      if (response.code === 200) callback(response);
      else notification.error({ message: response.msg });
    },
    /**根据id删除*/
    *deleteById({ payload, callback }, { call }) {
      const response = yield call(deleteById, payload);
      if (response.code === 200) callback(response);
      else notification.error({ message: response.msg });
    },
  },

  reducers: {
    save(state, action) {
      return {
        ...state,
        data: action.payload,
      };
    },
    saveAllNotRefundDebt(state, action) {
      return {
        ...state,
        allNotRefundDebt: action.payload,
      };
    },
  },
};
