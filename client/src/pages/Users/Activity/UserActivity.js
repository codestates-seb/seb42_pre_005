import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import styled from "styled-components";
import UserSummary from "./UserSummary";
import UserAnswers from "./UserAnswers";
import UserQuestions from "./UserQuestions";
import UserTags from "./UserTags";

export const ActivityContainer = styled.div`
    display: flex;
    flex-direction: row;
    /* overflow: hidden; */
    /* height: 500px; */
`
export const ActivityLeft = styled.div`
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
            background-color: #c1c1c1;
            color: black;
        }
        a {
            text-decoration: none;
        }
        &.current-tab {
            background-color: #d5d7d9;
            color: black;
        } 
    }
`

export const ActivityRight = styled.div`
`


function UserActivity({currentTab, userData}) {
    const navigate = useNavigate();

    return (
        <ActivityContainer>
            <ActivityLeft>
                <ActivityMenu>
                    <li onClick={() => navigate(`/users/${userData.memberId}/${userData.name}?tab=summary`)} className={currentTab === "summary" ? "current-tab" : null }>Summary</li>
                    <li onClick={() => navigate(`/users/${userData.memberId}/${userData.name}?tab=answers`)} className={currentTab === "answers" ? "current-tab" : null }>Answers</li>
                    <li onClick={() => navigate(`/users/${userData.memberId}/${userData.name}?tab=questions`)} className={currentTab === "questions" ? "current-tab" : null }>Questions</li>
                    <li onClick={() => navigate(`/users/${userData.memberId}/${userData.name}?tab=tags`)} className={currentTab === "tags" ? "current-tab" : null }>Tags</li>
                </ActivityMenu>
            </ActivityLeft>
            <ActivityRight>
                {currentTab === "summary" && <UserSummary/>}
                {currentTab === "answers" && <UserAnswers/>}
                {currentTab === "questions" && <UserQuestions/> }
                {currentTab === "tags" && <UserTags/> }
            </ActivityRight>
        </ActivityContainer>
    )
}

export default UserActivity;