import styled from "styled-components";

export const ProfileContainer = styled.div`
    display: flex;
    flex-direction: row;
    /* height: 1000px; */
    width: 100%;
`

export const VerticalFirst = styled.div`
    display: flex;
    flex-direction: column;
`

export const VerticalSecond = styled.div`
    display: flex;
    flex-direction: column;
`

const CardContent = styled.div`
    border: 1px solid #eee;
    border-radius: 10px;
`
const Title = styled.span`
`


export const Stats = styled(CardContent)`
    width: 240px;
    height: 150px;
`

export const Community = styled(CardContent)`
    width: 240px;
    height: 150px;
`
export const About = styled(CardContent)`
    width: 800px;
    height: 100px;
`

export const Badge = styled.div`
    display: flex;
    flex-direction: row;
`

export const Gold = styled(CardContent)`
    width: 240px;
    height: 150px;
`
export const Silver = styled(CardContent)`
    width: 240px;
    height: 150px;
`
export const Bronze = styled(CardContent)`
    width: 240px;
    height: 150px;
`


function UserProfile() {
    return (
        <ProfileContainer>
            <VerticalFirst>
                <Title>Stats</Title>
                <Stats>asdf</Stats>
                <Title>Communities</Title>
                <Community>asdf</Community>
            </VerticalFirst>
            <VerticalSecond>
                <Title>About</Title>
                <About>asdf</About>
                <Title>Badges</Title>
                <Badge>
                    <Gold>asdf</Gold>
                    <Silver>asdf</Silver>
                    <Bronze>asdfasdf</Bronze>
                </Badge>
            </VerticalSecond>
        </ProfileContainer>
    )
}

export default UserProfile;