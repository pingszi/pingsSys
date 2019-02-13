import { queryAll } from '@/services/sys/dept';

export default {
  namespace: 'dept',

  state: {
    allDepts: [],
  },

  effects: {
    *fetchAll(_, { call, put }) {
      const response = yield call(queryAll);
      yield put({
        type: 'saveAllDepts',
        payload: response.data,
      });
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
