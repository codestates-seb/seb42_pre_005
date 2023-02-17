import './App.css';

import { Routes, Route } from "react-router-dom";
import Header from './components/Header/Header';
import Main from './pages/Main/Main';
import Tags from './pages/Tags/Tags';
import Users from './pages/Users/Users';
import AskQuestion from './pages/Questions/AskQuestion';
import Login from './pages/Users/Login';
import Register from './pages/Users/Register';



function App() {
  return (
    <div className="App">
      <Header />
      <Routes>
        <Route path="/" element={<Main />} />
        <Route path="/tags" element={<Tags />} />
        <Route path="/users" element={<Users />} />
        <Route path="/ask" element={<AskQuestion />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
      </Routes>
    </div>
  );
}

export default App;
