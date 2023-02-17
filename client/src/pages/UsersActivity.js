import styled from "styled-components";

export const ProfileContainer = styled.div`
    display: flex;
    flex-direction: row;
    height: 1000px;
`
export const ProfileLeft = styled.div`
    display: flex;
    flex-direction: column;
`

export const ProfileRight = styled.div`
`

export const Stats = styled.div`
`

export const Community = styled.div`
`

function UsersActivity() {
    return (
        <ProfileContainer>
            <ProfileLeft></ProfileLeft>
            <ProfileRight></ProfileRight>
            USER ACTIVITY PAGE
        </ProfileContainer>
    )
}

export default UsersActivity;