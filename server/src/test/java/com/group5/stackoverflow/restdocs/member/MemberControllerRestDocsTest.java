package com.group5.stackoverflow.restdocs.member;

import com.google.gson.Gson;
import com.group5.stackoverflow.auth.tokenizer.JwtTokenizer;
import com.group5.stackoverflow.auth.utils.CustomAuthorityUtils;
import com.group5.stackoverflow.config.SecurityConfiguration;
import com.group5.stackoverflow.helper.MemberControllerTestHelper;
import com.group5.stackoverflow.helper.MockSecurity;
import com.group5.stackoverflow.member.controller.MemberController;
import com.group5.stackoverflow.member.dto.MemberDto;
import com.group5.stackoverflow.member.entity.Member;
import com.group5.stackoverflow.member.mapper.MemberMapper;
import com.group5.stackoverflow.member.repository.MemberRepository;
import com.group5.stackoverflow.member.service.MemberService;
import com.group5.stackoverflow.question.dto.QuestionDto;
import com.group5.stackoverflow.question.entity.Question;
import com.group5.stackoverflow.question.mapper.QuestionMapper;
import com.group5.stackoverflow.question.service.QuestionService;
import com.group5.stackoverflow.tag.service.TagService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static com.group5.stackoverflow.utils.ApiDocumentUtils.getRequestPreProcessor;
import static com.group5.stackoverflow.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Import({SecurityConfiguration.class, JwtTokenizer.class, CustomAuthorityUtils.class})
@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MemberControllerRestDocsTest implements MemberControllerTestHelper {
    @Autowired
    private MockMvc mockMvc;

    // (1)
    @MockBean
    private MemberService memberService;

    // (2)
    @MockBean
    private MemberMapper mapper;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private QuestionMapper questionMapper;

    @MockBean
    private QuestionService questionService;

    @Autowired
    private Gson gson;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @MockBean
    private TagService tagService;

    private String accessTokenForUser;
    private String accessTokenForAdmin;

    @BeforeAll
    public void init() {
        accessTokenForUser = MockSecurity.getValidAccessToken(jwtTokenizer.getSecretKey(), "USER");
        accessTokenForAdmin = MockSecurity.getValidAccessToken(jwtTokenizer.getSecretKey(), "ADMIN");
    }

    @Test
    public void postMemberTest() throws Exception {
        // (3) given
        MemberDto.Post post = new MemberDto.Post("홍길동", "temp@gmail.com" , "1234", 22);
        String content = gson.toJson(post);

        // (4)
        given(mapper.memberPostToMember(Mockito.any(MemberDto.Post.class))).willReturn(new Member());

        // (5)
        Member mockResultMember = new Member();
        mockResultMember.setMemberId(1L);
        given(memberService.createMember(Mockito.any(Member.class))).willReturn(mockResultMember);
        // Todo MemberRepository 어디서 사용되는지
//        given(memberRepository.findByEmail(Mockito.anyString())).willReturn(Optional.of(mockResultMember));

        // (6) when
        ResultActions actions =
                mockMvc.perform(
                        post("/members")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        // then
        actions
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", is(startsWith("/members/"))))
                .andDo(document(       // (7)
                        "post-member",     // (7-1)
                        getRequestPreProcessor(),      // (7-2)
                        getResponsePreProcessor(),     // (7-3)
                        requestFields(             // (7-4)
                                List.of(
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름")
                                                .attributes(key("validation").value("Not Null")),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
                                                .attributes(key("validation").value("Email")),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("암호")
                                                .attributes(key("validation").value("Not Null")),
                                        fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이") //// (7-5)
                                                .attributes(key("validation").value("- ")).optional()
                                )
                        ),
                        responseHeaders(        // (7-6)
                                headerWithName(HttpHeaders.LOCATION).description("Location header. 등록된 리소스의 URI")
                        )
                ));
    }

    @Test
    public void patchMemberTest() throws Exception {
        // given
        long memberId = 1L;
                MemberDto.Patch patch = new MemberDto.Patch(memberId, "홍길동",  23
                , 100,  Member.MemberStatus.MEMBER_ACTIVE);
        String content = gson.toJson(patch);

        MemberDto.Response responseDto =
                new MemberDto.Response(1L,
                        "홍길동",
                        "hgd@gmail.com",
                        23,
                        100,
                        Member.MemberStatus.MEMBER_ACTIVE);

        // willReturn()이 최소한 null은 아니어야 한다.
        given(mapper.memberPatchToMember(Mockito.any(MemberDto.Patch.class))).willReturn(new Member());

        given(memberService.updateMember(Mockito.any(Member.class))).willReturn(new Member());

        given(mapper.memberToMemberResponse(Mockito.any(Member.class))).willReturn(responseDto);

        // when
        ResultActions actions =
                mockMvc.perform(
                        patch("/members/{member-id}", memberId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                                .header("Authorization", "Bearer ".concat(accessTokenForUser))

                );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.memberId").value(patch.getMemberId()))
                .andExpect(jsonPath("$.data.name").value(patch.getName()))
//                .andExpect(jsonPath("$.data.password").value(patch.getPassword()))
                .andExpect(jsonPath("$.data.age").value(patch.getAge()))
                .andExpect(jsonPath("$.data.voteCount").value(patch.getVoteCount()))
                .andExpect(jsonPath("$.data.memberStatus").value(patch.getMemberStatus().getStatus()))
                .andDo(document("patch-member",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(              // (1)
                                parameterWithName("member-id").description("회원 식별자")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자").ignored(),    // (2)
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름")
                                                // TODO Not Space
                                                // TODO Validation 수정
                                                .attributes(key("validation").value("Not Space")).optional(),
//                                        fieldWithPath("password").type(JsonFieldType.STRING).description("암호")
//                                                .attributes(key("validation").value("Not Space")).optional(),
                                        fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이")
                                                .attributes(key("validation").value("Not Space")).optional(),
                                        fieldWithPath("voteCount").type(JsonFieldType.NUMBER).description("추천수")
                                                .attributes(key("validation").value("Not Space")).optional(),
                                        fieldWithPath("memberStatus").type(JsonFieldType.STRING).description("회원 상태: MEMBER_NEW / MEMBER_ACTIVE / MEMBER_SLEEP / MEMBER_QUIT")
                                                .attributes(key("validation").value("Not Space")).optional()
                                )
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("USER JWT token")
                        ),
                        responseFields(      // (4)
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),           // (5)
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("data.age").type(JsonFieldType.NUMBER).description("나이"),
                                        fieldWithPath("data.voteCount").type(JsonFieldType.NUMBER).description("추천수"),
                                        fieldWithPath("data.memberStatus").type(JsonFieldType.STRING).description("회원 상태: 신규 / 활동중 / 휴면 상태 / 탈퇴 상태")
                                )
                        )
                ));
    }

    @Test
    public void getMemberTest() throws Exception {
        // given
        MemberDto.Response responseDto =
                new MemberDto.Response(1L,
                        "홍길동",
                        "hgd@gmail.com",
                        23,
                        100,
                        Member.MemberStatus.MEMBER_ACTIVE);

        // willReturn()이 최소한 null은 아니어야 한다.
        given(memberService.findMember(Mockito.anyLong())).willReturn(new Member());
        given(mapper.memberToMemberResponse(Mockito.any(Member.class))).willReturn(responseDto);
        given(mapper.memberToMemberResponseForPublic(Mockito.any(Member.class))).willReturn(responseDto);

        // when
        ResultActions actions =
                mockMvc.perform(
                        get("/members/{member-id}", 1L)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer ".concat(accessTokenForAdmin))
                );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.memberId").value(responseDto.getMemberId()))
                .andExpect(jsonPath("$.data.name").value(responseDto.getName()))
                .andExpect(jsonPath("$.data.age").value(responseDto.getAge()))
                .andExpect(jsonPath("$.data.voteCount").value(responseDto.getVoteCount()))
                .andExpect(jsonPath("$.data.memberStatus").value(responseDto.getMemberStatus()))
                .andDo(document("get-member",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(              // (1)
                                parameterWithName("member-id").description("회원 식별자")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("USER JWT token")
                        ),
                        responseFields(      // (4)
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자 (검증된 요청에만 제공)"),           // (5)
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("data.age").type(JsonFieldType.NUMBER).description("나이 (검증된 요청에만 제공)"),
                                        fieldWithPath("data.voteCount").type(JsonFieldType.NUMBER).description("추천수"),
                                        fieldWithPath("data.memberStatus").type(JsonFieldType.STRING).description("회원 상태: 신규 / 활동중 / 휴면 상태 / 탈퇴 상태")
                                )
                        )
                ));
    }

    @Test
    public void getMembersTest() throws Exception {
        // given
        Member member1 = new Member();
        member1.setMemberId(1L);
        member1.setName("John Doe");
        member1.setAge(30);
        member1.setEmail("john.doe@example.com");
        member1.setVoteCount(0);
        member1.setMemberStatus(Member.MemberStatus.MEMBER_NEW);

        MemberDto.Response response1 = new MemberDto.Response(
                member1.getMemberId(),
                member1.getName(),
                member1.getEmail(),
                member1.getAge(),
                member1.getVoteCount(),
                member1.getMemberStatus()
        );


        Member member2 = new Member();
        member2.setMemberId(2L);
        member2.setName("Alice Smith");
        member2.setAge(25);
        member2.setEmail("alice.smith@example.com");
        member2.setVoteCount(0);
        member2.setMemberStatus(Member.MemberStatus.MEMBER_NEW);

        MemberDto.Response response2 = new MemberDto.Response(
                member2.getMemberId(),
                member2.getName(),
                member2.getEmail(),
                member2.getAge(),
                member2.getVoteCount(),
                member2.getMemberStatus()
        );

        List<Member> members = List.of(member1, member2);
        int page = 1;
        int size = 5;
        PageRequest pageable = PageRequest.of(page-1, size);
        Page<Member> pageMembers = new PageImpl<>(members, pageable, members.size());

        List<MemberDto.Response> responses = List.of(response1, response2);


        // willReturn()이 최소한 null은 아니어야 한다.
        given(memberService.findMembers(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString())).willReturn(pageMembers);
        given(mapper.membersToMemberResponses(Mockito.anyList())).willReturn(responses);
        given(mapper.membersToMemberResponsesForPublic(Mockito.anyList())).willReturn(responses);

        // when
        ResultActions actions =
                mockMvc.perform(
                        get("/members")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer ".concat(accessTokenForAdmin))
                                .param("page", String.valueOf(page))
                                .param("size", String.valueOf(size))
                                .param("mode", "base")
                );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].memberId").value(member1.getMemberId()))
                .andExpect(jsonPath("$.data[0].name").value(member1.getName()))
                .andExpect(jsonPath("$.data[0].age").value(member1.getAge()))
                .andExpect(jsonPath("$.data[0].voteCount").value(member1.getVoteCount()))
                .andExpect(jsonPath("$.data[0].memberStatus").value(member1.getMemberStatus().getStatus()))
                .andExpect(jsonPath("$.data[1].memberId").value(member2.getMemberId()))
                .andExpect(jsonPath("$.data[1].name").value(member2.getName()))
                .andExpect(jsonPath("$.data[1].age").value(member2.getAge()))
                .andExpect(jsonPath("$.data[1].voteCount").value(member2.getVoteCount()))
                .andExpect(jsonPath("$.data[1].memberStatus").value(member2.getMemberStatus().getStatus()))
                .andExpect(jsonPath("$.pageInfo.page").value(page))
                .andExpect(jsonPath("$.pageInfo.size").value(size))
                .andExpect(jsonPath("$.pageInfo.totalElements").value(pageMembers.getTotalElements()))
                .andExpect(jsonPath("$.pageInfo.totalPages").value(pageMembers.getTotalPages()))
                .andDo(document("get-members",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestParameters(
                                List.of(
                                        parameterWithName("page").description("페이지 번호"),
                                        parameterWithName("size").description("한번에 볼 페이지"),
                                        parameterWithName("mode").description("다음 중 하나로 정렬 내림차순 [base | vote]")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                        fieldWithPath("data.[].memberId").type(JsonFieldType.NUMBER).description("회원 식별자 (검증된 요청에만 제공)"),           // (5)
                                        fieldWithPath("data.[].name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("data.[].email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("data.[].age").type(JsonFieldType.NUMBER).description("나이 (검증된 요청에만 제공)"),
                                        fieldWithPath("data.[].voteCount").type(JsonFieldType.NUMBER).description("추천수"),
                                        fieldWithPath("data.[].memberStatus").type(JsonFieldType.STRING).description("회원 상태: 신규 / 활동중 / 휴면 상태 / 탈퇴 상태"),
                                        fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이지 정보"),
                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("페이지 번호"),
                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("한 페이지의 사이즈"),
                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("총 멤버의 수"),
                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("총 페이지")
                                )
                        )
                ));
    }

    @Test
    public void deleteMemberTest() throws Exception {
        // given

        // willReturn()이 최소한 null은 아니어야 한다.
//        given(memberService.deleteMember(Mockito.anyLong()))
//        given(mapper.memberToMemberResponse(Mockito.any(Member.class))).willReturn(responseDto);

        // when
        ResultActions actions =
                mockMvc.perform(
                        delete("/members/{member-id}", 1L)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer ".concat(accessTokenForUser))
                );

        // then
        actions
                .andExpect(status().isNoContent());

        actions =
                mockMvc.perform(
                        delete("/members/{member-id}", 1L)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer ".concat(accessTokenForAdmin))
                );

        // then
        actions
                .andExpect(status().isNoContent())
                .andDo(document("delete-member",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                pathParameters(              // (1)
                                        parameterWithName("member-id").description("회원 식별자")
                                ),
                                requestHeaders(
                                        headerWithName("Authorization").description("[USER | ADMIN] JWT token")
                                )

                        )
                );
    }
}
