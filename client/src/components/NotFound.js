import styled from "styled-components";
import img from "../icons/404NotPound.png";

const NotFoundBox = styled.div`
  display: flex;
  background-color: #F1F2F3;
  flex: auto;
  flex-grow: 1;
  justify-content: center;
  align-items: center;
`
const ContentsBox = styled.div`
  display: flex;
  .textdiv {
    width: 450px;
    margin: 30px;
  }
  h1 {
    font-size: 24px;
    font-weight: 500;
  }
  h2 {
    font-size: 18px;
    font-weight: 400;
    margin-bottom: 20px;
  }
  p {
    font-size: 16px;
    line-height: 180%;
  }
  a {
    color: #2E73C6;
    text-decoration: none;
    :hover {
      color: #8AAFDB;
    }
  }
`

const NotFound = () => {
  return (
  <NotFoundBox>
    <ContentsBox>
    <div className="imgdiv">
      <img src={img} alt="404 not found" width={200}/></div>
    <div className="textdiv">
      <h1>Page not found</h1>
      <h2>We're sorry, we couldn't find the page you requested.</h2>
      <div className="detaildiv">
        <p>Try <a href="https://stackoverflow.com/search">searching for similar questions</a></p>
        <p>Browse our <a href="https://stackoverflow.com/questions">recent questions</a></p>
        <p>Browse our <a href="https://stackoverflow.com/tags">popular tags</a></p>
        <p>If you feel something is missing that should be here, <a href="https://stackoverflow.com/contact">contact us.</a></p>
      </div>
    </div>
    </ContentsBox>
  </NotFoundBox>
  )
}

export default NotFound;
