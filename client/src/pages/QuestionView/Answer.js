// 답변 내용과 답변을 달 수 있는 창입니다.

// ----- 필요 라이브러리
import styled from "styled-components";
import { TiArrowSortedUp,TiArrowSortedDown } from "react-icons/ti"
import { RxBookmark, RxCounterClockwiseClock } from "react-icons/rx"
import AnswerCreate from "./AnswerCreate";
import { useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";


// ----- 컴포넌트 및 이미지 파일


// ----- CSS 영역
const AnswerBox = styled.div` // 답변부분 전체 박스
  display: flex;
  border-top: 1px solid #BBBFC3;
  padding-top: 20px;
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
function Answer( {AnswerData} ) {
  const navigate = useNavigate();
  const DeleteAction = () => {
    return axios.delete(`${process.env.REACT_APP_API_URL}/answers/${AnswerData.answerId}`)
  }

  return (
    <>
      {AnswerData && AnswerData.map((AnswerItem)=> 
      <AnswerBox key={AnswerItem.answerId}>
        <VoteBox>
          <TiArrowSortedUp className="counticon" />
            {AnswerItem.voteCount}
          <TiArrowSortedDown className="counticon" />
          <RxBookmark className="markicon" />
          <RxCounterClockwiseClock className="markicon" />
        </VoteBox>
        <IndexBox>
          <div className="contents">{AnswerItem.content}</div>
            <InfoBox>
              <Info>
                <div onClick={() => navigate(`/answers/${AnswerData.answerId}/edit`)}>Edit</div>
                <div onClick={() => DeleteAction()}>Delete</div>
              </Info>
              <UserBox>
                <div className="creatday">asked today</div>
                <UserProfile>
                  <div className="img">{AnswerItem.name}</div>
                  <div className="info">
                    <p>{AnswerItem.name}</p>
                    <div>유저정보</div>
                  </div>
                </UserProfile>
              </UserBox>
            </InfoBox>
          </IndexBox>
      </AnswerBox>
    )}
    <AnswerCreate />
    </> 
  )
}

export default Answer;