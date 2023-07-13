import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Board from './Pages/Board/Board';
import RegisterPage from './Pages/Auth/RegisterPage';
import LoginPage from './Pages/Auth/LoginPage';
import Main from './Pages/Main/Main';
import UserPage from './Pages/User/UserPage';
import Header from './Layout/Header';
import Footer from './Layout/Footer';

const App = () => {
  return (
    <>
     <Header />
      {
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<Main/>}/>
            <Route path="/Board" element={<Board/>}/>
            <Route path="/UserPage" element={<UserPage/>}/>
            <Route path="/RegisterPage" element={<RegisterPage/>}/>
            <Route path="/LoginPage" element={<LoginPage/>}/>
          </Routes>
        </BrowserRouter>
      }
      <Footer />
    </>
  )
}

export default App;
