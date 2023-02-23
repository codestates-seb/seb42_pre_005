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

const userData = createSlice({
    name: "userData",
    initialState: null,
    reducers: {
        setUserData(state, data){
            return data.payload;
        }
    }
})

export const { setIsLogin } = isLogin.actions;
export const { setUserData } = userData.actions;

export default configureStore({
    reducer: {
        isLogin: isLogin.reducer,
        userData: userData.reducer,
    },
    middleware: (getDefaultMiddleware) => getDefaultMiddleware({
        serializableCheck: false,
    }),
})