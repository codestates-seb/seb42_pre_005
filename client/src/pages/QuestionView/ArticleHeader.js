// ----- 필요 라이브러리
import styled from "styled-components";
import { Link } from "react-router-dom";

// ----- CSS 영역
const HeaderBox = styled.div`
  margin: 40px 0px 20px 0px;
  border-bottom: 1px solid #c5c5c5;
`
const HeaderTitle = styled.div` // 제목 및 새 질문 등록 버튼
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 5px;
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
const ArticleInfoBox = styled.div`
  display: flex;
  font-size: 13px;
  margin-bottom: 15px;
`
const ArticleInfo = styled.div`
  margin-right: 10px;
  .title {
    color: grey;
  }
`

function ArticleHeader ( {QuestionData} ) {
  return (
    <HeaderBox>
      <HeaderTitle>
        <h1>{QuestionData.data.title}</h1>
        <Link to="/ask"><button>Ask Question</button></Link>
      </HeaderTitle>
      <ArticleInfoBox>
        <ArticleInfo>
          <span className="title">Asked </span>
          <span className="text">{QuestionData.data.createdAt}</span>
        </ArticleInfo>
        <ArticleInfo>
          <span className="title">Modified </span>
          <span className="text">today</span>
        </ArticleInfo>
        <ArticleInfo>
          <span className="title">Viewed </span>
          <span className="text">{QuestionData.data.views} times</span>
        </ArticleInfo>
      </ArticleInfoBox>
    </HeaderBox>
  )
}

export default ArticleHeader;