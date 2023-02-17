import axios from "axios";
import { useState } from "react";
import { BrowserRouter } from "react-router-dom";
import styled from "styled-components";

export const RegisterPage = styled.div`
    display: flex;
    flex-direction: row;
    background-color: #eee;
    height: 100vh;
    justify-content: center;

`
export const Explaination = styled.div`
`
export const RegisterContainer = styled.div`
    background-color: #eee;
    width: 280px;
    height: 100%;

`
export const SocialContainer = styled.div`
    display: flex;
    flex-direction: column;
`
export const Google = styled.button`
`
export const GitHub = styled.button`
`
export const Facebook = styled.button`
`
export const InputContainer = styled.form`
    display: flex;
    flex-direction: column;
    background-color: white;
    border-radius: 10px;
    width: 100%;
    padding: 24px;
`
export const Bottom = styled.div`
    display: flex;
    justify-content: center;
`

function Register() {
    const [displayName, setDisplayName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const displayNameHandler = (e) => {
        setDisplayName(e.target.value);
    }

    const emailHandler = (e) => {
        setEmail(e.target.value);
    }

    const passwordHandler = (e) => {
        setPassword(e.target.value)
    }

    const registerHandler = () => {
        if(displayName === "" || email === "" || password === "") return;
        const registerData = {
            name: displayName,
            email,
            password
        }
        axios.post(``, registerData)
    }

    return (
        <RegisterPage>
            <Explaination>
                asdfasdfasdf
            </Explaination>
            <RegisterContainer>
                <SocialContainer>
                    <Google>Sign up with Google</Google>
                    <GitHub>Sign up with GitHub</GitHub>
                    <Facebook>Sign up with Facebook</Facebook>
                </SocialContainer>
                <InputContainer onSubmit={(e) => e.preventDefault()}>
                    <span>Display name</span>
                    <input type="text" value={displayName} onChange={(e) => displayNameHandler(e)} required></input>
                    <span>Email</span>
                    <input type="text" value={email} onChange={(e) => emailHandler(e)} required></input>
                    <span>Password</span>
                    <input type="password" value={password} onChange={(e) => passwordHandler(e)} required></input>
                    <button type="submit" onClick={registerHandler}>Sign Up</button>
                </InputContainer>
                <Bottom>
                    <span>Already have an account? <a href="/">Log in</a></span>
                </Bottom>
            </RegisterContainer>
        </RegisterPage>
    )
}

export default Register;