// ----- 필요 라이브러리
import { BrowserRouter, Routes, Route } from "react-router-dom";


// ----- 컴포넌트 및 이미지 파일
import './App.css';
import Header from './components/Header/Header';
import Main from "./components/Main/Main"
import AskQuestion from "./pages/AskQuestion";
import Tags from './components/Main/Tags/Tags';
import Users from './components/Main/Users/Users';

function App() {
  return (
    <BrowserRouter>
      <Header />
      <Routes>
        <Route path="/" element={<Main />} />
        <Route path="/tags" element={<Tags />} />
        <Route path="/users" element={<Users />} />
        <Route path="/ask" element={<AskQuestion />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;