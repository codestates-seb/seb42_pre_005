import { Outlet, useNavigate } from "react-router-dom";
import styled from "styled-components";

export const ProfileContainer = styled.div`
    display: flex;
    flex-direction: row;
    height: 1000px;
`
export const ProfileLeft = styled.div`
    display: flex;
    flex-direction: column;
    width: 125px;
    margin-right: 32px;
`

export const ActivityMenu = styled.ul`
    width: 125px;
    li {
        /* background-color: black; */
        display: flex;
        list-style: none;
        width: 125px;
        justify-content: left;
        align-items: center;
        padding: 6px 12px 6px 18px;
        border-radius: 20px;
        color: #525960;
        :hover {
            background-color: #F1F2F3;
            color: black;
        }
        a {
            text-decoration: none;
        }
    }
`

export const ProfileRight = styled.div`
`


function UsersActivity() {
    const navigate = useNavigate();
    return (
        <ProfileContainer>
            <ProfileLeft>
                <ActivityMenu>
                    <li onClick={() => navigate("/users/activity/summary")}>Summary</li>
                    <li onClick={() => navigate("/users/activity/answers")}>Answers</li>
                    <li onClick={() => navigate("/users/activity/questions")}>Questions</li>
                    <li onClick={() => navigate("/users/activity/tags")}>Tags</li>
                </ActivityMenu>
            </ProfileLeft>
            <ProfileRight>
                <Outlet></Outlet>
            </ProfileRight>
        </ProfileContainer>
    )
}

export default UsersActivity;