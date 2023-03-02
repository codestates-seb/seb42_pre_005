// 글 작성란입니다.

// ----- 필요 라이브러리
import styled from "styled-components";
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import axios from "axios";
import { getAccessToken } from "../../storage/cookie";

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
const TagBox = styled.div`
  margin-top: 20px;
`
const Tag = styled.span`
  padding: 5px 6px;
  margin: 5px;
  margin-top: 20px;
  font-size: 13px;
  border-radius: 5px;
  background-color: #EDF4FA;

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
  const navigate = useNavigate();

  const [title, setTitle] = useState(""); // 질문 제목 입력칸의 상태 관리창
  const [value, setValue] = useState(""); // 질문 내용 입력칸의 상태 관리창
  const [tags, setTags] = useState("") // 태그 내용 입력칸의 상태 관리창, 만들긴 했지만 요청 가지는 않음
  const [tagsArr, setTagsArr] = useState([]);

  const askTitle = (e) => { // 질문 제목 입력칸 상태 함수
    setTitle(e.target.value)
  };
  const editTag = (e) => { // 태그 내용 입력칸 상태 함수
    setTags(e.target.value)
  };

  const tagsOnKeyUp = (e) => { // 엔터키 누를 때마다 태그 업데이트
    if((e.keyCode === 13) && tags.length > 0) {
      setTagsArr([...tagsArr, tags.slice()]);
      setTags('')
    }
  }
  

  const ReviewButtonSubmit = (e) => { // 다 쓴 질문 제출 버튼 함수
    e.preventDefault();
    axios.post(`${process.env.REACT_APP_API_URL}/questions`, {
      title : title,
      content : value,
      tagNames : tagsArr,
    },{
      headers: {
        Authorization: getAccessToken()
      }
    })
    .then(res => {
        console.log(res)
        navigate("/questions");
        // 게시하고 나면 해당 게시물 페이지로 넘어가기
      })
      .catch((err) => {
        console.log(err);
        alert("Failed to write. Try again.");
      });
  };

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
        <EditInput value={title} onChange={askTitle} placeholder="e.g. Is there an R function for finding the index of an element in a vector?" />
      </EditBox>

      <EditBox>
        <EditTitle>What are the details of your problem?</EditTitle>
        <EditText>Introduce the problem and expand on what you put in the title. Minimum 20 characters.</EditText>
        <ReactQuill 
          theme="snow"
          value={value} 
          onChange={setValue}
          />
      </EditBox>
      <EditBox>
        <EditTitle>Tags</EditTitle>
        <EditText>Add up to 5 tags to describe what your question is about. Start typing to see suggestions.</EditText>
        <EditInput 
          value={tags}
          onChange={editTag}
          onKeyUp={tagsOnKeyUp}
          placeholder="Enter 키를 누르면 태그가 등록됩니다."
          />
        <TagBox>
          {tagsArr.map((tag, index) => {
                return (             
                <Tag key={index}>
                  {tag}
                </Tag>
                )
              })}
        </TagBox>
      </EditBox>
      <ReviewButton onClick={ReviewButtonSubmit}>Review your question</ReviewButton>
      <DiscardButton onClick={() => navigate("/questions")}>Discard draft</DiscardButton>
    </AskBox>
  );
}

export default AskQuestion;