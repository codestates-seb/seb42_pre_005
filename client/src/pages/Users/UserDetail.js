import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useLocation, useNavigate } from "react-router-dom";
import styled from "styled-components";
import UserActivity from "./Activity/UserActivity";
import UserProfile from "./Profile/UserProfile";
import queryString from 'query-string';


export const UsersPage = styled.div`
    display: flex;
    flex-direction: row;
    /* width: 100vw; */
    /* background-color: #eee; */
`;
export const UsersTop = styled.div`
    display: flex;
    /* height: 144px; */
    /* width: 80vw; */
    /* background-color: #eee; */
`;

export const UserImage = styled.div`
    background-color: yellow;
    width: 128px;
    height: 128px;
`

const UserInfo = styled.div`
    display: flex;
    align-items: center;
    h1 {
        margin-left: 12px;
        font-weight: normal;
    }
`

export const UsersMain = styled.div`
    display: flex;
    flex-direction: row;
    /* background-color: beige; */
    /* height: 1000px; */
`;
export const UsersMenu = styled.ul`
    height: 40px;
    display: flex;
    align-items: center;
    margin: 16px 0;
    li {
        /* background-color: black; */
        display: flex;
        list-style: none;
        width: 80px;
        height: 32px;
        justify-content: center;
        align-items: center;
        padding: 0 12px;
        border-radius: 20px;
        margin-right: 6px;
        :hover {
            background-color: #c5c5c5;
            color: white;
        }
        a {
            text-decoration: none;
        }
        &.current-tab {
            background-color: orange;
            color: white;
        } 
    }
`;

export const ContentsContainer = styled.div`
    padding: 24px;
`;

function UserDetail() {
    const userData = useSelector(state => state.userData);
    const navigate = useNavigate();
    const [currentTab, setCurrentTab] = useState('activity');
    const location = useLocation();
    const activityTabList = ["activity", "summary", "answers", "tags", "questions"]
    
    useEffect(()=>{
        const {tab} = queryString.parse(location.search)
        if(!(tab === "" || tab === undefined)) setCurrentTab(tab);
    },[location.search])

    return (
        <UsersPage>
            <ContentsContainer>
                <UsersTop>
                    <UserImage></UserImage>
                    <UserInfo>
                        <h1>{userData.name}</h1>
                    </UserInfo>
                </UsersTop>
                <UsersMenu>
                    <li onClick={() => navigate(`/users/${userData.id}/${userData.name}?tab=profile`)} className={currentTab === "profile" ? "current-tab" : null }>Profile</li>
                    <li onClick={() => navigate(`/users/${userData.id}/${userData.name}?tab=activity`)} className={currentTab !== "profile" ? "current-tab" : null }>Activity</li>
                </UsersMenu>
                <UsersMain>
                    {activityTabList.includes(currentTab) && <UserActivity currentTab={ currentTab === "activity" ? "summary" : currentTab}/>}
                    {currentTab === "profile" && <UserProfile/>}
                </UsersMain>
            </ContentsContainer>
        </UsersPage>
    );
}

export default UserDetail;
