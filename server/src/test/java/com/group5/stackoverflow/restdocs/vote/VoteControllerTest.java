package com.group5.stackoverflow.restdocs.vote;

import com.google.gson.Gson;
import com.group5.stackoverflow.vote.controller.VoteController;
import com.group5.stackoverflow.vote.service.VoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(VoteController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class VoteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VoteService voteService;

    @Autowired
    private Gson gson;

    @Test
    public void postQuestionUpCountTest() throws Exception {

    }

    @Test
    public void postQuestionDownCountTest() throws Exception {

    }

    @Test
    public void postAnswerUpCountTest() throws Exception {

    }

    @Test
    public void postAnswerDownCountTest() throws Exception {

    }
}
