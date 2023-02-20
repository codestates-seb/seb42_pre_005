// 가장 먼저 보이는 메인입니다. 왼쪽 사이드바와 모든 질문이 모인 Question 컴포로 구성되어 있습니다.

// ----- 필요 라이브러리
import styled from "styled-components";
import LeftSideBar from "../../components/LeftSideBar/LeftSideBar";
import Questions from "../Questions/Questions";

// ----- 컴포넌트 및 이미지 파일

const MainBox = styled.div`
  display: flex;
`

function Main() {
  return (
    <MainBox>
      <LeftSideBar/>
      <Questions/>
    </MainBox>
  )
}

export default Main;