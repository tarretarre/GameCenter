package org.example.gamecenter.controller;

import org.example.gamecenter.DTO.OddOrEvenDTO;
import org.example.gamecenter.service.OddOrEvenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OddOrEvenController.class)
public class OddOrEvenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OddOrEvenService ooe;

    private OddOrEvenDTO ooeDTO;
    private MockHttpSession session;

    @BeforeEach
    public void setUp() {
        ooeDTO = new OddOrEvenDTO();
        session = new MockHttpSession();
        session.setAttribute("ooeDTO", ooeDTO);
    }

    @Test
    public void testGameShouldShowOnRightPage() throws Exception {
        mockMvc.perform(get("/oddOrEven"))
                .andExpect(status().isOk())
                .andExpect(view().name("oddOrEven"));
    }

    @Test
    public void testStartGame() throws Exception {
        doAnswer(invocation -> {
            OddOrEvenDTO dto = invocation.getArgument(0);
            dto.setRandomNumber(17);
            return null;
        }).when(ooe).startNewGame(any(OddOrEvenDTO.class));

        mockMvc.perform(get("/oddOrEven/Start").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("oddOrEvenGamePlay"))
                .andExpect(model().attribute("randomNumber", 17));
    }

    @Test
    public void testPlayRound() throws Exception {
        ooeDTO.setRandomNumber(17);
        session.setAttribute("ooeDTO", ooeDTO);

        when(ooe.playRound(any(OddOrEvenDTO.class), any(String.class))).thenReturn("Correct");
        when(ooe.isGameOver(any(OddOrEvenDTO.class))).thenReturn(false);

        mockMvc.perform(post("/oddOrEven/Gameplay")
                        .session(session)
                        .param("choice", "odd"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/oddOrEven/Gameplay"))
                .andExpect(flash().attributeExists("result"))
                .andExpect(flash().attribute("result", "Correct"));
    }

    @Test
    public void testGameOverShowsCorrectly() throws Exception {
        ooeDTO.setCorrectChoices(3);
        session.setAttribute("ooeDTO", ooeDTO);

        mockMvc.perform(get("/oddOrEven/GameOver").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("oddOrEvenGameOver"))
                .andExpect(model().attributeExists("correctChoices"))
                .andExpect(model().attribute("correctChoices", 3));
    }
}
