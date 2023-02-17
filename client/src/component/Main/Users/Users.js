// 유저들을 모아볼 수 있는 페이지입니다. UserItem을 매핑해 보여줍니다.
// ----- 필요 라이브러리
import styled from "styled-components";
import LeftSideBar from "../../LeftSideBar/LeftSideBar";

const MainBox = styled.div`
  display: flex;
`
function Users() {
  return (
    <MainBox>
      <LeftSideBar />
      <div>"모든 유저목록 보기"</div>
    </MainBox>
  )
}

export default Users;