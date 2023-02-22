import './App.css';

import { Routes, Route } from "react-router-dom";

import Main from './pages/Main/Main';
import Header from './components/Header/Header';

import Login from './pages/Users/Login';
import Register from './pages/Users/Register';

import Profile from './pages/Users/Profile/Profile';
import Activity from './pages/Users/Activity/Activity';

import Users from './pages/Users/Users';
import UserSummary from './pages/Users/Activity/UserSummary';
import UserAnswers from './pages/Users/Activity/UserAnswers';
import UserQuestions from './pages/Users/Activity/UserQuestions';
import UserTags from './pages/Users/Activity/UserTags';
import UserDetail from './pages/Users/UserDetail';

import Tags from './pages/Tags/Tags';

import AllQuestions from './pages/Questions/AllQuestions';
import TopQuestions from './pages/Questions/TopQuestions';
import AskQuestion from './pages/Questions/AskQuestion';
import QuestionView from './pages/QuestionView/QuestionView';



function App() {
  return (
    <div className="App">
      <Header />
      <Routes>
        <Route path="/" element={<Main />}>
          <Route path="/" element={<TopQuestions/>}/>
          <Route path="/questions" element={<AllQuestions/>}/>
          <Route path="/tags" element={<Tags />} />
          <Route path="/view" element={<QuestionView />} />
          <Route path="/users" element={<Users />}>
            <Route path="profile" element={<Profile/>}/>
            <Route path="activity" element={<Activity/>}>
              <Route path="summary" element={<UserSummary/>}/>
              <Route path="answers" element={<UserAnswers/>}/>
              <Route path="questions" element={<UserQuestions/>}/>
              <Route path="tags" element={<UserTags/>}/>
            </Route>
          </Route>
        </Route>
        <Route path='/userdetail' element={<UserDetail/>}/>
        <Route path="/ask" element={<AskQuestion />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
      </Routes>
    </div>
  );
}

export default App;
