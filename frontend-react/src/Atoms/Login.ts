import { atom, selector } from 'recoil';
import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist();

export interface ILogin {
	id: string;
	password: string;
};


export const isLoginState = atom({
  key: 'isLogin',
  default: false,
  effects_UNSTABLE: [persistAtom],
});

//export const isLogin = selector({
//	key: 'isLogin',
//	//get은 필수지만 set은 옵셔널체이닝
//	get: ({ get }) => {
//	  return get(isLoginState);
//	},
//	set: ({ set }, state) => {
//	  set(isLoginState, state);
//	},
//  });

export const LoginState = atom<ILogin>({
	key: 'loginInfo',
	default: {
		id: '',
		password: '',
	}
});