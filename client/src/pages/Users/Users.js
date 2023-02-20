import { Outlet, Route, Routes, useNavigate } from "react-router-dom";
import styled from "styled-components";
import LeftSideBar from "../../components/LeftSideBar/LeftSideBar";
import UsersActivity from "./Activity/Activity";
import UsersProfile from "./Profile/Profile";

export const UsersPage = styled.div`
    display: flex;
    flex-direction: row;
    /* width: 100vw; */
    /* background-color: #eee; */
`;
export const UsersTop = styled.div`
    height: 144px;
    width: 80vw;
    background-color: #eee;
`;

export const UsersMain = styled.div`
    display: flex;
    flex-direction: row;
    /* background-color: beige; */
    height: 1000px;
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

function Users() {
    const navigate = useNavigate();
    return (
        <UsersPage>
            <LeftSideBar />
            <ContentsContainer>
                <UsersTop>USER 기본 정보가 들어갈 곳</UsersTop>
                <UsersMenu>
                    <li onClick={() => navigate("/users/profile")}>Profile</li>
                    <li onClick={() => navigate("/users/activity")}>Activity</li>
                </UsersMenu>
                <UsersMain>
                    <Outlet></Outlet>
                </UsersMain>
            </ContentsContainer>
        </UsersPage>
    );
}

export default Users;
