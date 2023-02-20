// 글 작성란입니다.

// ----- 필요 라이브러리
import styled from "styled-components";
import React, { useState } from 'react';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';

// ----- 컴포넌트 및 이미지 파일

// ----- CSS 영역
const AskBox = styled.div` // 묻는 공간 전체 창
  color: #3b4045;
  max-width: 900px;
  padding: 30px 15px;
  h1 { // Ask a public question 부분
    margin-bottom: 50px;
    font-size: 28px;
    font-weight: 600;
  }
`
const BuleBox = styled.div` // 가장 첫줄 파란색 박스
  margin-bottom: 20px;
  padding: 30px 25px;
  background-color: #EDF4FA;
  border: 1px solid #ADCDEA;
  border-radius: 5px;
  line-height: 135%;

  h2 {
    margin-bottom: 7px;
    font-size: 20px;
  }
  p {
    margin-bottom: 10px;
  }
  a {
    color: #0a95ff;
    text-decoration: none;
  }
  h5 {
    margin-bottom: 5px;
  }
  ul {
    padding-left: 30px;
  }
  ul {
    font-size: 14px;
  }
`
const EditBox = styled.div` // 각 입력칸 기본 포맷
  margin-bottom: 20px;
  padding: 30px 25px;
  background-color: #ffffff;
  border: 1px solid #bbbfc3;
  border-radius: 5px;
  line-height: 135%;
`
const EditTitle = styled.h6` // 각 입력칸 제목 형식
  font-size: 16px;
`
const EditText = styled.p` // 각 입력칸 설명 형식
  font-size: 13px;
  line-height: 115%;
  margin-bottom: 10px;
`
const EditInput = styled.input` // 각 입력칸 인풋 형식
  padding-left: 1%;
  width: 100%;
  height: 32px;
  border: 1px solid #bbbfc3;
  border-radius: 3px;
`
const ReviewButton = styled.button` // 질문 등록 버튼
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
const DiscardButton = styled.button` // 질문 작성 취소(삭제) 버튼
  padding: 10px 13px;
  border-radius: 4px;
  border: 0px solid;
  text-align: center;
  font-size: 14px;
  font-weight: 400;
  background-color: #fff;
  color: #910000;
  cursor: pointer;
  &:hover {
    background-color: #FBF2F2;
  }
`


// ----- 컴포넌트 영역

function AskQuestion() {
  const [problemText, setProblemText] = useState(""); // 질문 내용 입력칸의 상태 관리창
  const [expectText, setExpextText] = useState("")

  return (
    <AskBox>
      <h1>Ask a public question</h1>
      <BuleBox>
        <h2>Writing a good question</h2>
        <p>You’re ready to <a href="https://stackoverflow.com/help/how-to-ask">ask</a> a <a href="https://stackoverflow.com/help/on-topic">programming-related question</a> and this form will help guide you through the process. <br />Looking to ask a non-programming question? See <a href="https://stackexchange.com/sites#technology">the topics here</a> to find a relevant site.</p>
        <h5>Steps</h5>
        <ul>
          <li>Summarize your problem in a one-line title.</li>
          <li>Describe your problem in more detail.</li>
          <li>Describe what you tried and what you expected to happen.</li>
          <li>Add “tags” which help surface your question to members of the community.</li>
          <li>Review your question and post it to the site.</li>
        </ul>
      </BuleBox>
      <EditBox className="Titlebox">
        <EditTitle>Title</EditTitle>
        <EditText>Be specific and imagine you’re asking a question to another person.</EditText>
        <EditInput></EditInput>
      </EditBox>

      <EditBox>
        <EditTitle>What are the details of your problem?</EditTitle>
        <EditText>Introduce the problem and expand on what you put in the title. Minimum 20 characters.</EditText>
        <ReactQuill 
          theme="snow"
          value={problemText}
          onChange={(e) => setProblemText(e)} 
          />
      </EditBox>

      <EditBox>
        <EditTitle>What did you try and what were you expecting?</EditTitle>
        <EditText>Describe what you tried, what you expected to happen, and what actually resulted. Minimum 20 characters.</EditText>
        <ReactQuill 
          theme="snow"
          value={expectText}
          onChange={(e) => setExpextText(e)} 
          />
      </EditBox>

      <EditBox>
        <EditTitle>Tags</EditTitle>
        <EditText>Add up to 5 tags to describe what your question is about. Start typing to see suggestions.</EditText>
        <EditInput></EditInput>
      </EditBox>

      <EditBox>
        <EditTitle>Review questions already on Stack Overflow to see if your question is a duplicate.</EditTitle>
        <EditText>Clicking on these questions will open them in a new tab for you to review. Your progress here will be saved so you can come back and continue.</EditText>
        <EditInput></EditInput>
        <ReviewButton>Review your question</ReviewButton>
      </EditBox>
      <DiscardButton>Discard draft</DiscardButton>
    </AskBox>
  )
}

export default AskQuestion;