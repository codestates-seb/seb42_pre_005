// 질문 하나하나의 가장 작은 단위 컴포넌트입니다.

// ----- 필요 라이브러리
import styled from "styled-components";

// ----- 컴포넌트 및 이미지 파일

// ----- CSS 영역
const ItemBox = styled.div` // 아이템 하나의 전체 박스
  display: flex;
  padding: 25px 0px;
  border-bottom: 1px solid #c5c5c5;
`
const InfoBox = styled.div` // 투표, 답변, 조회 전체 부분
  padding: 0px 15px;
  width: 70%;
`
const Info = styled.p` // 투표, 답변, 조회 개별 부분
  margin-bottom: 5px;
  color: grey;
  font-size: 14px;
  text-align: right;
`
const ContentsBox = styled.div` // 질문, 태그, 유저 정보 부분

`
const ContentsHeader = styled.div` // 질문 부분
  
  
`
const QuestionTitle = styled.h3` // 질문 제목 부분
  margin-bottom: 5px;
  color: #0074cc;
  font-size: large;
  font-weight: 500;

  &:hover {
    color: #49a5f0;
    cursor: pointer;
  }
`
const QuestionDetail = styled.div` // 질문 본문 미리보기
  font-size: 14px;
  display: -webkit-box;
  word-wrap: break-word;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  text-overflow: ellipsis;
  overflow: hidden;
`
const ContentsFooter = styled.div` // 태그 및 유저정보
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 5px 0px;
`
const TagBox = styled.button` // 태그 개별 버튼
  background: #e1ecf4;
  border: none;
  border-radius: 5px;
  color: #39739d;
  font-size: 12px;
  
  margin-right: 5px;
  padding: 5px 6px;
  width: max-content;
`
const UserBox = styled.div` // 유저 아이디 및 올린 시간
  display: flex;
  align-items: center;
  height: max-content;
`
const UserImg = styled.div` // 유저 프로필 이미지
  display: flex;
  margin-right: 5px;
`
const UserName = styled.a` // 유저이름 : 클릭시 유저 개별 페이지로 이동
  margin-right: 5px;
  color: #39739d;
  font-size: small;
  text-decoration: none;
`
const UserLog = styled.p` // 유저활동 : 물어본 시간 표기
  color: #4c4c4c;
  font-size: small;
`

// ----- 컴포넌트 영역
function QuestionItem() {

  return (
    <ItemBox>
      <InfoBox>
        <Info>0 votes</Info>
        <Info>0 answers</Info>
        <Info>10 views</Info>
      </InfoBox>
      <ContentsBox>
        <ContentsHeader>
          <QuestionTitle>I would like to track my microsoft access database so that a new database is implemented whenever there is any changes. I would like to use log</QuestionTitle>
          <QuestionDetail>I want to track my database in microsoft access. For instance, let us assume I have a database named dataset 1, this database changes every day, I would like to track it, move it to pre-archive and imported the dataset 2 which is the dataset 1 with the changes. then I would like to compare dataset 1 and dataset 2. Plus, I would like to use log in microsoft access. Once compared, I do not need the dataset 1, so it would be removed and dataset 2 would the latest version of dataset 1 and the process should keep going automatically.

I am kind of having a hard time solving it.</QuestionDetail>
        </ContentsHeader>
        <ContentsFooter>
          <TagBox>mysql</TagBox>
          <UserBox>
            <UserImg>😀</UserImg>
            <UserName>therefrom</UserName>
            <UserLog>asked 1min ago</UserLog>
          </UserBox >
        </ContentsFooter>
      </ContentsBox>
    </ItemBox>
  )
}

export default QuestionItem;