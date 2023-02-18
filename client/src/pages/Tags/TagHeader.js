// 태그 상단 제목과 설명 부분
// ----- 필요 라이브러리
import styled from "styled-components";
import { MdSearch, MdInbox } from "react-icons/md";

// ----- CSS 부분
const TagHeaderBox = styled.div` // 태그 헤더 전체 박스

`
const TagTitle = styled.h1` // 상단 제목 스타일
  font-size: 30px;
  margin-bottom: 10px;
`
const TagContent = styled.p` // 설명 부분
  font-size: 16px;
  line-height: 135%;
  margin-bottom: 10px;
`
const TagLink = styled.p` // 설명 링크 부분
  color: #0074cc;
  font-size: 13px;
  margin-bottom: 20px;
`
const SubBox = styled.div` // 검색창 및 분류 sort
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding-bottom: 15px;
`
const Search = styled.div` // 검색 영역
  display: flex;
  align-items: center;
  border: 1px solid #bbbfc3;
  box-sizing: border-box;
  border-radius: 3px;
  height: 32px;
  padding-left: 5px;
  width: 30%;

  input {
    all: unset;
    font-size: 14px;
    padding-left: 1%;
     &.input-actived {
      box-shadow: 0 0 5px 4px rgba(95, 180, 255, 0.4);
    }
  }
`
const SortBox = styled.div` // sort 묶음
  display: flex;
  align-items: center;
  border: 1px solid gray;
`
const SortButton = styled.div` // sort 버튼
  padding: 8px 15px;
  border: none;
  border-left: 1px solid gray;
  font-size: 14px;
  border-radius: 0px;
`
// ----- 컴포넌트 부분
function TagHeader() {
  return (
    <TagHeaderBox>
      <TagTitle>Tags</TagTitle>
      <TagContent>A tag is a keyword or label that categorizes your question with other, similar questions.<br /> Using the right tags makes it easier for others to find and answer your question.</TagContent>
      <TagLink>Show all tag synonyms</TagLink>
      <SubBox>
        <Search>
          <MdSearch />
          <input placeholder="Filter by tag name" />
        </Search>
        <SortBox>
          <SortButton>Popular</SortButton>
          <SortButton>Name</SortButton>
          <SortButton>New</SortButton>
        </SortBox>
      </SubBox>
    </TagHeaderBox>

  )

}

export default TagHeader;