// ----- 필요 라이브러리
import styled from "styled-components";

const RightSideBarBox = styled.div`
  width: 150px;
  background-color: yellowgreen;
`

function RightSideBar() {
  return (
    <RightSideBarBox>
      오른쪽 사이드바 내용
      <div>The Overflow Blog</div>
      <div>You don’t have to build a browser in JavaScript anymore (Ep. 538)</div>
    </RightSideBarBox>
  )
}

export default RightSideBar;