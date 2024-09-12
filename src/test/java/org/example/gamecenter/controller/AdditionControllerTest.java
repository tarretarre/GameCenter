package org.example.gamecenter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.gamecenter.dto.AdditionGameDto;
import org.example.gamecenter.service.AdditionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class AdditionControllerTest {

    @Autowired
    private  MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AdditionService additionService;

    @Test
    void additionTest_shouldContainModelAttributes() throws Exception {
        AdditionGameDto gameDto = new AdditionGameDto();
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("gameDto", gameDto);


        mockMvc.perform(get("/addition").session(session))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("gameDto"))
                .andExpect(model().attributeExists("gameOver"))
                .andExpect(model().attributeExists("answerCheck"))
                .andExpect(model().attributeExists("lastRound"))
                .andExpect(model().attribute("gameOver", false))
                .andExpect(model().attribute("answerCheck", false))
                .andExpect(model().attribute("lastRound", false))
                .andExpect(view().name("addition"));
    }

    @Test
    void additionPost_startNewGame() throws Exception {
        int totalRounds = 5;
        MockHttpSession session = new MockHttpSession();
        AdditionGameDto gameDto = new AdditionGameDto();
        gameDto.setTotalRounds(totalRounds);

        when(additionService.isFirstRound(gameDto)).thenReturn(true);

        mockMvc.perform(post("/addition").session(session).param("totalRounds", Integer.toString(totalRounds)))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/addition"))

    }

    @Test
    void answerCheck() {
    }

    @Test
    void gameOver() {
    }

    @Test
    void reset() {
    }
}