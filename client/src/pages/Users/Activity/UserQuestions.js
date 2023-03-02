import axios from "axios";
import { useEffect, useState } from "react";
import styled from "styled-components";
import Paging from "../../../components/Pagination";
import { getAccessToken } from "../../../storage/cookie";

export const QuestionContainer = styled.div`
`

export const Title = styled.span`
`

export const CardContent = styled.div`
    border: 1px solid #eee;
    border-radius: 10px;
`

export const QuestionContent = styled(CardContent)`
    width: 800px;
    height: 400px;
`

function UserQuestions() {
    const [myQuestions, setMyQuestions] = useState(null);
    const [isPending, setIsPending] = useState(true);
    
    const [page, setPage] = useState(1); // 페이지 정보 가져오기
    const [size, setSize] = useState(36); //  한 페이지에 보여줄 아이템 수
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
        <QuestionContainer>
            <Title>Questions</Title>
            <QuestionContent>
            {!isPending && (myQuestions.length ? myQuestions.map(question => {
                            return (
                                <>
                                    <h1>{question.title}</h1>
                                    <h4>{question.content}</h4>
                                    <hr/>
                                </>
                            )
                        }): <span>질문한 내역이 없습니다.</span>)}
                        {!isPending && (myQuestions.length ?
                        <Paging total={total} size={size} page={page} setPage={setPage}/>
                        : null)}
            </QuestionContent>
        </QuestionContainer>
    )
}

export default UserQuestions;