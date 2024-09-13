package org.example.gamecenter.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(OddOrEven.class)
public class OddOrEvenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGameShouldShowOnRightPage() throws Exception {
        mockMvc.perform(get("/oddOrEven"))
                .andExpect(status().isOk())
                .andExpect(view().name("oddOrEven"));
    }
}
