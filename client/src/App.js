import './App.css';

import { Routes, Route } from "react-router-dom";
import Header from './components/Header/Header';
import Main from "./components/Main/Main"
import AskQuestion from "./pages/AskQuestion";
import Tags from './components/Main/Tags/Tags';
import Users from './components/Main/Users/Users';

function App() {
  return (
    <div className="App">
      <Header />
      <Routes>
        <Route path="/" element={<Main />} />
        <Route path="/tags" element={<Tags />} />
        <Route path="/users" element={<Users />} />
        <Route path="/ask" element={<AskQuestion />} />
      </Routes>
    </div>
  );
}

export default App;
