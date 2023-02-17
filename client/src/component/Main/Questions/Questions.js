// 모든 질문이 모여 있는 페이지입니다. 질문 상단바와 모든 질문으로 구성되어 있습니다.

// ----- 필요 라이브러리
import styled from "styled-components";
import QuestionHeader from "./QuestionHeader";


// ----- 컴포넌트 및 이미지 파일
import QuestionItem from "./QuestionItem";

// ----- CSS 영역
const QuestionsBox = styled.div` // 전체 박스 구성
  align-items: center;
  max-width: 800px;
  padding-top: 40px;
`
const QustionList = styled.div` // 질문 목록 박스
  
`

function Questions() {

  return (
    <QuestionsBox>
      <QuestionHeader />
      <QustionList>
        <QuestionItem />
      </QustionList>
    </QuestionsBox>
  )
}

export default Questions;