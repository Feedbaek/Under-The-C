import "./App.css";
import { RecoilRoot, atom, selector, useRecoilState, useRecoilValue } from 'recoil';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Board from './Pages/Board/Board';
import LoginPage from './Pages/Auth/LoginPage';
import RegisterPage from './Pages/Auth/RegisterPage';
import Header from './Layout/Header';
import Footer from './Layout/Footer';
import Main from './Pages/Main/Main';
//import Search from "./Pages/Search/Search";
import UserPage from './Pages/User/UserPage';

const App = () => {
  return (
    <>
	<RecoilRoot>
		<BrowserRouter>
				<Header/>
			<Routes>
				<Route path="/" element={<Main/>}/>
				<Route path="/board" element={<Board/>}/>
				<Route path="/UserPage" element={<UserPage/>}/>
				<Route path='/LoginPage' element={<LoginPage/>}/>
				<Route path="/RegisterPage" element={<RegisterPage/>}/>
			</Routes>
		</BrowserRouter>
	</RecoilRoot>
	<Footer/>
    </>
  )
}

export default App;
