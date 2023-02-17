import { BrowserRouter, Link, Route, Routes, useNavigate } from "react-router-dom";
import styled from "styled-components";
import UsersActivity from "./UsersActivity";
import UsersProfile from "./UsersProfile";

export const UsersPage = styled.div`
    display: flex;
    flex-direction: column;
    /* width: 100vw; */
    background-color: #eee;
`
export const UsersTop = styled.div`
    height: 144px;
    /* background-color: black; */
`

export const UsersMain = styled.div`
    display: flex;
    flex-direction: row;
`
export const UsersMenu = styled.nav`
    height: 40px;
    ul {
        display: flex;
        li {
            /* background-color: black; */
            list-style: none;
            width: 80px;
            text-align: center;
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
    }
`



function Users() {
    const navigate = useNavigate();
    return (
        <UsersPage>
            <UsersTop>
                USER 기본 정보가 들어갈 곳
            </UsersTop>
                <UsersMenu>
                    <ul>
                        <li onClick={()=>navigate("/profile")}>
                            Profile
                        </li>
                        <li onClick={()=>navigate("/activity")}>
                            Activity
                        </li>
                    </ul>
                </UsersMenu>
                <UsersMain>
                    <Routes>
                        <Route path="/profile" element={<UsersProfile/>}/>
                        <Route path="/activity" element={<UsersActivity/>}/>
                    </Routes>
                </UsersMain>
        </UsersPage>
    )
}

export default Users;