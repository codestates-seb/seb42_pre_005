// 개별 글 내용, 투표, 사용자로 이루어진 컴포넌트입니다.

// ----- 필요 라이브러리
import styled from "styled-components";

// ----- 컴포넌트 및 이미지 파일
import Vote from "./Vote";
import UserInfo from "./UserInfo";

// ----- CSS 영역
const ArticleBox = styled.div`
  display: flex;
`
const IndexBox = styled.div` // 투표 및 본문 부분
`
const Content = styled.div` // 글 내용
  line-height: 150%;
  margin-bottom: 20px;
`
const TagBox = styled.div` // 태그 목록 박스
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
function Article() {
  return (
    <ArticleBox>
      <Vote />
      <IndexBox>
        <Content>
        <p>I recently added a new scene to my game and I was testing going to it from previously made scenes. But while I can switch between the 3 scenes I made before just fine, when I switch to the new scene, the game gets an error and freezes.</p>
        <p>Here is the code from a previously made scene. The code involved in scene switching makes it so the game switches to the adjacent when the player moves to an invisible sprite on one of the sides of the scene.</p>
        </Content>
        <TagBox></TagBox>
        <InfoBox>
          <Info>
            <div>Share</div>
            <div>Edit</div>
            <div>Follow</div>
          </Info>
          <UserInfo color="#DCE9F6" />
        </InfoBox>
      </IndexBox>
    </ArticleBox>
  )
}

export default Article;