// 답변 내용과 답변을 달 수 있는 창입니다.

// ----- 필요 라이브러리
import styled from "styled-components";
import AnswerCreate from "./AnswerCreate";
import UserInfo from "./UserInfo";
import Vote from "./Vote";

// ----- 컴포넌트 및 이미지 파일


// ----- CSS 영역
const AnswerBox = styled.div` // 답변부분 전체 박스
  display: flex;
  border-top: 1px solid #BBBFC3;
  padding-top: 20px;
`
const IndexBox = styled.div` // 투표 및 본문 부분
`
const Content = styled.div` // 글 내용
  line-height: 150%;
  margin-bottom: 20px;
`
const InfoBox = styled.div` // 공유, 수정 버튼 및 유저정보
  display: flex;
  justify-content: space-between;
`
const Info = styled.div` // 공유 수정 팔로우 버튼
  display: flex;
  color: gray;
  font-size: 14px;
  text-decoration: none;
  div {
    margin-right: 10px;
  }
`

// ----- 컴포넌트 영역
function Answer() {
  return (
    <>
    <AnswerBox>
      <Vote />
      <IndexBox>
        <Content>
        <p>I recently added a new scene to my game and I was testing going to it from previously made scenes. But while I can switch between the 3 scenes I made before just fine, when I switch to the new scene, the game gets an error and freezes.</p>
        </Content>
        <InfoBox>
          <Info>
            <div>Share</div>
            <div>Edit</div>
            <div>Follow</div>
          </Info>
          <UserInfo />
        </InfoBox>
      </IndexBox>
    </AnswerBox>
    <AnswerCreate />
    </> 

  )
}

export default Answer;