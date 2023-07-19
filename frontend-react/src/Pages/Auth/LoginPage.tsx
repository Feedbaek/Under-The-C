import { memo, useRef } from "react";
import { useNavigate } from "react-router-dom";
import { LoginState, isLoginState } from "../../Atoms/Login";
import { useRecoilState } from "recoil";
import { fetchLogin } from "../../Api/api";
import { useMutation } from "react-query";
import { useState } from "react";

const LoginPage = () => {
	const navigate = useNavigate();

	const id = useRef("");
	const password = useRef("");
	const [, setLoginState] = useRecoilState(LoginState);
	const [, setIsLoginState] = useRecoilState(isLoginState);

	const loginMutation = useMutation(() => fetchLogin(id.current, password.current), {
		 onSuccess: () => {
			setLoginState({ id: id.current, password: password.current });
			setIsLoginState(true);
			navigate('/');
		},
		onError: () => {
			alert("로그인에 실패했습니다.");
			setIsLoginState(false);
			id.current = "";
			password.current = "";
		}
	});
	
	const handleChange = (e: any, ref: any) => {
		const value = e.target.value;
		ref.current = value;
	}

	const onSubmit = (e: any) => {
		e.preventDefault();
		// 전처리
		if (!id) {
		  return alert("ID를 입력하세요.");
		}
		else if (!password) {
		  return alert("Password를 입력하세요.");
		}
		loginMutation.mutate();
	};

    return (
		<div className="bg-neutral-100">
			<div className="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
				<div className="w-full p-6 bg-white rounded-lg shadow  md:mt-0 sm:max-w-md xl:p-0">
					<form onSubmit={onSubmit} className="space-y-4 md:space-y-6">
						<h2 className="text-center text-slate-500">Login</h2>
						<input type='text' 
								onChange={(e) => handleChange(e, id)}
								className="bg-gray-50 border border-gray-300 text-gray-900
											sm:text-sm rounded-lg focus:ring-primary-600 
											focus:border-primary-600 block w-full p-2.5"
											placeholder="id"
						/>
						<input type='password'
							   onChange={(e) => handleChange(e, password)}
								className="bg-gray-50 border border-gray-300 text-gray-900
								sm:text-sm rounded-lg focus:ring-primary-600 
								focus:border-primary-600 block w-full p-2.5"
											placeholder="password"
						/>
						<button type="submit" 
								disabled={loginMutation.isLoading} 
								className="w-full bg-blue-500 text-white bg-primary-600 
											hover:bg-primary-700 focus:ring-4 
											focus:outline-none focus:ring-primary-300 
											font-medium rounded-lg text-sm px-5 
											py-2.5 text-center "> 
						로그인 </button>
						<br/>
						<br/>
						<a href="/RegisterPage" 
						className="font-medium text-primary-600 hover:underline">
							회원가입
						</a>
					</form>
				</div>
			</div>
		</div>
    );
};

export default memo(LoginPage);
