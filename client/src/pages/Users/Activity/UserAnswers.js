import styled from "styled-components";

export const AnswerContainer = styled.div`
`

export const Title = styled.span`
`

export const CardContent = styled.div`
    border: 1px solid #eee;
    border-radius: 10px;
`

export const AnswerContent = styled(CardContent)`
    width: 800px;
    height: 250px;
`

function UserAnswers() {
    return (
        <AnswerContainer>
            <Title>Answers</Title>
            <AnswerContent>

            </AnswerContent>
        </AnswerContainer>
    )
}

export default UserAnswers;