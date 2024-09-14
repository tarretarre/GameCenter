package org.example.gamecenter.controller;

import org.example.gamecenter.dto.AdditionGameDto;
import org.example.gamecenter.service.AdditionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class AdditionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdditionService additionService;

    private MockHttpSession session;

    @BeforeEach
    void setUp() {
        session = new MockHttpSession();
    }

    @Test
    void additionTestShouldContainModelAttributes() throws Exception {
        assert session.getAttribute("gameDto") == null;

        mockMvc.perform(get("/addition").session(session))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("gameDto"))
                .andExpect(model().attribute("gameOver", false))
                .andExpect(model().attribute("answerCheck", false))
                .andExpect(model().attribute("lastRound", false))
                .andExpect(view().name("addition"));

        assert session.getAttribute("gameDto") != null;
    }

    @Test
    void additionPostStartNewGame() throws Exception {
        when(additionService.isFirstRound(any(AdditionGameDto.class))).thenReturn(true);
        assert session.getAttribute("gameDto") == null;

        mockMvc.perform(post("/addition")
                        .session(session)
                        .param("totalRounds", any(Integer.class).toString()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/addition"));

        assert session.getAttribute("gameDto") != null;
        verify(additionService).isFirstRound(any(AdditionGameDto.class));
        verify(additionService).startGame(any(AdditionGameDto.class), any(Integer.class));
        verify(additionService, never()).nextRound(any(AdditionGameDto.class));
    }

    @Test
    void additionPostProceedToNextRound() throws Exception {
        when(additionService.isFirstRound(any(AdditionGameDto.class))).thenReturn(false);
        assert session.getAttribute("gameDto") == null;

        mockMvc.perform(post("/addition")
                        .session(session)
                        .param("totalRounds", any(Integer.class).toString()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/addition"));

        assert session.getAttribute("gameDto") != null;
        verify(additionService).isFirstRound(any(AdditionGameDto.class));
        verify(additionService).nextRound(any(AdditionGameDto.class));
        verify(additionService, never()).startGame(any(AdditionGameDto.class), any(Integer.class));
    }

    @Test
    void answerCheckIfIsLastRound() throws Exception {
        when(additionService.isLastRound(any(AdditionGameDto.class))).thenReturn(true);
        assert session.getAttribute("gameDto") == null;

        mockMvc.perform(post("/addition/answer-check")
                        .session(session)
                        .param("userAnswer", any(Integer.class).toString()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeCount(2))
                .andExpect(flash().attribute("lastRound", true))
                .andExpect(flash().attribute("answerCheck", true))
                .andExpect(redirectedUrl("/addition"));

        assert session.getAttribute("gameDto") != null;
        verify(additionService).isLastRound(any(AdditionGameDto.class));
        verify(additionService).checkAnswer(any(AdditionGameDto.class));
    }

    @Test
    void answerCheckIfIsNotLastRound() throws Exception {
        when(additionService.isLastRound(any(AdditionGameDto.class))).thenReturn(false);
        assert session.getAttribute("gameDto") == null;

        mockMvc.perform(post("/addition/answer-check")
                        .session(session)
                        .param("userAnswer", any(Integer.class).toString()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeCount(1))
                .andExpect(flash().attribute("answerCheck", true))
                .andExpect(redirectedUrl("/addition"));

        assert session.getAttribute("gameDto") != null;
        verify(additionService).isLastRound(any(AdditionGameDto.class));
        verify(additionService).checkAnswer(any(AdditionGameDto.class));
    }

    @Test
    void gameOverExpectFlashAttributeGameOver() throws Exception {
        mockMvc.perform(post("/addition/game-over"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeCount(1))
                .andExpect(flash().attribute("gameOver", true))
                .andExpect(redirectedUrl("/addition"));
    }

    @Test
    void resetShouldRemoveSessionAttributeAndRedirect() throws Exception {
        session.setAttribute("gameDto", new AdditionGameDto());
        assert session.getAttribute("gameDto") != null;

        mockMvc.perform(post("/addition/reset")
                        .session(session))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/addition"));

        assert session.getAttribute("gameDto") == null;
    }
}