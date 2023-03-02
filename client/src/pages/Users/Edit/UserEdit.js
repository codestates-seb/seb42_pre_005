import axios from "axios";
import { useState } from "react";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import { getAccessToken } from "../../../storage/cookie";
import { setLoginUser } from "../../../store/store";

const EditBackdrop = styled.div`
    background-color: rgba(0, 0, 0, 0.3);
    position: fixed;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 100;
`

const EditView = styled.div`
    display: flex;
    flex-direction: column;
    background-color: white;
    width: 300px;
    height: auto;
    border-radius: 20px;
    justify-content: center;
    padding: 20px;
    section {
        width: 260px;
        border: 1px solid black;
        border-radius: 20px;
        padding: 10px;
    }
`
const Title = styled.span`
    font-size: 20pt;
`
const SubTitle = styled.span`
`
function UserEdit({isOpen, modalHandler, loginUser}) {
    // const [isOpen, setIsOpen] = useState(false);
    const [userName, setUserName] = useState(loginUser.name);
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const nameHandler = (e) => {
        setUserName(e.target.value);
    }

    const editHandler = () => {
        axios.patch(`${process.env.REACT_APP_API_URL}/members/${loginUser.id}`,{
            name: userName
        }, {
            headers: {
                Authorization: getAccessToken()
            }
        })
        .then(res => {
            dispatch(setLoginUser({
                ...loginUser,
                name: userName
            }))
            modalHandler();
            navigate(`/users/${loginUser.id}/${loginUser.name}`)
        })
    }

    return (
        <>
        {isOpen ? (
                <EditBackdrop onClick={modalHandler}>
                    <EditView onClick={(e) => {
                        e.stopPropagation();
                    }}>
                        <Title>Edit your profile</Title>
                        <hr/>
                        <SubTitle>Public information</SubTitle>
                        <section>
                            Display name
                            <input type="text" value={userName} onChange={(e)=>nameHandler(e)}/>
                            <button onClick={editHandler}>수정하기</button>
                        </section>
                    </EditView>
                </EditBackdrop>
            ) : null}
         </>
    )
}

export default UserEdit;