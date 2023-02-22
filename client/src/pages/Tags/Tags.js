// 태그들을 모아볼 수 있는 페이지입니다. TagItem을 매핑해 보여줍니다.
// ----- 필요 라이브러리
import styled from "styled-components";

// ----- 필요 컴포넌트
import TagHeader from "./TagHeader";
import TagItem from "./TagItem";

// ----- CSS 영역

const TagBox = styled.div`
  max-width: 1000px;
  padding: 40px;
  /* background-color: beige; */
`
// ----- 컴포넌트 영역
function Tags() {
  return (
      <TagBox>
        <TagHeader />
        <TagItem />
      </TagBox>
  )
}

export default Tags;