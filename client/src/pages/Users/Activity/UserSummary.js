import axios from "axios";
import { useEffect, useState } from "react";
import styled from "styled-components";
import Paging from "../../../components/Pagination";
import { getAccessToken } from "../../../storage/cookie";

export const SummaryContainer = styled.div`

`

export const Title = styled.span`
    margin-bottom: 6px;
`

export const FirstSection = styled.div`
`

export const SecondSection = styled.div`
    display: flex;
    flex-direction: row;
`

export const ThirdSection = styled.div`
    display: flex;
    flex-direction: row;
`

export const SummaryCard = styled.div`
`

export const AnswerCard = styled.div`
`

export const QuestionCard = styled.div`
`

export const TagCard = styled.div`
`

export const ReputationCard = styled.div`
`

export const CardContent = styled.div`
    border: 1px solid #eee;
    border-radius: 10px;
`

export const SummaryContent = styled(CardContent)`
    width: 800px;
    height: 200px;
`

export const AnswerContent = styled(CardContent)`
    width: 400px;
    height: 100px;
`

export const QuestionContent = styled(CardContent)`
    width: 400px;
    height: 100px;
`

export const TagContent = styled(CardContent)`
    width: 400px;
    height: 100px;
`

export const ReputationContent = styled(CardContent)`
    width: 400px;
    height: 100px;
`

function UserSummary() {
    const [myQuestions, setMyQuestions] = useState(null);
    const [isPending, setIsPending] = useState(true);
    
    const [page, setPage] = useState(1); // 페이지 정보 가져오기
    const [size, setSize] = useState(5); //  한 페이지에 보여줄 아이템 수
    const [total, setTotal] = useState(); // 전체 아이템 수

    useEffect(() => {
        axios.get(`${process.env.REACT_APP_API_URL}/questions/my?page=${page}&size=${size}`,{
            headers: {
                Authorization: getAccessToken()
            }
        })
        .then((res) => {
            setMyQuestions(res.data.data);
            setTotal(res.data.data.length);
            setIsPending(false);
        })

    }, [])
    
    return (
        <SummaryContainer>
            <FirstSection>
                <SummaryCard>
                    <Title>Summary</Title>
                    <SummaryContent>

                    </SummaryContent>
                </SummaryCard>
            </FirstSection>
            <SecondSection>
                <AnswerCard>
                    <Title>Answers</Title>
                    <AnswerContent></AnswerContent>
                </AnswerCard>
                <QuestionCard>
                    <Title>Questions</Title>
                    <QuestionContent>
                        {!isPending && (myQuestions.length ? myQuestions.map(question => {
                            return (
                                <>
                                    <h1>{question.title}</h1>
                                </>
                            )
                        }): <span>질문한 내역이 없습니다.</span>)}
                        {!isPending && (myQuestions.length ?
                        <Paging total={total} size={size} page={page} setPage={setPage}/>
                        : null)}
                    </QuestionContent>
                </QuestionCard>
            </SecondSection>
            <ThirdSection>
                <TagCard>
                    <Title>Tags</Title>
                    <TagContent></TagContent>
                </TagCard>
                <ReputationCard>
                    <Title>Reputation</Title>
                    <ReputationContent></ReputationContent>
                </ReputationCard>
            </ThirdSection>
        </SummaryContainer>
    )
}

export default UserSummary;