package com.group5.stackoverflow.restdocs.question;

import com.google.gson.Gson;
import com.group5.stackoverflow.auth.tokenizer.JwtTokenizer;
import com.group5.stackoverflow.auth.utils.CustomAuthorityUtils;
import com.group5.stackoverflow.config.SecurityConfiguration;
import com.group5.stackoverflow.helper.MockSecurity;
import com.group5.stackoverflow.member.entity.Member;
import com.group5.stackoverflow.member.repository.MemberRepository;
import com.group5.stackoverflow.question.controller.QuestionController;
import com.group5.stackoverflow.question.dto.QuestionDto;
import com.group5.stackoverflow.question.entity.Question;
import com.group5.stackoverflow.question.mapper.QuestionMapper;
import com.group5.stackoverflow.question.service.QuestionService;
import org.junit.jupiter.api.BeforeAll;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import({SecurityConfiguration.class, JwtTokenizer.class, CustomAuthorityUtils.class})
@WebMvcTest(QuestionController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QuestionControllerRestDocsTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @MockBean
    private QuestionService questionService;

    @MockBean
    private QuestionMapper questionMapper;

    @Autowired
    private Gson gson;

    @MockBean
    private MemberRepository memberRepository;

    private String accessTokenForUser;
    private String accessTokenForAdmin;

    @BeforeAll
    public void init() {
        accessTokenForUser = MockSecurity.getValidAccessToken(jwtTokenizer.getSecretKey(), "USER");
        accessTokenForAdmin = MockSecurity.getValidAccessToken(jwtTokenizer.getSecretKey(), "ADMIN");
    }

//    @Test
//    public void postQuestionTest() throws Exception {
//        Long memberId = 1L;
//
//        QuestionDto.Post post = new QuestionDto.Post("타이틀입니다.",
//                "이곳은 질문을 적는 곳입니다.", memberId);
//        String content = gson.toJson(post);
//        String accessToken = accessTokenForUser;
//
//        given(questionMapper.questionPostToQuestion(Mockito.any(QuestionDto.Post.class))).willReturn(new Question());
//
//        Question mockResultQuestion = new Question();
//        mockResultQuestion.setQuestionId(1L);
//
//        given(questionService.createQuestion(Mockito.any(Question.class))).willReturn(mockResultQuestion);
//
//        ResultActions actions =
//                mockMvc.perform(
//                        post("/questions")
//                                .header("Authorization", "Bearer ".concat(accessToken))
//                                .accept(MediaType.APPLICATION_JSON)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(content)
//                );
//
//        actions
//                .andExpect(status().isCreated())
//                .andExpect(header().string("Location", is(startsWith("/questions"))))
//                .andDo(document("post-question",
//                        getRequestPreProcessor(),
//                        getResponsePreProcessor(),
//                        requestHeaders(
//                                headerWithName("Authorization").description("Bearer (accessToken)")
//                        ),
//                        requestFields(
//                                List.of(
//                                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목")
//                                                .attributes(key("validation").value("Not Null")),
//                                        fieldWithPath("content").type(JsonFieldType.STRING).description("질문 내용")
//                                                .attributes(key("validation").value("Not Null")),
//                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자")
//                                                .attributes(key("validation").value("Not Null"))
//                                )
//                        ),
//                        responseHeaders(
//                                headerWithName(HttpHeaders.LOCATION).description("Location header. 등록된 리소스의 URI")
//                        )
//                ));
//    }

    @Test
    public void patchQuestionTest() throws Exception {
        Long questionId = 1L;
        QuestionDto.Patch patch = new QuestionDto.Patch(questionId, "타이틀입니다.",
                "이부분은 질문 내용입니다.");
        String content = gson.toJson(patch);
        String accessToken = accessTokenForUser;

        QuestionDto.Response response =
                new QuestionDto.Response(1L,
                        "타이틀입니다.",
                        "이부분은 질문 내용입니다.",
                        1L,
                        "taekie",
                        20,
                        10,
                        "JAVA");

        given(questionMapper.questionPatchToQuestion(Mockito.any(QuestionDto.Patch.class))).willReturn(new Question());

        given(questionService.updateQuestion(Mockito.any(Question.class))).willReturn(new Question());

        given(questionMapper.questionToQuestionResponse(Mockito.any(Question.class))).willReturn(response);

        ResultActions actions =
                mockMvc.perform(
                        patch("/questions/{question-id}", questionId)
                                .header("Authorization", "Bearer ".concat(accessToken))
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.questionId").value(patch.getQuestionId()))
                .andExpect(jsonPath("$.data.title").value(patch.getTitle()))
                .andExpect(jsonPath("$.data.content").value(patch.getContent()))
                .andDo(document("patch-question",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("question-id").description("질문 식별자")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer (accessToken")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("questionId").type(JsonFieldType.NUMBER).description("질문 식별자")
                                                .attributes(key("validation").value("Not Null")),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("질문 제목")
                                                .attributes(key("validation").value("Not Null")),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("질문 내용")
                                                .attributes(key("validation").value("Not Null"))
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.questionId").type(JsonFieldType.NUMBER).description("질문 식별자"),
                                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("질문 제목"),
                                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("질문 내용"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("data.voteCount").type(JsonFieldType.NUMBER).description("추천수"),
                                        fieldWithPath("data.views").type(JsonFieldType.NUMBER).description("조회수"),
                                        fieldWithPath("data.tagName").type(JsonFieldType.STRING).description("태그 이름")
                                )
                        )
                ));
    }

    @Test
    public void getQuestionTest() throws Exception {
        String accessToken = accessTokenForAdmin;

        QuestionDto.Response response =
                new QuestionDto.Response(1L,
                        "타이틀 입니다.",
                        "질문 내용입니다.",
                        1L,
                        "taekie",
                        30,
                        40,
                        "JAVA");

        given(questionService.findQuestion(Mockito.anyLong())).willReturn(new Question());
        given(questionMapper.questionToQuestionResponse(Mockito.any(Question.class))).willReturn(response);

        ResultActions actions =
                mockMvc.perform(
                        get("/questions/{question-id}", 1L)
                                .header("Authorization", "Bearer ".concat(accessToken))
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                );

        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.questionId").value(response.getQuestionId()))
                .andExpect(jsonPath("$.data.title").value(response.getTitle()))
                .andExpect(jsonPath("$.data.content").value(response.getContent()))
                .andExpect(jsonPath("$.data.memberId").value(response.getMemberId()))
                .andExpect(jsonPath("$.data.name").value(response.getName()))
                .andExpect(jsonPath("$.data.voteCount").value(response.getVoteCount()))
                .andExpect(jsonPath("$.data.views").value(response.getViews()))
                .andExpect(jsonPath("$.data.tagName").value(response.getTagName()))
                .andDo(document("get-question",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("question-id").description("질문 식별자")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer (accessToken)")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.questionId").type(JsonFieldType.NUMBER).description("질문 식별자"),
                                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("질문 제목"),
                                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("질문 내용"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("data.voteCount").type(JsonFieldType.NUMBER).description("추천수"),
                                        fieldWithPath("data.views").type(JsonFieldType.NUMBER).description("조회수"),
                                        fieldWithPath("data.tagName").type(JsonFieldType.STRING).description("태그 이름")
                                )
                        )
                ));
    }

    @Test
    public void getQuestionsTest() throws Exception {

        int page = 1;
        int size = 10;

        Member member1 = new Member();
        member1.setMemberId(1L);
        member1.setName("taekie");
        member1.setAge(30);
        member1.setEmail("taekie@example.com");
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

        Member member2 = new Member();
        member2.setMemberId(2L);
        member2.setName("gildong");
        member2.setAge(25);
        member2.setEmail("gildong@example.com");
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

        Page<Question> pageQuestions = new PageImpl<>(List.of(question1, question2),
                PageRequest.of(page, size, Sort.by("question-id").descending()), 2);
        List<QuestionDto.Response> responses = List.of(
                new QuestionDto.Response(1L, "타이틀입니다.","질문 내용입니다.",
                        1L, "taekie", 52, 98, "JAVA"),
                new QuestionDto.Response(2L, "2번째 타이틀입니다.","2번째 질문 내용입니다.",
                        2L, "gildong", 23, 54, "SPRING")
        );

        given(questionService.findQuestions(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString())).willReturn(pageQuestions);
        given(questionMapper.questionsToQuestionResponses(Mockito.anyList())).willReturn(responses);

        ResultActions actions =
                mockMvc.perform(
                        get("/questions")
                                .param("page","1")
                                .param("size", "10")
                                .accept(MediaType.APPLICATION_JSON)
                );

        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andDo(document("get-questions",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestParameters(
                                parameterWithName("page").description("조회 페이지"),
                                parameterWithName("size").description("페이지당 조회 수")
                        ),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                fieldWithPath("data[].questionId").type(JsonFieldType.NUMBER).description("질문 식별자"),
                                fieldWithPath("data[].title").type(JsonFieldType.STRING).description("질문 제목"),
                                fieldWithPath("data[].content").type(JsonFieldType.STRING).description("질문 내용"),
                                fieldWithPath("data[].memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                fieldWithPath("data[].name").type(JsonFieldType.STRING).description("회원 이름"),
                                fieldWithPath("data[].voteCount").type(JsonFieldType.NUMBER).description("추천수"),
                                fieldWithPath("data[].views").type(JsonFieldType.NUMBER).description("조회수"),
                                fieldWithPath("data[].tagName").type(JsonFieldType.STRING).description("태그 이름"),
                                fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이지 정보"),
                                fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("조회 페이지 정보"),
                                fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("사이즈 정보"),
                                fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 조회 건 수"),
                                fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수")
                        )
                ));
    }

    @Test
    public void deleteQuestion() throws Exception {
        Long questionId = 1L;
        String accessToken = accessTokenForUser;

        doNothing().when(questionService).deleteQuestion(questionId);

        ResultActions actions =
                mockMvc.perform(
                        delete("/questions/{question-id}", questionId)
                                .header("Authorization", "Bearer ".concat(accessToken)))
                        .andExpect(status().isNoContent())
                        .andDo(document("delete-question",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                pathParameters(
                                        parameterWithName("question-id").description("질문 식별자")
                                ),
                                requestHeaders(
                                        headerWithName("Authorization").description("Bearer (accessToken)")
                                )
                        ));
    }
}
