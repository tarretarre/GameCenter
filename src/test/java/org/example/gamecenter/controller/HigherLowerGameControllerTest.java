package org.example.gamecenter.controller;

import org.example.gamecenter.service.HigherLowerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class HigherLowerGameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    HigherLowerService higherLowerService;

    private final MockHttpSession session = new MockHttpSession();


    @Test
    void testHigherLowerGameGetMapping() throws Exception {
        this.mockMvc.perform(get("/higherlowergame"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("startGame", false))
                .andExpect(view().name("HigherLowerGame"));
    }

    @Test
    void testHigherLowerGameStartGame() throws Exception {
        this.mockMvc.perform(get("/bartTheGame"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/higherlowergame"))
                .andExpect(flash().attribute("startGame", true));
    }


    @Test
    public void testMakeGuessWithResultOne() throws Exception {
        when(higherLowerService.randomNumberGenerator()).thenReturn(11);
        int testRandomNumber = higherLowerService.randomNumberGenerator();
        assert testRandomNumber == 11;

        session.setAttribute("guessInt", testRandomNumber);
        mockMvc.perform(post("/makeGuess")
                        .session(session)
                        .param("guess", "5"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/higherlowergame"))
                .andExpect(flash().attribute("startGame", true))
                .andExpect(flash().attribute("guess", "5"))
                .andExpect(flash().attribute("result", 1));
    }
}