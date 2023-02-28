package com.group5.stackoverflow.restdocs.answer;

import com.google.gson.Gson;
import com.group5.stackoverflow.answer.controller.AnswerController;
import com.group5.stackoverflow.answer.dto.AnswerDto;
import com.group5.stackoverflow.answer.entity.Answer;
import com.group5.stackoverflow.answer.mapper.AnswerMapper;
import com.group5.stackoverflow.answer.service.AnswerService;
import com.group5.stackoverflow.auth.tokenizer.JwtTokenizer;
import com.group5.stackoverflow.auth.utils.CustomAuthorityUtils;
import com.group5.stackoverflow.config.SecurityConfiguration;
import com.group5.stackoverflow.helper.MockSecurity;
import com.group5.stackoverflow.member.entity.Member;
import com.group5.stackoverflow.member.mapper.MemberMapper;
import com.group5.stackoverflow.member.repository.MemberRepository;
import com.group5.stackoverflow.member.service.MemberService;
import com.group5.stackoverflow.question.entity.Question;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.List;

import static com.group5.stackoverflow.utils.ApiDocumentUtils.getRequestPreProcessor;
import static com.group5.stackoverflow.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import({SecurityConfiguration.class, JwtTokenizer.class, CustomAuthorityUtils.class})
@WebMvcTest(AnswerController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AnswerControllerRestDocsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @Autowired
    private Gson gson;

    @MockBean
    private AnswerMapper mapper;

    @MockBean
    private AnswerService answerService;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberMapper memberMapper;

    private String accessTokenForUser;
    private String accessTokenForAdmin;

    @BeforeAll
    public void init() {
        accessTokenForUser = MockSecurity.getValidAccessToken(jwtTokenizer.getSecretKey(), "USER");
        accessTokenForAdmin = MockSecurity.getValidAccessToken(jwtTokenizer.getSecretKey(), "ADMIN");
    }


    @Test
    @DisplayName("답변 등록")
    public void postAnswerTest() throws Exception {

        AnswerDto.Post post = new AnswerDto.Post(1L, 1L, "이곳은 답변을 적는 곳입니다.");
        String content = gson.toJson(post);

        AnswerDto.Response responseDto = new AnswerDto.Response(
                1L,
                1L,
                1L,
                "홍길동",
                "이곳은 답변을 적는 곳입니다.",
                0
        );

        Member member = new Member();
        member.setMemberId(post.getMemberId());

        given(memberService.findMemberByEmail(Mockito.anyString())).willReturn(member);

        given(mapper.answerPostDtoToAnswer(Mockito.any(AnswerDto.Post.class))).willReturn(new Answer());

        Answer mockResultAnswer = new Answer();
        mockResultAnswer.setAnswerId(1L);
        given(answerService.createAnswer(Mockito.any(Answer.class), Mockito.anyLong())).willReturn(mockResultAnswer);

        given(mapper.answerToAnswerResponse(Mockito.any(Answer.class))).willReturn(responseDto);

        Long questionId = 1L;
        Long memberId = 1L;

        ResultActions actions = mockMvc.perform(
                post("/{member-id}/questions/{question-id}/answers", memberId, questionId)
                        .header("Authorization", "Bearer ".concat(accessTokenForUser))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        actions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.memberId").value(post.getMemberId()))
                .andExpect(jsonPath("$.data.questionId").value(post.getQuestionId()))
                .andExpect(jsonPath("$.data.content").value(post.getContent()))
                .andDo(document(
                                "post-answer",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("member-id").description("회원 식별자"),
                                        parameterWithName("question-id").description("질문 식별자")

                                ),
                                requestHeaders(
                                        headerWithName("Authorization").description("Bearer (accessToken)")
                                ),
                                requestFields(
                                        List.of(
                                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자")
                                                        .attributes(key("validation").value("Not Null")),
                                                fieldWithPath("questionId").type(JsonFieldType.NUMBER).description("질문 식별자")
                                                        .attributes(key("validation").value("Not Null")).ignored(),
                                                fieldWithPath("content").type(JsonFieldType.STRING).description("답변 내용")
                                                        .attributes(key("validation").value("Not Null"))
                                        )
                                ),
                                responseFields(
                                        List.of(
                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                                fieldWithPath("data.answerId").type(JsonFieldType.NUMBER).description("답변 식별자"),
                                                fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                                fieldWithPath("data.questionId").type(JsonFieldType.NUMBER).description("질문 식별자"),
                                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("답변자 이름"),
                                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("답변 내용"),
                                                fieldWithPath("data.voteCount").type(JsonFieldType.NUMBER).description("추천수")


                                        )
                                )
                        )
                );
    }


    @Test
    @DisplayName("답변 수정")
    public void patchAnswerTest() throws Exception {

        AnswerDto.Patch patch = new AnswerDto.Patch(
                1L, 1L, 1L, "이곳은 답변을 적는 곳입니다.");
        String content = gson.toJson(patch);

        AnswerDto.Response response = new AnswerDto.Response(
                1L,
                1L,
                1L,
                "홍길동",
                "이곳은 답변을 적는 곳입니다.",
                0
        );

        Member member = new Member();
        member.setMemberId(patch.getMemberId());

        given(memberService.findMemberByEmail(Mockito.anyString())).willReturn(member);

        given(mapper.answerPatchDtoToAnswer(Mockito.any(AnswerDto.Patch.class))).willReturn(new Answer());

        Answer mockResultAnswer = new Answer();
        mockResultAnswer.setAnswerId(1L);
        given(answerService.updateAnswer(Mockito.any(Answer.class), Mockito.anyLong())).willReturn(mockResultAnswer);

        given(mapper.answerToAnswerResponse(Mockito.any(Answer.class))).willReturn(response);

        Long answerId = 1L;
        Long memberId = 1L;

        ResultActions actions = mockMvc.perform(
                patch("/{member-id}/answers/{answer-id}", memberId, answerId)
                        .header("Authorization", "Bearer ".concat(accessTokenForUser))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.memberId").value(patch.getMemberId()))
                .andExpect(jsonPath("$.data.answerId").value(patch.getMemberId()))
                .andExpect(jsonPath("$.data.questionId").value(patch.getQuestionId()))
                .andExpect(jsonPath("$.data.content").value(patch.getContent()))
                .andDo(document(
                        "patch-answer",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("member-id").description("회원 식별자"),
                                parameterWithName("answer-id").description("답변 식별자")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer (accessToken)")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자")
                                                .attributes(key("validation").value("Not Null")),
                                        fieldWithPath("answerId").type(JsonFieldType.NUMBER).description("답변 식별자")
                                                .attributes(key("validation").value("Not Null")),
                                        fieldWithPath("questionId").type(JsonFieldType.NUMBER).description("질문 식별자")
                                                .attributes(key("validation").value("Not Null")).ignored(),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("답변 내용")
                                                .attributes(key("validation").value("Not Null"))
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.answerId").type(JsonFieldType.NUMBER).description("답변 식별자"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.questionId").type(JsonFieldType.NUMBER).description("질문 식별자"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("답변자 이름"),
                                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("답변 내용"),
                                        fieldWithPath("data.voteCount").type(JsonFieldType.NUMBER).description("추천수")
                                )
                        )
                ));

    }

    @Test
    @DisplayName("답변 삭제")
    public void deleteAnswerTest() throws Exception {

        Long answerId = 1L;

        doNothing().when(answerService).deleteAnswer(Mockito.anyLong(), Mockito.anyString());

        ResultActions actions = mockMvc.perform(
                delete("/answers/{answer-id}", answerId)
                        .header("Authorization", "Bearer ".concat(accessTokenForUser))
        );

        actions
                .andExpect(status().isNoContent())
                .andDo(document("delete-answer",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("answer-id").description("답변 식별자")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer (accessToken)")
                        )
                ));
    }

    @Test
    @DisplayName("답변 추천 기능")
    public void answerPatchVote() throws Exception {
        Long answerId = 1L;

        AnswerDto.PatchVote patchVote = new AnswerDto.PatchVote(1L, 1L);
        String content = gson.toJson(patchVote);

        AnswerDto.Response response = new AnswerDto.Response(
                1L,
                1L,
                1L,
                "홍길동",
                "답변 내용입니다.",
                1
        );

        given(answerService.updateVote(Mockito.anyLong(), Mockito.anyString())).willReturn(new Answer());

        given(mapper.answerToAnswerResponse(Mockito.any(Answer.class))).willReturn(response);

        ResultActions actions =
                mockMvc.perform(
                        patch("/answers/{answer-id}/vote", answerId)
                                .header("Authorization", "Bearer ".concat(accessTokenForUser))
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                                .param("updown", "up")
                );

        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.answerId").value(patchVote.getAnswerId()))
                .andDo(document("patch-answer-vote",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("answer-id").description("답변 식별자")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer (accessToken")
                        ),
                        requestParameters(
                                parameterWithName("updown").description("추천 업 or 추천 다운")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.answerId").type(JsonFieldType.NUMBER).description("답변 식별자"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.questionId").type(JsonFieldType.NUMBER).description("질문 식별자"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("답변 내용"),
                                        fieldWithPath("data.voteCount").type(JsonFieldType.NUMBER).description("추천수")
                                )
                        )
                ));
    }

    @Test
    @DisplayName("내가 쓴 답변 조회")
    public void getMyAnswersTest() throws Exception {

        int page = 1;
        int size = 10;

        Member member1 = new Member();
        member1.setMemberId(1L);
        member1.setName("홍길동");
        member1.setAge(30);
        member1.setEmail("test1@test.com");
        member1.setVoteCount(0);
        member1.setPassword("mysecretpassword");
        member1.setMemberStatus(Member.MemberStatus.MEMBER_NEW);

        Question question1 = new Question();
        question1.setMember(member1);
        question1.setQuestionId(1L);
        question1.setTitle("타이틀입니다.");
        question1.setContent("질문 내용입니다.");
        question1.setVoteCount(20);
        question1.setViews(30);

        Answer answer1 = new Answer();
        answer1.setAnswerId(1L);
        answer1.setContent("답변 내용입니다.");
        answer1.setVoteCount(20);

        Member member2 = new Member();
        member2.setMemberId(2L);
        member2.setName("홍길동2");
        member2.setAge(25);
        member2.setEmail("test2@test.com");
        member2.setVoteCount(0);
        member2.setPassword("mypassword123");
        member2.setMemberStatus(Member.MemberStatus.MEMBER_NEW);

        Question question2 = new Question();
        question2.setMember(member2);
        question2.setQuestionId(2L);
        question2.setTitle("2번째 타이틀입니다.");
        question2.setContent("2번째 질문 내용입니다.");
        question2.setVoteCount(10);
        question2.setViews(50);

        Answer answer2 = new Answer();
        answer2.setAnswerId(2L);
        answer2.setContent("2번째 답변 내용입니다.");
        answer2.setVoteCount(40);

        Page<Answer> pageAnswers = new PageImpl<>(List.of(answer1, answer2),
                PageRequest.of(page, size, Sort.by("answerId").descending()), 2);
        List<AnswerDto.Response> responses = List.of(
                new AnswerDto.Response(1L, 1L, 1L, "홍길동", "답변 내용입니다.", 20),
                new AnswerDto.Response(2L, 2L, 2L, "홍길동2", "2번째 답변 내용입니다.", 40)
        );

        given(answerService.findMyAnswers(Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt())).willReturn(pageAnswers);
        given(mapper.answersToAnswerResponses(Mockito.anyList())).willReturn(responses);

        ResultActions actions =
                mockMvc.perform(
                        get("/answers/my")
                                .header("Authorization", "Bearer ".concat(accessTokenForUser))
                                .param("page", "1")
                                .param("size", "10")
                                .accept(MediaType.APPLICATION_JSON)
                );

        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andDo(document("get-my-answers",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer (accessToken)")
                        ),
                        requestParameters(
                                parameterWithName("page").description("조회 페이지"),
                                parameterWithName("size").description("페이지당 조회 수")
                        ),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                fieldWithPath("data[].answerId").type(JsonFieldType.NUMBER).description("답변 식별자"),
                                fieldWithPath("data[].memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                fieldWithPath("data[].questionId").type(JsonFieldType.NUMBER).description("질문 식별자"),
                                fieldWithPath("data[].name").type(JsonFieldType.STRING).description("회원 이름"),
                                fieldWithPath("data[].content").type(JsonFieldType.STRING).description("답변 내용"),
                                fieldWithPath("data[].voteCount").type(JsonFieldType.NUMBER).description("추천수"),
                                fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이지 정보"),
                                fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("조회 페이지 정보"),
                                fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("사이즈 정보"),
                                fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 조회 건 수"),
                                fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수")
                        )
                ));
    }
}
