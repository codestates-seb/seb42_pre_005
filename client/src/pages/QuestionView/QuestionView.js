// 개별 글을 볼 수 있는 컴포넌트입니다.

// ----- 필요 라이브러리
import styled from "styled-components";
import Article from "./Article";
import Answer from "./Answer";
import AnswerCreate from "./AnsweCreate";

// ----- 컴포넌트 및 이미지 파일

// ----- CSS 영역
const ViewBox = styled.div` // 묻는 공간 전체 창
  color: #3b4045;
  max-width: 900px;
  padding: 30px 15px;
  h1 { // Ask a public question 부분
    margin-bottom: 50px;
    font-size: 28px;
    font-weight: 600;
  }
`

// ----- 컴포넌트 영역
function QuestionView() {
  return (
    <ViewBox>
      <Article />
      <Answer />
      <AnswerCreate />
    </ViewBox>
  )
}

export default QuestionView

