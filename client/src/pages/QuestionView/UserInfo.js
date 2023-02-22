// 개별 게시물과 댓글란에서 유저별 프로필이 보이는 컴포넌트입니다

// ----- 필요 라이브러리
import styled from "styled-components";

// ----- 컴포넌트 및 이미지 파일

// ----- CSS 영역
const UserBox = styled.div` // 답변부분 전체 박스
  background-color: ${(props) => (props.color ? props.color : "white")};
  padding: 10px;
  border-radius: 5px;
  width: 200px;
  margin-bottom: 20px;
`
const CreateDay = styled.div` // 질문날짜
  font-size: 12px;
  color: gray;
  margin-bottom: 5px;
`
const UserProfile = styled.div` // 유저사진, 이름
  display: flex;
  align-items: center;
  font-size: 14px;
  .img {
    width: 32px;
    height: 32px;
    background-color: gray;
    margin-right: 10px;
  }
`

// ----- 컴포넌트 영역
function UserInfo(props) {
  return (
    <UserBox color={props.color}>
      <CreateDay>
        asked Feb 23, 2018 at 14:32
      </CreateDay>
      <UserProfile>
        <div className="img">유저사진</div>
        <div className="info">
          <a>유저이름</a>
          <div>숫자</div>
        </div>
      </UserProfile>
    </UserBox>

  )
}

export default UserInfo;