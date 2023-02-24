import { Cookies } from 'react-cookie';

export const ACCESSTOKEN_TIME_OUT = 600 * 1000;

const cookies = new Cookies();

export const setAccessToken = (accessToken) => {
    const expireTime = new Date().getTime() + ACCESSTOKEN_TIME_OUT;
    return cookies.set('access_token', accessToken, {
        sameSite: 'strict',
        path: "/",
        expires: expireTime
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