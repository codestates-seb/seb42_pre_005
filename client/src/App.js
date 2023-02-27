import './App.css';

import { Routes, Route, useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";

import Main from './pages/Main/Main';
import Header from './components/Header/Header';

import Login from './pages/Users/Login';
import Register from './pages/Users/Register';

import Users from './pages/Users/Users';
import UserDetail from './pages/Users/UserDetail';

import Tags from './pages/Tags/Tags';

import AllQuestions from './pages/Questions/AllQuestions';
import TopQuestions from './pages/Questions/TopQuestions';
import AskQuestion from './pages/Questions/AskQuestion';
import QuestionView from './pages/QuestionView/QuestionView';
import NotFound from './components/NotFound';
import EditQuestion from './pages/Questions/EditQustions';
import { useEffect } from 'react';
import { getAccessToken } from './storage/cookie';




function App() {
  const dispatch = useDispatch();
  const isLogin = useSelector(state => state.isLogin);
  const userData = useSelector(state => state.userData);

  const navigate = useNavigate();
  
  const authHandler = () => {
    const accessToken = getAccessToken();
    if(accessToken === undefined) {

    }else{
        
    }
  }

  useEffect(() => {
    if(userData === null) {
      navigate("/login")
    }
  },[])

  return (
    <div className="App">
      <Header />
      <Routes>
        <Route path="/" element={<Main />}>
          <Route path="/" element={<TopQuestions/>}/>
          <Route path="/questions" element={<AllQuestions/>}/>
          <Route path="/tags" element={<Tags />} />
          <Route path="/view" element={<QuestionView />} />
          <Route path='/users/:id/:name' element={<UserDetail/>}/>
          <Route path="/users" element={<Users />}/>
          <Route path='/companies' element={<NotFound />} />
          <Route path='/collectives' element={<NotFound />} />
          <Route path='/teams' element={<NotFound />} />
        </Route>
        <Route path="/ask" element={<AskQuestion />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/edit" element={<EditQuestion />} />
      </Routes>
    </div>
  );
}

export default App;
