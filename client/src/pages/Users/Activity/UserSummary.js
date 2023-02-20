import styled from "styled-components";

export const SummaryContainer = styled.div`

`

export const Title = styled.span`
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
    width: 1000px;
    height: 250px;
`

export const AnswerContent = styled(CardContent)`
    width: 450px;
    height: 100px;
`

export const QuestionContent = styled(CardContent)`
    width: 450px;
    height: 100px;
`

export const TagContent = styled(CardContent)`
    width: 450px;
    height: 100px;
`

export const ReputationContent = styled(CardContent)`
    width: 450px;
    height: 100px;
`

function UserSummary() {
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
                    <QuestionContent></QuestionContent>
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