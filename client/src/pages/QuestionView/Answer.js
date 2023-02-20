// 답변 내용과 답변을 달 수 있는 창입니다.

// ----- 필요 라이브러리
import styled from "styled-components";
import UserInfo from "./UserInfo";
import Vote from "./Vote";

// ----- 컴포넌트 및 이미지 파일


// ----- CSS 영역
const AnswerBox = styled.div` // 답변부분 전체 박스
  border-top: 1px solid #c5c5c5;
  
`
const IndexBox = styled.div` // 투표, 콘텐츠 부분
  display: flex;  
`
const Content = styled.div` // 답변 콘텐츠 영역
  margin: 20px 10px;
`



// ----- 컴포넌트 영역
function Answer() {
  return (
    <AnswerBox>
      <IndexBox>
        <Vote />
        <Content>
          콘텐츠 내용
        </Content>
      </IndexBox>
      <UserInfo />
    </AnswerBox>

  )
}

export default Answer;