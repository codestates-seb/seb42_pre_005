import axios from "axios";
import { useState } from "react";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import { getAccessToken } from "../../../storage/cookie";
import { setIsLogin, setLoginUser } from "../../../store/store";

const DeleteBackdrop = styled.div`
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

const DeleteView = styled.div`
    display: flex;
    flex-direction: column;
    background-color: white;
    width: auto;
    height: auto;
    border-radius: 20px;
    justify-content: center;
    padding: 20px;
    section {
        width: 600px;
        padding: 10px;
    }
`

const DeleteConfirmBox = styled.div`
    display: flex;
    justify-content: space-between;
    align-items: flex-end;
    margin-top: 10px;
`

const DeleteButton = styled.button`
    float: right;
    color: white;
    background-color: red;
    padding: 10px;
    :disabled {
        opacity: 0.4;
    }
`

function UserDelete({isOpen, modalHandler, loginUser}) {
    const [isChecked, setIsChecked] = useState(false);
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const checkHandler = () => {
        setIsChecked(!isChecked)
    }

    const deleteHandler = () => {
        axios.delete(`${process.env.REACT_APP_API_URL}/members/${loginUser.id}`,{
            headers: {
                Authorization: getAccessToken()
            }
        })
        .then(()=>{
            modalHandler();
            dispatch(setLoginUser(null));
            dispatch(setIsLogin(false));
            navigate("/");
        })
    }
    return (
        <>
            {isOpen ? (
                <DeleteBackdrop onClick={modalHandler}>
                    <DeleteView
                        onClick={(e) => {
                            e.stopPropagation();
                        }}
                    >
                        <h1>Delete Profile</h1>
                        <hr />
                        <section>
                            프로필을 삭제합니다. 삭제 후에는 복구가 불가능합니다.
                            <DeleteConfirmBox>
                                <label style={{cursor:"pointer"}}>
                                    <input type="checkbox" checked={isChecked} onChange={checkHandler}/>
                                    삭제 관련된 정보를 확인했습니다.
                                </label>
                                <DeleteButton disabled={!isChecked} onClick={deleteHandler}>Delete profile</DeleteButton>
                            </DeleteConfirmBox>
                        </section>
                    </DeleteView>
                </DeleteBackdrop>
            ) : null}
        </>
    );
}

export default UserDelete;