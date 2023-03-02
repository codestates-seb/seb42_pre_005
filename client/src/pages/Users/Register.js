import axios from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import { Facebook, GitHub, Google } from "./Login";

axios.defaults.withCredentials = true;

export const RegisterPage = styled.div`
    display: flex;
    flex-direction: row;
    background-color: #eee;
    height: 93vh;
    justify-content: center;

`
export const Explaination = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    width: 420px;
    .explain-bottom {
        margin: 10px 0 ;
        font-size: 10pt;
    }
`
export const Title = styled.span`
    font-size: 18pt;
    margin-bottom: 24px;
`
export const SubTitle = styled.span`
    font-size: 12pt;
    margin: 10px 0;
    font-weight: 200;
`
export const RegisterContainer = styled.div`
    background-color: #eee;
    width: auto;
    /* height: 100%; */
    margin: 24px 0;
`
export const SocialContainer = styled.div`
    display: flex;
    flex-direction: column;
    button {
        width: 316px;
    }
`
export const InputContainer = styled.form`
    display: flex;
    flex-direction: column;
    background-color: white;
    border-radius: 5px;
    width: 316px;

    padding: 24px;
    margin: 12px 0 24px 0;
    box-shadow: rgba(99, 99, 99, 0.2) 0px 2px 8px 0px;
    input {
        width: 268px;
        height: 32px;
        border: 1px solid hsl(210deg 8% 85%);
        border-radius: 3px;
        margin: 6px 0;
        padding: 7px 8px;
    }
    button {
        width: 268px;
        height: 37px;
        border: 1px solid hsl(210deg 8% 85%);
        border-radius: 3px;
        background-color: #0A95FF;
        color: white;
        margin: 12px 0 0 0;
    }
    .pw-explain {
        font-size: 8pt;
    }
`
export const Bottom = styled.div`
    display: flex;
    width: 316px;
    justify-content: center;
`

function Register() {
    const [displayName, setDisplayName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [loginUser, setLoginUser] = useState();

    const navigate = useNavigate();

    // const regExPw = "^(?=.*?[A-Za-z](?=.*?\d)[A-Za-z\d]{8,20}$";

    // const regExPw = /^(?=.*[a-zA-Z])((?=.*\d)(?=.*\W)){8,20}$/
    const regExPw = "^(?=.*[0-9])[a-z0-9]+$"

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
       
        console.log(registerData);
        // axios.post(`${process.env.REACT_APP_API_URL}/members`, registerData)
        axios.post(process.env.REACT_APP_API_URL+"/members", registerData)
        .then(res => {
            // setLoginUser(res.data);
            // console.log(res.data);
            navigate("/login")
        })
        // axios.post(`/api/members`, registerData)
        // .then(res => {
        //     // setLoginUser(res.data);
        //     console.log(res.data);
        //     navigate("/login")
        // })
        
    }

    return (
        <RegisterPage>
            <Explaination>
                <Title>Join the Stack Overflow Community</Title>
                <SubTitle>Get unstuck ㅡ ask a question</SubTitle>
                <SubTitle>Unlock new privileges like voting and commenting</SubTitle>
                <SubTitle>Save your favorite tags, filters, and jobs</SubTitle>
                <SubTitle>Earn reputation and badges</SubTitle>
                <span className="explain-bottom">Collaborate and share knowledge with a private group for FREE.</span>
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
                    <input type="email" value={email} onChange={(e) => emailHandler(e)} required minLength="6"></input>
                    <span>Password</span>
                    <input type="password" value={password} minLength="8" maxLength="20" pattern={regExPw} onChange={(e) => passwordHandler(e)} required></input>
                    <span className="pw-explain">Passwords must contain at least eight characters, including at least 1 letter and 1 number.</span>
                    <button type="submit" onClick={registerHandler}>Sign Up</button>
                </InputContainer>
                <Bottom>
                    <span>Already have an account? <span style={{color: "#0A95FF", cursor: "pointer"}} onClick={() => navigate("/login")}>Log in</span></span>
                </Bottom>
            </RegisterContainer>
        </RegisterPage>
    )
}

export default Register;