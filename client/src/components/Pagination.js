// 페이지네이션 구현 컴포넌트입니다

// ----- 필요 라이브러리
import React, { useState } from "react";
import styled from "styled-components";
import Pagination from "react-js-pagination";


// ----- 컴포넌트 및 이미지 파일

// ----- CSS 영역
const PageBox = styled.div`
  margin-top: 50px;
  .pagination {
    display: flex;
  }
  a { 
    text-decoration: none;
    color: #3C4044;
  }
  ul {
    list-style: none;
  }
  ul.pagination li {
    font-size: 13px;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 5px 8px;
    border: 1px solid #D6D9DB;
    border-radius: 3px;
    margin-right: 5px;
    :hover {
      background-color: #e1e1e1;
    }
  }

  ul.pagination li.active {
    background-color: #EE6D1D;
    border: 1px solid #EE6D1D;
  }
  ul.pagination li.active a {
    color: white;
    font-weight: 600;
  }
`


// ----- 컴포넌트 영역
function Paging( {total, size, page, setPage} ) {
  const pageHandler = (page) => {
    setPage(page);
  }

  return (
    <PageBox>
      <Pagination 
        activePage={page} // 현재 페이지
        itemsCountPerPage={size} // 한 페이지에 보여줄 아이템 수
        totalItemsCount={total} // 전체 아이템 수
        pageRangeDisplayed={5} // 페이지네이터에서 보여줄 범위
        prevPageText={"Prev"} // 이전 페이지 가기
        nextPageText={"Next"} // 다음 페이지 가기
        firstPageText={'<'} // 첫 페이지 가기
        lastPageText={'>'} // 마지막 페이지 가기
        onChange={pageHandler} // 페이지 바뀔 때 핸들링하는 함수
        />
    </PageBox>
  )
}

export default Paging;