import { useState } from "react";
import styled from "styled-components";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export const LoginPage = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 24px;
    height: 100vh;
    background-color: #eee;
`
export const LoginContainer = styled.div`
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
export const InputContainer = styled.div`
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
function Login() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const emailHandler = (e) => {
        setEmail(e.target.value);
    }

    const passwordHandler = (e) => {
        setPassword(e.target.value)
    }

    const loginRequestHandler = () => {
        if(email === "" || password === "") return;
        const loginData = {
            email,
            password
        }
        axios.post(``, loginData)

    }
    return (
        <LoginPage>
            <LoginContainer>
                <div>
                    5조 스택오버플로우
                </div>
                <SocialContainer>
                    <Google>Log in with Google</Google>
                    <GitHub>Log in with GitHub</GitHub>
                    <Facebook>Log in with Facebook</Facebook>
                </SocialContainer>
                <InputContainer onSubmit={(e) => e.preventDefault()}>
                    <span>Email</span>
                    <input type="text" value={email} onChange={emailHandler} required></input>
                    <span>Password</span>
                    <input type="password" value={password} onChange={passwordHandler} required></input>
                    <button type="submit" onClick={loginRequestHandler}>Log in</button>
                </InputContainer>
                <Bottom>
                    <span>Don't have an account? <span onClick={() => navigate('/register')}>Sign up</span></span>
                </Bottom>
            </LoginContainer>
        </LoginPage>
    )
}
export default Login;