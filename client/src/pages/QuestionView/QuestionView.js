// 개별 글을 볼 수 있는 컴포넌트입니다.

// ----- 필요 라이브러리
import styled from "styled-components";
import Article from "./Article";
import Answer from "./Answer";
import RightSideBar from "../../components/Main/RightSideBar";
import ArticleHeader from "./ArticleHeader";
import { useEffect, useState } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import { getAccessToken } from "../../storage/cookie";

// ----- 컴포넌트 및 이미지 파일

// ----- CSS 영역
const ViewBox = styled.div` // 묻는 공간 전체 창
  margin-left: 20px;
`
const ContentsBox = styled.div`
  display: flex;
  max-width: 1200px;
`
const Contents = styled.div`
`

// ----- 컴포넌트 영역
function QuestionView() {
  const [QuestionData, setQuestionData] = useState("");
  const { id } = useParams();
  const [isPending, setIsPending] = useState(true);
  const [isUpdated, setIsUpdated] = useState(false);

  useEffect(() => {
      axios.get(`${process.env.REACT_APP_API_URL}/questions/${id}`,{
        headers: {
          Authorization: getAccessToken(),
        }
      })
      .then((res) => {
        const { data } = res;
        setIsPending(false);
        setQuestionData(data);
        // console.log(QuestionData)
      });
  }, [isUpdated]);
  // console.log(QuestionData)

  return (
      <ViewBox>
          {!isPending && (
              <>
                  <ArticleHeader QuestionData={QuestionData} />
                  <ContentsBox>
                      <Contents>
                          <Article QuestionData={QuestionData} setIsUpdated={setIsUpdated}/>
                          <Answer
                              AnswerData={QuestionData.data.answerResponseDtos}
                          />
                      </Contents>
                      <RightSideBar />
                  </ContentsBox>
              </>
          )}
      </ViewBox>
  );
}

export default QuestionView

