import { useNavigate } from "react-router-dom";
import styled from "styled-components";

const CardContainer = styled.div`
    display: flex;
    flex-direction: row;
    width: 250px;
    height: 80px;
    span {
        margin-left: 10px;
    }
`

const UserImage = styled.div`
    display: table-cell;
    vertical-align: middle;
    width: 48px;
    height: 48px;
    overflow-y: hidden;
    background-color: yellow;
`

const UserInfo = styled.div`
`

function UserCard({user}) {
    const navigate = useNavigate()
    const random = Math.random()*69+1
    return (
        <CardContainer>
            <UserImage>
                {/* <img alt="user_img" src={`https://i.pravatar.cc/48?img=${random}`}/> */}
            </UserImage>
            <UserInfo>
                <span onClick={() => navigate(`/users/${user.memberId}/${user.name}`)}>{user.name}</span>
            </UserInfo>
        </CardContainer>
    )
}

export default UserCard;