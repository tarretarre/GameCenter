package org.example.gamecenter.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class HigherLowerGameControllerTest {

    @Autowired
    private MockMvc mockMvc;



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
}