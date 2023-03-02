import { configureStore, createSlice, getDefaultMiddleware } from '@reduxjs/toolkit'

const isLogin = createSlice({
    name: "isLogin",
    initialState: false,
    reducers: {
        setIsLogin(state, data) {
            return data.payload;
        }
    }
})

const loginUser = createSlice({
    name: "loginUser",
    initialState: null,
    reducers: {
        setLoginUser(state, data){
            return data.payload;
        }
    }
})

export const { setIsLogin } = isLogin.actions;
export const { setLoginUser } = loginUser.actions;

export default configureStore({
    reducer: {
        isLogin: isLogin.reducer,
        loginUser: loginUser.reducer,
    },
    middleware: (getDefaultMiddleware) => getDefaultMiddleware({
        serializableCheck: false,
    }),
})