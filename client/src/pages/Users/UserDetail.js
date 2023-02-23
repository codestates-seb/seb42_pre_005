import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { Outlet, useLocation, useNavigate } from "react-router-dom";
import styled from "styled-components";
import UserActivity from "./Activity/UserActivity";
import UserProfile from "./Profile/UserProfile";
import queryString from 'query-string';
import { current } from "@reduxjs/toolkit";


export const UsersPage = styled.div`
    display: flex;
    flex-direction: row;
    /* width: 100vw; */
    /* background-color: #eee; */
`;
export const UsersTop = styled.div`
    height: 144px;
    /* width: 80vw; */
    background-color: #eee;
`;

export const UsersMain = styled.div`
    display: flex;
    flex-direction: row;
    /* background-color: beige; */
    /* height: 1000px; */
`;
export const UsersMenu = styled.ul`
    height: 40px;
    display: flex;
    margin: 16px 0 16px 0;
    li {
        /* background-color: black; */
        display: flex;
        list-style: none;
        width: 80px;
        justify-content: center;
        align-items: center;
        padding: 6px 12px;
        border-radius: 20px;
        :hover {
            background-color: orange;
            color: white;
        }
        a {
            text-decoration: none;
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
                <UsersTop>USER 기본 정보가 들어갈 곳</UsersTop>
                <UsersMenu>
                    <li onClick={() => navigate(`/users/${userData.id}/${userData.name}?tab=profile`)}>Profile</li>
                    <li onClick={() => navigate(`/users/${userData.id}/${userData.name}?tab=activity`)}>Activity</li>
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
