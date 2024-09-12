package org.example.gamecenter.controller;

import org.example.gamecenter.service.HigherLowerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.lenient;
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
        this.mockMvc.perform(get("/startTheGame"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/higherlowergame"))
                .andExpect(flash().attribute("startGame", true));
    }


    @Test
    public void testMakeGuessWithResultOne() throws Exception {
        lenient().when(higherLowerService.controllGuess("42", 24)).thenReturn(2);
        mockMvc.perform(post("/makeGuess")
                        .param("guess", "42"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/higherlowergame"))
                .andExpect(flash().attribute("startGame", true))
                .andExpect(flash().attribute("guess", "42"))
                .andExpect(flash().attribute("result", 2));
    }
}