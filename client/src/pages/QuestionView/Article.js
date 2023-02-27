// 개별 글 내용, 투표, 사용자로 이루어진 컴포넌트입니다.

// ----- 필요 라이브러리
import styled from "styled-components";
import { TiArrowSortedUp,TiArrowSortedDown } from "react-icons/ti"
import { RxBookmark, RxCounterClockwiseClock } from "react-icons/rx"
import { useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";

// ----- 컴포넌트 및 이미지 파일

// ----- CSS 영역
const ArticleBox = styled.div`
  display: flex;
  justify-content: flex-start;
`
const VoteBox = styled.div` // 투표 기능
  display: flex;
  align-items: center;
  flex-direction: column;
  margin-right: 15px;
  width: 50px;
  color: #BBBFC3;
  .counticon {
    font-size: 45px;
  }
  .markicon {
    font-size: 15px;
    margin-bottom: 5px;
  }
`
const IndexBox = styled.div` // 본문 부분 박스
  width: 800px;
  margin-bottom: 30px;
  .contents { // 글 내용
    line-height: 150%;
    margin-bottom: 20px;
  }
  .tags {
    background: #e1ecf4;
    border: none;
    border-radius: 5px;
    color: #39739d;
    font-size: 12px;
    
    margin-right: 5px;
    padding: 5px 6px;
  }
`
const InfoBox = styled.div` // 공유, 수정 버튼 및 유저정보
  display: flex;
  justify-content: space-between;
  margin-top: 30px;
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
const UserBox = styled.div` // 답변부분 전체 박스
  background-color: #DCE9F6;
  padding: 10px;
  border-radius: 5px;
  width: 200px;
  margin-bottom: 20px;
  .creatday {
    font-size: 12px;
    color: gray;
    margin-bottom: 5px;
  }
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
function Article() {
  const navigate = useNavigate();
  const params = useParams();
  const [item, setItem] = useState();

  useEffect(()=>{
    console.log(params);
    axios.get(`${process.env.REACT_APP_API_URL}/questions/${params.id}`)
    .then((res)=>{
      console.log(res.data)
      setItem(res.data)
    })
  })

  return (
    <ArticleBox>
      <VoteBox>
        <TiArrowSortedUp className="counticon" />
        {item.voteCount}
        <TiArrowSortedDown className="counticon" />
        <RxBookmark className="markicon" />
        <RxCounterClockwiseClock className="markicon" />
      </VoteBox>
      <IndexBox>
        <div className="contents">{item.content}</div>
        <button className="tags">{item.tagName}</button>
        <InfoBox>
          <Info>
            <div>Share</div>
            <div onClick={() => navigate(`/questions/${item.questionId}/edit`)}>Edit</div>
            <div>Follow</div>
          </Info>
          <UserBox>
            <div className="creatday">asked Feb 23, 2018 at 14:32</div>
            <UserProfile>
              <div className="img">{item.name}</div>
              <div className="info">
                <div onClick={() => navigate(`/users/${item.memberId}/${item.name}`)}>{item.name}</div>
                <div>{item.questionId}</div>
              </div>
            </UserProfile>
          </UserBox>
        </InfoBox>
      </IndexBox>
    </ArticleBox>
  )
}

export default Article;