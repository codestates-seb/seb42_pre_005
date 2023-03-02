import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import styled from "styled-components";
import UserActivity from "./Activity/UserActivity";
import UserProfile from "./Profile/UserProfile";
import queryString from 'query-string';
import axios from "axios";
import { getAccessToken } from "../../storage/cookie";
import UserEdit from "./Edit/UserEdit";
import UserDelete from "./Delete/UserDelete";


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
    display: table-cell;
    vertical-align: middle;
    overflow-y: hidden;
    width: 128px;
    height: 128px;
`

const UserInfo = styled.div`
    display: flex;
    flex-direction: column;
    /* align-items: center; */
    h1 {
        margin-left: 12px;
        font-weight: normal;
    }
`
const EditButton = styled.button`
`

const DeleteButton = styled.button`
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
    const loginUser = useSelector(state => state.loginUser);

    const params = useParams();
    const [userData, setUserData] = useState();
    const [isPending, setIsPending] = useState(true);

    const [isEditOpen, setIsEditOpen] = useState(false);
    const [isDeleteOpen, setIsDeleteOpen] = useState(false);

    const navigate = useNavigate();
    const [currentTab, setCurrentTab] = useState('activity');
    const location = useLocation();
    const activityTabList = ["activity", "summary", "answers", "tags", "questions"]
    
    useEffect(()=>{
        // console.log(params);
        axios.get(`${process.env.REACT_APP_API_URL}/members/${params.id}`,{
            headers : {
                Authorization: getAccessToken(),
            }
        })
        .then(res => {
            setUserData(res.data.data);
            // console.log(userData);
            setIsPending(false);
        })
        const {tab} = queryString.parse(location.search)
        if(!(tab === "" || tab === undefined)) setCurrentTab(tab);
    },[location.search, params])

    const editModalHandler = () => {
        setIsEditOpen(!isEditOpen);
    }

    const deleteModalHandler = () => {
        setIsDeleteOpen(!isDeleteOpen);
    }

    return (
        <UsersPage>
            <ContentsContainer>
                <UsersTop>
                    <UserImage>
                        {/* <img alt="user_img" src="https://i.pravatar.cc/128"/> */}
                    </UserImage>
                    { !isPending &&
                    <UserInfo>
                        {loginUser.id === userData.memberId 
                        ?
                        <>
                            <EditButton onClick={editModalHandler}>Edit Profile</EditButton>
                            <DeleteButton onClick={deleteModalHandler}>Delete Profile</DeleteButton>
                        </>
                        : null }
                        <h1>{userData.name}</h1>
                    </UserInfo>
                    }
                </UsersTop>
                <UsersMenu>
                    <li onClick={() => navigate(`/users/${userData.memberId}/${userData.name}?tab=profile`)} className={currentTab === "profile" ? "current-tab" : null }>Profile</li>
                    <li onClick={() => navigate(`/users/${userData.memberId}/${userData.name}?tab=activity`)} className={currentTab !== "profile" ? "current-tab" : null }>Activity</li>
                    {/* <li onClick={() => navigate(`/users/edit/${userData.memberId}`)} className={currentTab !== "profile" ? "current-tab" : null }>Settings</li> */}
                </UsersMenu>
                <UsersMain>
                    {activityTabList.includes(currentTab) && <UserActivity userData={userData} currentTab={ currentTab === "activity" ? "summary" : currentTab}/>}
                    {currentTab === "profile" && <UserProfile userData={userData}/>}
                </UsersMain>
            </ContentsContainer>
            <UserEdit isOpen={isEditOpen} modalHandler={editModalHandler} loginUser={loginUser}/>
            <UserDelete isOpen={isDeleteOpen} modalHandler={deleteModalHandler} loginUser={loginUser}/>
        </UsersPage>
    );
}

export default UserDetail;
