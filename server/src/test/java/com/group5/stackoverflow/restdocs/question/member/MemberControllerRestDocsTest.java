package com.group5.stackoverflow.restdocs.question.member;

import com.google.gson.Gson;
import com.group5.stackoverflow.member.controller.MemberController;
import com.group5.stackoverflow.member.dto.MemberDto;
import com.group5.stackoverflow.member.entity.Member;
import com.group5.stackoverflow.member.mapper.MemberMapper;
import com.group5.stackoverflow.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.group5.stackoverflow.utils.ApiDocumentUtils.getRequestPreProcessor;
import static com.group5.stackoverflow.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class MemberControllerRestDocsTest {
    @Autowired
    private MockMvc mockMvc;

    // (1)
    @MockBean
    private MemberService memberService;

    // (2)
    @MockBean
    private MemberMapper mapper;

    @Autowired
    private Gson gson;

    @Test
    public void postMemberTest() throws Exception {
        // (3) given
        MemberDto.Post post = new MemberDto.Post("홍길동", "gnswp21@gmail.com" , "1234", 22);
        String content = gson.toJson(post);

        // (4)
        given(mapper.memberPostToMember(Mockito.any(MemberDto.Post.class))).willReturn(new Member());

        // (5)
        Member mockResultMember = new Member();
        mockResultMember.setMemberId(1L);
        given(memberService.createMember(Mockito.any(Member.class))).willReturn(mockResultMember);

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
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("암호")
                                                .attributes(key("constraints").value("Optional")),
                                        fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이") //// (7-5)
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
        MemberDto.Patch patch = new MemberDto.Patch(memberId, "홍길동", "password", 23
                , 100,  Member.MemberStatus.MEMBER_ACTIVE);
        String content = gson.toJson(patch);

        MemberDto.Response responseDto =
                new MemberDto.Response(1L,
                        "홍길동",
                        "hgd@gmail.com",
                        "password",
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
                );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.memberId").value(patch.getMemberId()))
                .andExpect(jsonPath("$.data.name").value(patch.getName()))
                .andExpect(jsonPath("$.data.password").value(patch.getPassword()))
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
                                                .attributes(key("constraints").value("Optional")),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("암호")
                                                .attributes(key("constraints").value("Optional")),
                                        fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이")
                                                .attributes(key("constraints").value("Optional")),
                                        fieldWithPath("voteCount").type(JsonFieldType.NUMBER).description("추천수")
                                                .attributes(key("constraints").value("Optional")),
                                        fieldWithPath("memberStatus").type(JsonFieldType.STRING).description("회원 상태: MEMBER_NEW / MEMBER_ACTIVE / MEMBER_SLEEP / MEMBER_QUIT")
                                                .attributes(key("constraints").value("Optional"))
                                )
                        ),
                        responseFields(      // (4)
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),           // (5)
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("data.password").type(JsonFieldType.STRING).description("암호"),
                                        fieldWithPath("data.age").type(JsonFieldType.NUMBER).description("나이"),
                                        fieldWithPath("data.voteCount").type(JsonFieldType.NUMBER).description("추천수"),
                                        fieldWithPath("data.memberStatus").type(JsonFieldType.STRING).description("회원 상태: 신규 / 활동중 / 휴면 상태 / 탈퇴 상태")
                                )
                        )
                ));
    }
}