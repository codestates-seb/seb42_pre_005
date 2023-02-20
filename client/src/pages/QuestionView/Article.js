// 개별 글 제목, 내용, 투표, 사용자로 이루어진 컴포넌트입니다.

// ----- 필요 라이브러리
import styled from "styled-components";
import Vote from "./Vote";
import UserInfo from "./UserInfo";


// ----- 컴포넌트 및 이미지 파일
const ArticleBox = styled.div` // 전체 감싸는 컴포넌트
`

const ArticleHeader = styled.div` // 제목, 질문박스, 상세정보
`
const HeaderTitle = styled.div` // 제목 및 새 질문 등록 버튼
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 20px;
  h1 {
    font-size: 25px;
  }
  button {
    align-items: center;
    padding: 9px 13px;
    border-radius: 5px;
    box-shadow: inset 0px 1px 0px 0px rgb(255 255 255 / 30%);
    border: 1px solid rgb(57,115,157);
    text-align: center;
    font-size: 14px;
    font-weight: 400;
    color: #fff;
    background: rgb(10, 149, 255);
    cursor: pointer;
    &:hover {
      background-color: rgb(49, 114, 198);
    }
  }

`

const IndexBox = styled.div` // 투표 및 본문 부분
  display: flex;
  border-top: 1px solid #c5c5c5;
`

const Content = styled.div` // 글 내용
`

// ----- CSS 영역

// ----- 컴포넌트 영역
function Article() {

  return (
    <ArticleBox>
      <ArticleHeader>
        <HeaderTitle>
          <h1>Java 8 calculate months between two dates</h1>
          <button>Ask Question</button>
        </HeaderTitle>
        <div>
        Asked 4 years, 11 months ago
        Modified today
        Viewed 105k times
        </div>
      </ArticleHeader>
      <IndexBox>
        <Vote />
        <Content>
          콘텐츠 내용
        </Content>
      </IndexBox>
      <UserInfo />
    </ArticleBox>
  )
}

export default Article;