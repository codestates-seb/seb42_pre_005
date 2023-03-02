import styled from "styled-components";
import { BiSearchAlt, BiLock } from "react-icons/bi";

const Allbox = styled.div`
  margin: 30px;
  text-align: center;
  justify-content: center;
  .blackback {
    border-radius: 10px;
    background-color: #232629;
  }
`
const FirstBox = styled.div` // 첫번째 말풍선 박스 정렬
  display: flex;
  justify-content: center;
  margin: 30px 0px;
`
const SecondBox = styled(FirstBox)` // 두번째 설명 박스 4개 정렬
`
const ThirdBox = styled(FirstBox)` // 세번째 스택오버플로우&팀박스 정렬
  .sortRow {
    button {
      padding: 15px 15px;
    }
  }
`
const MainTitle = styled.h1` // 가장 큰 제목 스타일링
  font-size: 50px;
  font-weight: 800;
  color: #fff;
  margin: 30px 0px;
`
const ContentsBox = styled.div` // 첫번째, 세번째 박스 스타일링
  margin: 20px;
  padding: 30px;
  width: ${(props) => props.width || "370px"};
  border-radius: 10px;
  background-color: ${(props) => props.color || "white"};
  h2 {
    font-size: 24px;
    line-height: 28px;
    margin-bottom: 10px;
  }
  h3 {
    font-size: 18px;
    line-height: 24px;
    font-weight: 400;
    margin-bottom: 15px;
  }
  .icons {
    font-size: 50px;
    margin-bottom: 10px;
    color: ${(props) => props.color || "#222528"};
  }
  p {
    margin-top: 15px;
    font-size: 13px;
  }
  img {
    height: 240px;
  }
`
const ContentsButton = styled.button` // 모든 버튼 스타일링, color를 props로 받아 적용
  color: #fff;
  font-size: 15px;
  border: none;
  border-radius: 5px;
  padding: 15px 32px;
  margin: 0px 5px;
  background-color: ${(props) => props.color || "#222528"};
`
const SubComtentsBox = styled.div` // 두번째 4개 설명 스타일링
  width: 200px;
  margin: 10px;
  h4 {
    color: #fff;
    font-size: 18px;
    line-height: 32px;
    font-weight: 600;
  }  
  p {
    color: gray;
    font-size: 15px;
    line-height: 20px;
  }
`

const VisitorMain = () => {
  return (
    <Allbox>
      <div className="blackback">
      <FirstBox>
        <ContentsBox color="#fee3cd">
          <BiSearchAlt className="icons" color="#EC5E0F"/>
          <h3>Find the best answer to your technical question, help others answer theirs</h3>
          <ContentsButton color="#EC5E0F">
            Join the community
          </ContentsButton>
          <p>or search content</p>
        </ContentsBox>
        <ContentsBox color="#C3E3FE">
          <BiLock className="icons" color="#127FFE" />
          <h3>Want a secure, private space for your technical knowledge?</h3>
          <ContentsButton color="#127FFE">
            Discover Teams
          </ContentsButton>
        </ContentsBox>
      </FirstBox>
      
      <MainTitle>Every developer has a <br />tab open to Stack Overflow</MainTitle>

      <SecondBox>
        <SubComtentsBox>
          <h4>100+ million</h4>
          <p>monthly visitors to Stack Overflow & Stack Exchange</p>
        </SubComtentsBox>
        <SubComtentsBox>
          <h4>45.1+ billion</h4>
          <p>Times a developer got help since 2008</p>
        </SubComtentsBox>
        <SubComtentsBox>
          <h4>191% ROI</h4>
          <p>from companies using Stack Overflow for Teams</p>
        </SubComtentsBox>
        <SubComtentsBox>
          <h4>5,000+</h4>
          <p>Stack Overflow for Teams instances active every day</p>
        </SubComtentsBox>
      </SecondBox>

      <ThirdBox>
      <ContentsBox width="450px">
          <img src="https://cdn.sstatic.net/Img/home/illo-public.svg" />
          <h2>A public platform building the definitive collection of coding questions & answers</h2>
          <h3>A community-based space to find and contribute answers to technical challenges, and one of the most popular websites in the world.</h3>
          <ContentsButton color="#EC5E0F">
            Join the community
          </ContentsButton>
          <p>or search content</p>
        </ContentsBox>
        <ContentsBox width="420px">
          <img src="https://cdn.sstatic.net/Img/home/illo-teams.svg" />
          <h2>A private collaboration & knowledge sharing SaaS platform for companies</h2>
          <h3>A web-based platform to increase productivity, decrease cycle times, accelerate time to market, and protect institutional knowledge.</h3>
          <div className="sortRow">
            <ContentsButton color="#127FFE">For large organizations</ContentsButton>
            <ContentsButton color="#127FFE">For small teams</ContentsButton>
          </div>
        </ContentsBox>
      </ThirdBox>
      </div>

      {/* <div className="whitebox">
        <h2>Thousands of organizations around the globe use Stack Overflow for Teams</h2>
      </div> */}
    </Allbox>
  )
}

export default VisitorMain;