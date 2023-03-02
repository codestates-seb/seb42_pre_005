// 모든 질문이 모여 있는 페이지입니다. 질문 상단바와 모든 질문으로 구성되어 있습니다.

// ----- 필요 라이브러리
import styled from "styled-components";
import axios from "axios";
import { useEffect, useState } from "react";



// ----- 컴포넌트 및 이미지 파일
import TopQuestionHeader from "../../components/Header/TopQuestionHeader";
import RightSideBar from "../../components/Main/RightSideBar";
import QuestionItem from "../../components/Questions/QuestionItem";
import Paging from "../../components/Pagination";

// ----- CSS 영역
const QuestionsBox = styled.div` // 전체 박스 구성
  align-items: center;
  width: 800px;
  padding: 40px 0px;
  .pagenation {
    margin-left: 20px;
  }
`
const QustionList = styled.div` // 질문 목록 박스
`
const RightSideBarBox = styled.div`
    margin-top: 40px;
`

function TopQuestions() {
  const [questionList, setQuestionList] = useState([]); // 뿌려줄 질문 리스트
    const [page, setPage] = useState(1); // 페이지 정보 가져오기
    const [isPending, setIsPending] = useState(true);
    const [size, setSize] = useState(10); //  한 페이지에 보여줄 아이템 수
    const [total, setTotal] = useState(); // 전체 아이템 수

  useEffect(() => {
        // axios.defaults.withCredentials = true;
        axios.get(`${process.env.REACT_APP_API_URL}/questions?page=${page}&size=${size}`)
        .then((res) => {
            // console.log(res.data)
            setQuestionList(res.data.data);
            setTotal(res.data.pageInfo.totalElements)
        })
        .then(()=> setIsPending(false));
    }, [page]);

  return (
    <>
      <QuestionsBox>
        <TopQuestionHeader qustionList={questionList}/>
        <QustionList>
          {!isPending && questionList.map((questionItem) => <QuestionItem 
          key={questionItem.questionId} 
          questionItem={questionItem}/>)}
        </QustionList>
        <div className="pagenation">
          <Paging 
          total={total} size={size} page={page} setPage={setPage}/>
        </div>
      </QuestionsBox>
      <RightSideBarBox>
        <RightSideBar />
      </RightSideBarBox>
    </>
  )
}

export default TopQuestions;