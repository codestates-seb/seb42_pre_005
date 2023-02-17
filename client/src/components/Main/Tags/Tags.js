// 태그들을 모아볼 수 있는 페이지입니다. TagItem을 매핑해 보여줍니다.
// ----- 필요 라이브러리
import styled from "styled-components";
import LeftSideBar from "../../LeftSideBar/LeftSideBar";

const MainBox = styled.div`
  display: flex;
`
function Tags() {
  return (
    <MainBox>
      <LeftSideBar />
      <div>"모든 태그목록 보기"</div>
    </MainBox>
  )
}

export default Tags;