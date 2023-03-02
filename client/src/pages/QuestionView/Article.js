// 개별 글 내용, 투표, 사용자로 이루어진 컴포넌트입니다.

// ----- 필요 라이브러리
import styled from "styled-components";
import { TiArrowSortedUp,TiArrowSortedDown } from "react-icons/ti"
import { RxBookmark, RxCounterClockwiseClock } from "react-icons/rx"
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { getAccessToken } from "../../storage/cookie";

// ----- 컴포넌트 및 이미지 파일
import Markdown from "../../components/Markdown";

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
  padding: 11px;
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
function Article({QuestionData, setIsUpdated}) {
  const navigate = useNavigate();
  const DeleteAction = () => {
    return axios.delete(`${process.env.REACT_APP_API_URL}/questions/${QuestionData.data.questionId}`)
  }

  const voteHandler = (type) => {
    console.log(type);
    if(type === "voteUp"){
      axios.patch(`${process.env.REACT_APP_API_URL}/questions/${QuestionData.data.questionId}/vote?updown=up`, {},{
        headers: {
          Authorization: getAccessToken()
        }
      }).then(()=>{
        setIsUpdated(true);
      })
    }else{
      axios.patch(`${process.env.REACT_APP_API_URL}/questions/${QuestionData.data.questionId}/vote?updown=down`, {},{
        headers: {
          Authorization: getAccessToken()
        }
      }).then(()=>{
        setIsUpdated(true);
      })
    }
  }
  return (
    <ArticleBox>
      <VoteBox>
        <TiArrowSortedUp className="counticon" data-name="voteUp" onClick={(e)=>voteHandler(e.target.dataset.name)} />
        {QuestionData.data.voteCount}
        <TiArrowSortedDown className="counticon" data-name="voteDown" onClick={(e)=>voteHandler(e.target.dataset.name)}/>
        <RxBookmark className="markicon" />
        <RxCounterClockwiseClock className="markicon" />
      </VoteBox>
      <IndexBox>
        <Markdown className="contents" markdown={QuestionData.data.content} />
        <button className="tags">{QuestionData.data.tagName}</button>
        <InfoBox>
          <Info>
            <div onClick={() => navigate(`/questions/${QuestionData.data.questionId}/edit`)}>Edit</div>
            <div onClick={() => DeleteAction()}>Delete</div>
          </Info>
          <UserBox>
            <div className="creatday">asked Feb 23, 2018 at 14:32</div>
            <UserProfile>
              <div className="img">{QuestionData.data.name}</div>
              <div className="info">
                <div onClick={() => navigate(`/users/${QuestionData.data.memberId}/${QuestionData.data.name}`)}>{QuestionData.data.name}</div>
                <div>{QuestionData.data.questionId}</div>
              </div>
            </UserProfile>
          </UserBox>
        </InfoBox>
      </IndexBox>
    </ArticleBox>
  )
}

export default Article;