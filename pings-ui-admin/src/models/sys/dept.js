import { queryAll, save, deleteById } from '@/services/sys/dept';

import { notification } from 'antd';

export default {
  namespace: 'dept',

  state: {
    allDepts: [],
  },

  effects: {
    /**查找所有部门 */
    *fetchAll({ payload }, { call, put }) {
      const response = yield call(queryAll, payload);
      yield put({
        type: 'saveAllDepts',
        payload: response.data,
      });
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
    saveAllDepts(state, action) {
      return {
        ...state,
        allDepts: action.payload,
      };
    },
  },
};
