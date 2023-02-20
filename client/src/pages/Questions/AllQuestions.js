// 모든 질문이 모여 있는 페이지입니다. 질문 상단바와 모든 질문으로 구성되어 있습니다.

// ----- 필요 라이브러리
import styled from "styled-components";
import AllQuestionHeader from "../../components/Header/AllQuestionHeader";

// ----- 컴포넌트 및 이미지 파일
import QuestionItem from "../../components/Questions/QuestionItem";
import RightSideBar from "../../components/Main/RightSideBar";

// ----- CSS 영역
const QuestionsBox = styled.div`
    // 전체 박스 구성
    align-items: center;
    max-width: 800px;
    padding-top: 40px;
`;
const QustionList = styled.div`
    // 질문 목록 박스
`;

function AllQuestions() {
    return (
        <>
            <QuestionsBox>
                <AllQuestionHeader />
                <QustionList>
                    <QuestionItem />
                </QustionList>
            </QuestionsBox>
            <RightSideBar />
        </>
    );
}

export default AllQuestions;
