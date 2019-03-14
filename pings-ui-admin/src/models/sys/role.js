import {
  queryList,
  queryAll,
  queryByUserId,
  save,
  deleteById,
  allotRight,
} from '@/services/sys/role';

import { notification } from 'antd';

export default {
  namespace: 'role',

  state: {
    data: {
      list: [],
      pagination: {},
    },

    allRoles: [], //**所有角色
    userRoles: [], //**指定用户的角色
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
    /**查询所有角色*/
    *fetchAll(_, { call, put }) {
      const response = yield call(queryAll);
      yield put({ type: 'saveAllRoles', payload: response.data });
    },
    /**根据用户id查询角色*/
    *fetchByUserId({ payload }, { call, put }) {
      const response = yield call(queryByUserId, payload);
      yield put({ type: 'saveUserRoles', payload: response.data });
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
    /**分配权限*/
    *allotRight({ payload }, { call }) {
      yield call(allotRight, payload);
    },
  },

  reducers: {
    save(state, action) {
      return {
        ...state,
        data: action.payload,
      };
    },
    saveAllRoles(state, action) {
      return {
        ...state,
        allRoles: action.payload,
      };
    },
    saveUserRoles(state, action) {
      return {
        ...state,
        userRoles: action.payload,
      };
    },
  },
};
