import React from 'react';
import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container';
import Form from 'react-bootstrap/Form';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import 'bootstrap/dist/css/bootstrap.css';
import { isLoginState } from '../Atoms/Login';
import { useRecoilState } from 'recoil';

const Header = () => {
  const [loggedIn, setLoggedIn] = useRecoilState(isLoginState);

  const handleLogout = () => {
    setLoggedIn(false);
  };

  console.log(loggedIn);

  return (
    <Navbar expand="lg" className="bg-body-tertiary">
      <Container>
        <Navbar.Brand href="/">Under-The-C</Navbar.Brand>
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">
          <Nav className="me-auto">
            <Nav.Link href="/">메인 페이지</Nav.Link>
            <Nav.Link href="/Board">강의평가</Nav.Link>
          </Nav>
          <Nav className="ms-auto">
            <Form className="d-flex">
              <Form.Control
                type="search"
                placeholder="Search"
                className="me-2"
                aria-label="Search"
              />
              <Button variant="light" size="sm" href="/">
                Search
              </Button>
            </Form>
            {loggedIn ? (
              <>
                <Button variant="light" size="sm" href="/UserPage">
                  마이페이지
                </Button>
                <Button variant="light" size="sm" onClick={handleLogout} href="/">
                  로그아웃
                </Button>
              </>
            ) : (
				<>
				<Button variant="light" size="sm" href="/RegisterPage">
					회원가입
				</Button>
				<Button variant="light" size="sm" href="/LoginPage">
					로그인
				</Button>
			  </>
            )}
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default Header;
