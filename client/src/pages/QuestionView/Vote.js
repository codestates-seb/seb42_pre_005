// 게시글과 답변의 추천 컴포넌트

// ----- 필요 라이브러리
import styled from "styled-components";
import { TiArrowSortedUp,TiArrowSortedDown } from "react-icons/ti"
import { RxBookmark, RxCounterClockwiseClock } from "react-icons/rx"


// ----- 컴포넌트 및 이미지 파일
const VoteBox = styled.div` // 투표 기능
  display: flex;
  align-items: center;
  flex-direction: column;
  margin-right: 15px;
  width: 50px;
  color: #BBBFC3;
  .counticon {
    font-size: 45px;
  }
  .markicon {
    font-size: 15px;
    margin-bottom: 5px;
  }
`
// ----- 컴포넌트 영역
function Vote() {
  return (
    <VoteBox>
      <TiArrowSortedUp className="counticon" />
      15
      <TiArrowSortedDown className="counticon" />
      <RxBookmark className="markicon" />
      <RxCounterClockwiseClock className="markicon" />
    </VoteBox>
  )
 }

export default Vote