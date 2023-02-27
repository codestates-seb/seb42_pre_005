// 메인-왼쪽 사이드바입니다. 

// ----- 필요 라이브러리
import styled from "styled-components";
import { Link, useLocation, useNavigate } from 'react-router-dom';

// ----- 필요 컴포넌트

const LeftSideBarBox = styled.div` // 전체 박스 스타일
  width: 170px; 
  padding-top: 40px;
  padding-left: 15px;
  border-right: 1px solid #c5c5c5;
  color: gray;
  white-space: nowrap;
  a { // 링크에 밑줄 없애기
    color: gray;
    text-decoration: none;
  }
`
const TitleBox = styled.ol` // 그룹핑 해주는 박스 스타일
  margin-bottom: 27px;
  list-style: none;
`
const SmallTitle = styled.li` // 작은 타이틀 스타일
  font-size: 12px;
`
const BigTitle = styled.li` // 큰 타이틀 스타일
  font-size: 14px;
  padding: 12px 80px 10px 12px;
  
  :hover {
    color: #c5c5c5;
  }
  &.current-page {
    background-color: rgb(241, 242, 243);
    border-right: 3px solid rgb(244, 130, 36);
    font-weight: 600;
    color: black;
  }
`

function LeftSideBar() {
  const navigate = useNavigate();
  const curruntPath = useLocation().pathname;

  return (
    <LeftSideBarBox>
      <TitleBox>
        <Link to="/"><SmallTitle>Home</SmallTitle></Link>
      </TitleBox>
      <TitleBox>
        <SmallTitle>PUBLIC</SmallTitle>
          <BigTitle 
            className={curruntPath === "/questions" ? "current-page" : null} 
            onClick={() => navigate("/questions")}>Questions</BigTitle>
          <BigTitle 
            className={curruntPath === "/tags" ? "current-page" : null} 
            onClick={() => navigate("/tags")}>Tags</BigTitle>
          <BigTitle 
            className={curruntPath === "/users" ? "current-page" : null} 
            onClick={() => navigate("/users")}>User</BigTitle>
          <BigTitle>Companies</BigTitle>
      </TitleBox>
      <TitleBox>
        <SmallTitle>COLLECTIVES</SmallTitle>
          <BigTitle>Explore Collectives</BigTitle>
      </TitleBox>
      <TitleBox>
        <SmallTitle>TEAMS</SmallTitle>
          <BigTitle>Create free Team</BigTitle>
      </TitleBox>
    </LeftSideBarBox>
  )
}

export default LeftSideBar;