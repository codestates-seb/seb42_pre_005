import { useState } from "react";
import styled from "styled-components";

const EditContainer = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
    width: 100%;
`

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
`
const Title = styled.span`
`
const SubTitle = styled.span`
`
function UserEdit({isOpen, setIsOpen, loginUser}) {
    // const [isOpen, setIsOpen] = useState(false);
    const [userName, setUserName] = useState(loginUser.name);

    const nameHandler = (e) => {
        setUserName(e.target.value);
    }

    const editHandler = () => {

    }

    return (
        <>
        {isOpen ? (
            <EditContainer>
                <EditBackdrop onClick={setIsOpen}>
                    <EditView>
                        <Title>Edit your profile</Title>
                        <hr/>
                        <SubTitle>Public information</SubTitle>
                        Display name
                        <input type="text" value={userName} onChange={(e)=>nameHandler(e)}/>
                        <button onClick={editHandler}>수정하기</button>
                    </EditView>
                </EditBackdrop>
                </EditContainer>
            ) : null}
         </>
    )
}

export default UserEdit;