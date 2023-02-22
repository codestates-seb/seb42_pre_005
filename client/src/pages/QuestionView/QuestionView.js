// 개별 글을 볼 수 있는 컴포넌트입니다.

// ----- 필요 라이브러리
import styled from "styled-components";
import Article from "./Article";
import Answer from "./Answer";
import AnswerCreate from "./AnswerCreate";
import RightSideBar from "../../components/Main/RightSideBar";
import ArticleHeader from "./ArticleHeader";

// ----- 컴포넌트 및 이미지 파일

// ----- CSS 영역
const ViewBox = styled.div` // 묻는 공간 전체 창
  margin-left: 20px;
`
const ContentsBox = styled.div`
  display: flex;
  max-width: 1000px;
`
const Contents = styled.div`
  
`

// ----- 컴포넌트 영역
function QuestionView() {
  return (
    <ViewBox>
      <ArticleHeader />
      <ContentsBox>
        <Contents>
          <Article />
          <Answer />
        </Contents>
        <RightSideBar />
      </ContentsBox>
    </ViewBox>
  )
}

export default QuestionView

