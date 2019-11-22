import { notification } from 'antd';

import {
  queryList,
  save,
  deleteById,
  queryAllNotRefundDebt,
  queryAllRefundDebt,
} from '@/services/bill/debt';

export default {
  namespace: 'debt',

  state: {
    data: {
      list: [],
      pagination: {},
    },

    allNotRefundDebt: [], //**没有还清的欠款单
    allRefundDebt: [], //**所有的欠款单
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
    /**查询所有的欠款单*/
    *fetchAllRefundDebt(_, { call, put }) {
      const response = yield call(queryAllRefundDebt);
      yield put({ type: 'saveAllRefundDebt', payload: response.data });
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
    saveAllRefundDebt(state, action) {
      return {
        ...state,
        allRefundDebt: action.payload,
      };
    },
  },
};
