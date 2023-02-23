import styled from "styled-components";

export const TagContainer = styled.div`
`

export const Title = styled.span`
`

export const CardContent = styled.div`
    border: 1px solid #eee;
    border-radius: 10px;
`

export const TagContent = styled(CardContent)`
    width: 800px;
    height: 250px;
`

function UserTags() {
    return (
        <TagContainer>
            <Title>Tags</Title>
            <TagContent>

            </TagContent>
        </TagContainer>
    )
}

export default UserTags;