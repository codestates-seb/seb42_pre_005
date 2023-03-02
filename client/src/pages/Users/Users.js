import axios from "axios";
import { useEffect, useState } from "react";
import { MdSearch } from "react-icons/md";
import styled from "styled-components";
import Paging from "../../components/Pagination";
import UserCard from "../../components/Users/UserCard";

export const UsersPage = styled.div`
    display: flex;
    flex-direction: column;
    width: 80%;
`;

export const UsersHeader = styled.div``;

export const UsersContent = styled.div``;

const UserList = styled.div`
    grid-template-columns: repeat(4, minmax(0, 1fr));
    display: grid;
    gap: 12px;
`

const SubBox = styled.div`
    // 검색창 및 분류 sort
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding-bottom: 15px;
`;

const Search = styled.div`
    // 검색 영역
    display: flex;
    align-items: center;
    border: 1px solid #bbbfc3;
    box-sizing: border-box;
    border-radius: 3px;
    height: 32px;
    padding-left: 5px;
    width: 30%;

    input {
        all: unset;
        font-size: 14px;
        padding-left: 1%;
        &.input-actived {
            box-shadow: 0 0 5px 4px rgba(95, 180, 255, 0.4);
        }
    }
`;

export const ContentsContainer = styled.div`
    padding: 24px;
`;

const SortBox = styled.div`
    // sort 묶음
    display: flex;
    align-items: center;
    border: 1px solid gray;
`;
const SortButton = styled.div`
    // sort 버튼
    padding: 8px 15px;
    border: none;
    border-left: 1px solid gray;
    font-size: 14px;
    border-radius: 0px;
`

const Title = styled.h1` // 상단 제목 스타일
  font-size: 30px;
  margin-bottom: 24px;
`

const FilterBox = styled.div`
    display: flex;
    flex-direction: row;
    justify-content: right;
`

const FilterItem = styled.span`
    padding: 15px 5px;
    color: darkgray;
    :hover {
        border-bottom: 1px solid orange;
        color: black;
    }
`

function Users() {
    const [userDatas, setUserDatas] = useState(null);
    const [isPending, setIsPending] = useState(true);
    
    const [page, setPage] = useState(1); // 페이지 정보 가져오기
    const [size, setSize] = useState(36); //  한 페이지에 보여줄 아이템 수
    const [total, setTotal] = useState(); // 전체 아이템 수

    useEffect(() => {
        axios.get(`${process.env.REACT_APP_API_URL}/members?page=${page}&size=${size}&by=base`)
        .then(res => {
            // console.log(res.data.data)
            // console.log(res.data.pageInfo)
            setUserDatas(res.data.data)
            setTotal(res.data.pageInfo.totalElements)
        })
        .then(()=> setIsPending(false));
    },[page])
    return (
        <UsersPage>
            <ContentsContainer>
                <UsersHeader>
                    <Title>Users</Title>
                    <SubBox>
                        <Search>
                            <MdSearch />
                            <input placeholder="Filter by tag name" />
                        </Search>
                        <SortBox>
                            <SortButton>Reputation</SortButton>
                            <SortButton>New Users</SortButton>
                            <SortButton>Voters</SortButton>
                            <SortButton>Editors</SortButton>
                            <SortButton>Moderators</SortButton>
                        </SortBox>
                    </SubBox>
                </UsersHeader>
                <FilterBox>
                    <FilterItem>week</FilterItem>
                    <FilterItem>month</FilterItem>
                    <FilterItem>quarter</FilterItem>
                    <FilterItem>year</FilterItem>
                    <FilterItem>all</FilterItem>
                </FilterBox>
                <UsersContent>
                    <UserList>
                        {!isPending && userDatas.map(user => <UserCard user={user}/>)}
                    </UserList>
                    <Paging total={total} size={size} page={page} setPage={setPage}/>
                </UsersContent>
            </ContentsContainer>
        </UsersPage>
    );
}

export default Users;
