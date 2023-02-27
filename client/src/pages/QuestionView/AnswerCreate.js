// 새 글을  작성하는 공간입니다

// ----- 필요 라이브러리
import styled from "styled-components";
import { useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from "axios";
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';

// ----- 컴포넌트 및 이미지 파일

// ----- CSS 영역
const CreateBox = styled.div` // 전체 영역
  border-top: 1px solid #c5c5c5;
  margin-bottom: 50px;
  h1 {
    margin-top: 30px;
    margin-bottom: 30px;
    font-size: 25px;
    font-weight: bold;
  }
`
const CreateButton = styled.button` // 답변 등록 버튼
  margin-top: 20px;
  padding: 10px 13px;
  border-radius: 4px;
  box-shadow: inset 0px 1px 0px 0px rgb(255 255 255 / 30%);
  border: 1px solid rgb(57,115,157);
  text-align: center;
  font-size: 14px;
  font-weight: 400;
  background: rgb(10, 149, 255);
  color: rgb(255, 255, 255);
  cursor: pointer;
  &:hover {
    background-color: rgb(49, 114, 198);
  }
`

// ----- 컴포넌트 영역
function AnswerCreate() {
  const [answerText, setAnswerText] = useState(""); // 질문 내용 입력칸의 상태 관리창
  const { id } = useParams();


  const Answerpost = () => {
    // 댓글 포스트 요청 함수
    axios({
      method: 'post',
      url: `${process.env.REACT_APP_API_URL}/questions/${id}/answers`,
      data: {content : {answerText}}
    })
    .then(res => {
      console.log(res)
      window.location.reload()
    })
  }

  return (
    <CreateBox>
      <h1>Your answer</h1>
      <ReactQuill 
        theme="snow"
        value={answerText}
        onChange={(e) => setAnswerText(e)} 
      />
      <CreateButton onClick={Answerpost}>Post Your Answer</CreateButton>
    </CreateBox>
  )
}
export default AnswerCreate;