import {
  queryList,
  queryCurrent,
  save,
  deleteById,
  allotRole,
  updatePassword,
} from '@/services/sys/user';

import { notification } from 'antd';

export default {
  namespace: 'user',

  state: {
    data: {
      list: [],
      pagination: {},
    },
    currentUser: {},
  },

  effects: {
    /**查询用户列表 */
    *fetch({ payload }, { call, put }) {
      const response = yield call(queryList, payload);
      yield put({
        type: 'save',
        payload: response.data,
      });
    },
    /**获取当前用户 */
    *fetchCurrent(_, { call, put }) {
      const response = yield call(queryCurrent);
      yield put({
        type: 'saveCurrentUser',
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
    /**分配角色*/
    *allotRole({ payload, callback }, { call }) {
      const response = yield call(allotRole, payload);
      if (response.code === 200) callback(response);
      else notification.error({ message: response.msg });
    },
    /**修改密码*/
    *updatePassword({ payload, callback }, { call }) {
      const response = yield call(updatePassword, payload);
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
    saveCurrentUser(state, action) {
      return {
        ...state,
        currentUser: action.payload || {},
      };
    },
  },
};
