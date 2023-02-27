// ----- 필요 라이브러리
import styled from "styled-components";
import { RiPencilFill, RiChat2Line, RiStackOverflowFill } from "react-icons/ri";

// ----- 컴포넌트 및 이미지 파일

// ----- CSS 영역
const RightSideBarBox = styled.div` // 오른쪽 사이드바 전체 박스
  width: 300px;
  min-width: 300px;
  max-width: 300px;
  margin-left: 25px;
`
const YellowBox = styled.div` // 노란색 전체 박스
  background-color: #FCF7E4;
  border-radius: 10px;
  border: 1px solid #EFE6C0;
  padding-bottom: 10px;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05), 0 1px 4px rgba(0, 0, 0, 0.05), 0 2px 8px rgba(0, 0, 0, 0.05);
`
const YellowTitle = styled.div` // 노란색 박스 타이틀
  background-color: #FAF3D8;
  padding: 15px;
  background-color: beige;
  margin-bottom: 15px;
  border-top: 1px solid #EFE6C0;
  border-bottom: 1px solid #EFE6C0;
  font-size: 15px;
  font-weight: 600;
  color: #3f3f3f;
`
const YellowContents = styled.div` // 노란색 박스 내용
  display: flex;
  padding: 0px 15px;
  margin-bottom: 10px;
  
  .icons {
    margin-right: 5px;
  }
  a {
    color: #3f3f3f;
    font-size: 14px;
    text-decoration: none;
  }
`

// ----- 컴포넌트 영역
const Contents = { // 박스에 들어갈 아이템들 목록. 맵으로 뿌려줄 예정
  Blog: [
    {
      icon: "pencil",
      href: "https://stackoverflow.blog/2023/02/19/developer-with-adhd-youre-not-alone/?cb=1",
      content: "Developer with ADHD? You’re not alone.",
    },
    {
      icon: "pencil",
      href: "https://stackoverflow.blog/2023/02/20/are-companies-shifting-away-from-public-clouds/?cb=1",
      content: "Are clouds having their on-prem moment?",
    },
  ],
  Featured: [
    {
      icon: "chat",
      href: "https://meta.stackexchange.com/questions/386681/ticket-smash-for-status-review-tag-part-deux?cb=1",
      content: "Ticket smash for [status-review] tag: Part Deux",
    },
    {
      icon: "chat",
      href: "https://meta.stackexchange.com/questions/386727/weve-added-a-necessary-cookies-only-option-to-the-cookie-consent-popup?cb=1",
      content: `We've added a "Necessary cookies only" option to the cookie consent popup`,
    },
    {
      icon: "stack",
      href: "https://meta.stackoverflow.com/questions/423096/we-ve-made-changes-to-our-privacy-notice-for-collectives?cb=1",
      content: "We’ve made changes to our Privacy Notice for Collectives™",
    },
    {
      icon: "stack",
      href: "https://meta.stackoverflow.com/questions/345643/the-amazon-tag-is-being-burninated?cb=1",
      content: "The [amazon] tag is being burninated",
    },
    {
      icon: "stack",
      href: "https://meta.stackoverflow.com/questions/423231/microsoft-azure-collective-launch-and-proposed-tag-changes?cb=1",
      content: "Microsoft Azure Collective launch and proposed tag changes",
    },
    {
      icon: "stack",
      href: "https://meta.stackoverflow.com/questions/421831/temporary-policy-chatgpt-is-banned?cb=1",
      content: "Temporary policy: ChatGPT is banned",
    },
  ],
};

export const ContentsItem = function ({ item }) { // 아이템+내용 뿌려줄 함수
  return (
    <YellowContents>
      <div className="icons">
        {item.icon === "pencil" && <RiPencilFill width="14" height="14"/>}
        {item.icon === "chat" && <RiChat2Line width="14" height="14"/>}
        {item.icon === "stack" && <RiStackOverflowFill width="14" height="14"/>}
      </div>
      <div className="content">
        <a href={item.href}>{item.content}</a>
      </div>
    </YellowContents>
  )
}

function RightSideBar() {
  return (
    <RightSideBarBox>
      <YellowBox>
        <YellowTitle>The Overflow Blog</YellowTitle>
          {Contents.Blog.map((el,idx) => (
            <ContentsItem item={el} key={idx} />))}
        <YellowTitle>Featured on Meta</YellowTitle>
          {Contents.Featured.map((el,idx) => (
            <ContentsItem item={el} key={idx} />))}
      </YellowBox>
    </RightSideBarBox>
  )
}

export default RightSideBar;