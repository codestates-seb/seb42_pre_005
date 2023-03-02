// 최상단의 헤더 입니다. 스택오버플로우 로고, 검색창, 로그인/회원가입 버튼(비로그인 시), 유저 정보(로그인 시)로 구성되어 있습니다.

// ----- 필요 라이브러리
import { useState } from "react";
import styled from "styled-components";
import { MdSearch, MdInbox } from "react-icons/md";

// ----- 컴포넌트 및 이미지 파일
import Logo from "../../icons/Logo.svg";
import defaultProfile from "../../icons/defaultProfile.png";
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { setIsLogin, setLoginUser } from "../../store/store";

// ----- CSS 영역
const HeaderBox = styled.div` // 헤더 전체 박스
  display: flex;
  align-items: center;
  justify-content: space-between;
  
  width: 100vw;
  height: 50px;
  padding: 0px 15px;
  background-color: rgb(248, 249, 249);
  border-top: 3px solid rgb(244, 130, 36);
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05), 0 1px 4px rgba(0, 0, 0, 0.05), 0 2px 8px rgba(0, 0, 0, 0.05);
`
const StackoverflowLogo = styled.div` // 스택오버플로우 로고 영역
  display: flex;
  align-items: center;
  height: 100%;
  :hover {
    background-color: grey;
  }
`
const SearchBox = styled.div` // 검색창 영역
  display: flex;
  align-items: center;
  
  border: 1px solid #bbbfc3;
  box-sizing: border-box;
  border-radius: 3px;
  
  height: 32px;
  padding-left: 5px;
  width: 50%;

  .icons {
    font-size: 22px;
    color: gray;
  }
`
const SearchBarInput = styled.input` // 검색창 인풋 영역
  all: unset;
  font-size: 14px;
  padding-left: 1%;
  &.input-actived {
    box-shadow: 0 0 5px 4px rgba(95, 180, 255, 0.4);
  } 
`
const RightBox = styled.div` // 오른쪽 로그인영역(로그인 전), 유저영역(로그인 후)
  display: flex;
  align-items: center;
`
const LoginBox = styled.div` // 로그인 전 : 로그인, 회원가입 영역
  display: flex;
  height: 100%;
`
const Button = styled.div` // 기본 버튼 CSS
  padding: 8px 12px;
  margin: 0px 5px;
  border-radius: 5px;
  box-shadow: inset 0px 1px 0px 0px rgb(255 255 255 / 30%);
  border: 1px solid rgb(57,115,157);
  text-align: center;
  font-size: 14px;
  font-weight: 400;
  cursor: pointer;
`
const LoginButton = styled(Button)` // Log in 버튼 CSS, 위의 Button 컴포넌트의 스타일을 재활용
  background: rgb(225,236,244);
  color: rgb(57,115,157);

  &:hover {
    background-color: rgb(185, 210, 232);
  }
`
const SignupButton = styled(Button)` // Sign up 버튼 CSS, 위의 Button 컴포넌트의 스타일을 재활용
  background: #0a95ff;
  color: rgb(255, 255, 255);
  &:hover {
    background-color: rgb(49, 114, 198);
  }
`
const UserBox = styled.div` // 로그인 후 : 유저사진, 아이콘 영역
  height: 100%;
`
const LogOut = styled.button`
  margin: 20px;
`

// ----- 컴포넌트 영역
function Header() {
  // const [isLogin, setisLogin] = useState(false); // 로그인 여부를 결정짓는 상태
  const navigate = useNavigate();
  const isLogin = useSelector(state => state.isLogin);
  const loginUser = useSelector(state => state.loginUser);
  const dispatch = useDispatch();

  return (
    <HeaderBox>
      <StackoverflowLogo onClick={() => navigate("/")}>
        <img src={Logo} alt="logo" />
      </StackoverflowLogo>
      <SearchBox>
        <MdSearch className="icons"/>
        <SearchBarInput placeholder="Search..." />
      </SearchBox>
      <RightBox> 
          {/* <UserBox>
            <img src={defaultProfile} alt="user profile img" onClick={() => navigate("/userdetail")} height="24px" />
            <MdInbox />
          </UserBox> 
          <LoginBox>
            <LoginButton onClick={() => navigate("/login")}>Log in</LoginButton>
            <SignupButton onClick={() => navigate("/register")}>Sign up</SignupButton>
          </LoginBox> */}

        {isLogin 
          ? // 로그인을 했을 때는 유저 프로필 사진과 아이콘들이 보임
          <UserBox>
            <img src={defaultProfile} alt="user profile img" onClick={() => navigate(`/users/${loginUser.id}/${loginUser.name}`)} height="24px" />
            <LogOut onClick={() => {
              dispatch(setLoginUser(null));
              dispatch(setIsLogin(false));
              navigate("/");
            }}>Log out</LogOut>
          </UserBox> 
          : // 로그인 하지 않았을 때는 login과 signup 버튼이 보임
          <LoginBox>
            <LoginButton onClick={() => navigate("/login")}>Log in</LoginButton>
            <SignupButton onClick={() => navigate("/register")}>Sign up</SignupButton>
          </LoginBox>}
      </RightBox>
    </HeaderBox>
  );
}

export default Header;