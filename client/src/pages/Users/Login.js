import { useState } from "react";
import styled from "styled-components";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";

import Logo from "../../icons/Logo.svg";
import { setIsLogin, setLoginUser } from "../../store/store";
import { setAccessToken } from "../../storage/cookie";

const StackoverflowLogo = styled.div` // 스택오버플로우 로고 영역
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 30px 0 24px 0;
  cursor: pointer;
`

export const LoginPage = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 24px;
    height: 93vh;
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
export const SocialButton = styled.button`
    width: 278px;
    height: 37px;
    text-align: center;
    margin: 4px 0;
    border: 1px solid hsl(210deg 8% 85%);
    border-radius: 5px;
`
export const Google = styled(SocialButton)`
    background-color: white;
`
export const GitHub = styled(SocialButton)`
    background-color: hsl(210deg 8% 20%);
    color: white;
`
export const Facebook = styled(SocialButton)`
    background-color: #385499;
    color: white;
`
export const InputContainer = styled.form`
    display: flex;
    flex-direction: column;
    background-color: white;
    border-radius: 5px;
    width: 278px;
    height: 233px;
    padding: 24px;
    margin: 12px 0 24px 0;
    box-shadow: rgba(99, 99, 99, 0.2) 0px 2px 8px 0px;
    input {
        width: 230px;
        height: 32px;
        border: 1px solid hsl(210deg 8% 85%);
        border-radius: 3px;
        margin: 6px 0;
        padding: 7px 8px;
    }
    button {
        width: 230px;
        height: 37px;
        border: 1px solid hsl(210deg 8% 85%);
        border-radius: 3px;
        background-color: #0A95FF;
        color: white;
        margin: 12px 0 0 0;
    }
`
export const Bottom = styled.div`
    display: flex;
    justify-content: center;
`
function Login() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();
    const dispatch = useDispatch();

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
        // console.log(loginData);
        axios.post(process.env.REACT_APP_API_URL+"/auth/login", loginData)
        .then(res => {
            dispatch(setIsLogin(true));
            dispatch(setLoginUser({
                id: res.data.data.memberId,
                name: res.data.data.name,
                email: res.data.data.email
            }))
            // console.log("res token")
            // console.log(res.headers.authorization);
            setAccessToken(res.headers.authorization)
            navigate("/")
        })
        .catch(err => {
            console.log(err);
        })
        // axios.post(`/api/auth/login`, loginData)
        // .then(res => {
        //     console.log(res);
        //     navigate("/")
        // })
    }

    const fakeLoginHandler = () => {
        dispatch(setIsLogin(true));
        dispatch(setLoginUser({
            memberId: 1,
            name: "dev-jq1",
            age: 20,
            email: "test@test.com",
        }))
        navigate("/")
    }

    return (
        <LoginPage>
            <LoginContainer>
                <StackoverflowLogo onClick={() => navigate("/")}>
                    <img src={Logo} alt="logo" />
                </StackoverflowLogo>
                <SocialContainer>
                    <Google>Log in with Google</Google>
                    <GitHub>Log in with GitHub</GitHub>
                    <Facebook>Log in with Facebook</Facebook>
                </SocialContainer>
                <InputContainer onSubmit={(e) => e.preventDefault()}>
                    <span>Email</span>
                    <input type="email" value={email} onChange={emailHandler} required></input>
                    <span>Password</span>
                    <input type="password" value={password} onChange={passwordHandler} required></input>
                    <button type="submit" onClick={loginRequestHandler}>Log in</button>
                </InputContainer>
                <button onClick={fakeLoginHandler}>Fake log in</button>
                <Bottom>
                    <span>Don't have an account? <span style={{color: "#0A95FF", cursor: "pointer"}} onClick={() => navigate('/register')}>Sign up</span></span>
                </Bottom>
            </LoginContainer>
        </LoginPage>
    )
}
export default Login;