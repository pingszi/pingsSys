import { queryList, queryByType, save, deleteById } from '@/services/bill/basData';

import { notification } from 'antd';

export default {
  namespace: 'basData',

  state: {
    data: {
      list: [],
      pagination: {},
    },

    typeBasData: [], //**指定类型的基础数据
  },

  effects: {
    /**查询角色列表 */
    *fetch({ payload }, { call, put }) {
      const response = yield call(queryList, payload);
      yield put({
        type: 'save',
        payload: response.data,
      });
    },
    /**根据类型查询基础数据*/
    *fetchByType({ payload }, { call, put }) {
      const response = yield call(queryByType, payload);
      yield put({ type: 'saveTypeBasData', payload: response.data });
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
    saveTypeBasData(state, action) {
      return {
        ...state,
        typeBasData: action.payload,
      };
    },
  },
};
