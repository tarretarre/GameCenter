package org.example.gamecenter.controller;

import org.example.gamecenter.service.OddOrEvenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

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

    @Test
    public void testGameShouldShowOnRightPage() throws Exception {
        mockMvc.perform(get("/oddOrEven"))
                .andExpect(status().isOk())
                .andExpect(view().name("oddOrEven"));
    }

    @Test
    public void testStartGame() throws Exception {
        when(ooe.getRandomNumber()).thenReturn(17);

        mockMvc.perform(get("/oddOrEven/Start"))
                .andExpect(status().isOk())
                .andExpect(view().name("oddOrEvenGamePlay"))
                .andExpect(model().attribute("randomNumber", 17));
    }

    @Test
    public void testPlayRound() throws Exception {
        when(ooe.playRound("odd")).thenReturn("Correct");
        when(ooe.isGameOver()).thenReturn(false);
        when(ooe.getRandomNumber()).thenReturn(17);

        mockMvc.perform(post("/oddOrEven/Gameplay")
                .param("choice", "odd"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/oddOrEven/Gameplay"))
                .andExpect(flash().attributeExists("result"))
                .andExpect(flash().attribute("result", "Correct"));
    }

    @Test
    public void testGameOverShowsCorrectly() throws Exception {
        mockMvc.perform(get("/oddOrEven/GameOver"))
                .andExpect(status().isOk())
                .andExpect(view().name("oddOrEvenGameOver"))
                .andExpect(model().attributeExists("correctChoices"));
    }
}
