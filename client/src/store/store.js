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

export const { setIsLogin } = isLogin.actions;

export default configureStore({
    reducer: {
        isLogin: isLogin.reducer,
    },
    middleware: (getDefaultMiddleware) => getDefaultMiddleware({
        serializableCheck: false,
    }),
})