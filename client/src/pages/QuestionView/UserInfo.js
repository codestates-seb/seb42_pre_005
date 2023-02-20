// 개별 게시물과 댓글란에서 유저별 프로필이 보이는 컴포넌트입니다

// ----- 필요 라이브러리
import styled from "styled-components";
import Vote from "./Vote";

// ----- 컴포넌트 및 이미지 파일


// ----- CSS 영역
const UserBox = styled.div` // 답변부분 전체 박스
  background-color: #dceaf6;
  padding: 10px;
  border-radius: 5px;
  width: 200px;
  margin-bottom: 20px;
`
const CreateDay = styled.div` // 질문날짜
  font-size: 14px;
`
const UserProfile = styled.div` // 유저사진, 이름
  display: flex;
  align-items: center;
  
  .img {
    width: 32px;
    height: 32px;
    margin: 8px 8px 0 0;
    background-color: gray;
  }
`

// ----- 컴포넌트 영역
function UserInfo() {
  return (
    <UserBox>
      <CreateDay>
        asked Feb 23, 2018 at 14:32
      </CreateDay>
      <UserProfile>
        {/* <img src={이미지 가져올 경로} alt="프로필이미지" />
        <div>{유저이름}</div> */}
        <div className="img">유저사진</div>
        <div>유저이름</div>
      </UserProfile>
    </UserBox>

  )
}

export default UserInfo;