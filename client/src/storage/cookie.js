import { Cookies } from 'react-cookie';

export const ACCESSTOKEN_TIME_OUT = 600 * 1000;

const cookies = new Cookies();

export const setAccessToken = (accessToken) => {
    // const expireTime = new Date().getTime() + ACCESSTOKEN_TIME_OUT;
    // const token = accessToken.replace("Bearer","");
    return cookies.set('access_token', accessToken, {
        domain: 'seb42-pre5.s3-website.ap-northeast-2.amazonaws.com',
        sameSite: 'strict',
        path: "/",
        maxAge: ACCESSTOKEN_TIME_OUT,
        httpOnly: false
    });
}

export const getAccessToken = () => {
    return cookies.get('access_token');
}

export const removeAccessToken = () => {
    return cookies.remove('access_token', {
        sameSite: 'strict',
        path: "/"
    })
}