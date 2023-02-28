// 답변 수정란입니다.

// ----- 필요 라이브러리
import styled from "styled-components";
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import axios from "axios";


// ----- 컴포넌트 및 이미지 파일

// ----- CSS 영역
const AllEditBox = styled.div` // 편집 공간 전체 창
  color: #3b4045;
  max-width: 900px;
  padding: 30px 15px;
  h1 { // Ask a public question 부분
    margin-bottom: 50px;
    font-size: 28px;
    font-weight: 600;
  }
`
const EditBox = styled.div` // 각 입력칸 기본 포맷
margin-bottom: 20px;
  p {
    font-size: 18px;
    margin-bottom: 5px;
    font-weight: 800;
  }
  input {
    padding-left: 1%;
  width: 100%;
  height: 32px;
  border: 1px solid #bbbfc3;
  border-radius: 3px;
  }
`
const SaveButton = styled.button` // 질문 등록 버튼
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
const CancelButton = styled.button` // 질문 작성 취소(삭제) 버튼
  padding: 10px 13px;
  border-radius: 4px;
  border: 0px solid;
  text-align: center;
  font-size: 14px;
  font-weight: 400;
  background-color: #fff;
  color: rgb(10, 149, 255);
  cursor: pointer;
  &:hover {
    background-color: #FBF2F2;
  }
`

// ----- 컴포넌트 영역

function EditAnswer() {
  const navigate = useNavigate();
  const [value, setValue] = useState("text"); // 질문 내용 입력칸의 상태 관리창
  
  const EditButtonSubmit = (e) => { // 다 수정한 질문 제출 버튼 함수
    axios.patch(`${process.env.REACT_APP_API_URL}/answers/{answer-id}`, {
      content : value,
    })
    .then(res => {
        console.log(res)
        navigate(`/questions`);
        // 게시하고 나면 해당 게시물 페이지로 넘어가기
      })
      .catch((err) => {
        console.log(err);
        alert("Failed!");
      });
  }
  return (
    <AllEditBox>
      <EditBox className="body">
        <p>Body</p>
        <ReactQuill 
          theme="snow"
          value={value} 
          onChange={setValue}
          />
      </EditBox>
      <SaveButton onClick={EditButtonSubmit}>Save edits</SaveButton>
      <CancelButton onClick={() => navigate("/questions")}>Cancel</CancelButton>
    </AllEditBox>
  )
}

export default EditAnswer;