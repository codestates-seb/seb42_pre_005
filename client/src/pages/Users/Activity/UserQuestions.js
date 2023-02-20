import styled from "styled-components";

export const QuestionContainer = styled.div`
`

export const Title = styled.span`
`

export const CardContent = styled.div`
    border: 1px solid #eee;
    border-radius: 10px;
`

export const QuestionContent = styled(CardContent)`
    width: 1000px;
    height: 250px;
`

function UserQuestions() {
    return (
        <QuestionContainer>
            <Title>Questions</Title>
            <QuestionContent>

            </QuestionContent>
        </QuestionContainer>
    )
}

export default UserQuestions;