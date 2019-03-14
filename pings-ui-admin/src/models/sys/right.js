import { queryAll, save, deleteById, queryByRoleId } from '@/services/sys/right';

import { notification } from 'antd';

export default {
  namespace: 'right',

  state: {
    allRights: [],
    roleRights: [], //**指定权限的权限
  },

  effects: {
    /**查找所有权限 */
    *fetchAll(_, { call, put }) {
      const response = yield call(queryAll);
      yield put({
        type: 'saveAllRights',
        payload: response.data,
      });
    },
    /**根据角色id查询权限*/
    *fetchByRoleId({ payload }, { call, put }) {
      const response = yield call(queryByRoleId, payload);
      yield put({ type: 'saveRoleRights', payload: response.data });
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
    saveAllRights(state, action) {
      return {
        ...state,
        allRights: action.payload,
      };
    },
    saveRoleRights(state, action) {
      return {
        ...state,
        roleRights: action.payload,
      };
    },
  },
};
