// 질문 목록의 상단바입니다. 타이틀, 질문 묻기 버튼, 전체 글 목록, sort가 포함되어 있습니다.

// ----- 필요 라이브러리
import styled from "styled-components";
import { Link } from "react-router-dom";

// ----- CSS 영역
const TitleBox = styled.div` // 상단 제목과 Ask Qustion 버튼 부분
  padding-left: 20px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 20px;
`
const Title = styled.h1` // 상단 제목 스타일
  font-size: 30px;
`
const AskButton = styled.button` // 질문 버튼
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
`
const SubBox = styled.div` // 전체 글 목록 및 sort
  display: flex;
  align-items: center;
  justify-content: flex-end;
  padding-left: 20px;
  padding-bottom: 15px;
  border-bottom: 1px solid #c5c5c5;
`
const SortBox = styled.div` // sort 묶음
  display: flex;
  align-items: center;
  border: 1px solid gray;
  border-radius: 5px;
`
const SortButton = styled.button` // sort 버튼
  padding: 8px 15px;
  border: none;
  border-left: 1px solid gray;
  border-radius: 0px;
  font-size: 13px;
  color: gray;
  background-color: white;
  :hover {
    color: black;
  }
`

function TopQuestionHeader( {qustionList} ) {
  return (
    <>
      <TitleBox>
        <Title>Top Questions</Title>
        <Link to="/ask"><AskButton>Ask Question</AskButton></Link>
      </TitleBox>
      <SubBox>
        <SortBox>
          <SortButton className="left">Interesting</SortButton>
          <SortButton>Bountied</SortButton>
          <SortButton>Hot</SortButton>
          <SortButton>Week</SortButton>
          <SortButton>Month</SortButton>
        </SortBox>
      </SubBox>
    </>
  )
}

export default TopQuestionHeader;