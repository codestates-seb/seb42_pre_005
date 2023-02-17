// ----- 필요 라이브러리
import { BrowserRouter, Routes, Route } from "react-router-dom";


// ----- 컴포넌트 및 이미지 파일
import './App.css';
import Header from './component/Header/Header';
import Main from "./component/Main/Main"
import AskQuestion from "./page/AskQuestion";
import Tags from './component/Main/Tags/Tags';
import Users from './component/Main/Users/Users';

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