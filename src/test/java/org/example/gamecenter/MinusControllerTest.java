package org.example.gamecenter;

import org.example.gamecenter.controller.MinusController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MinusController.class)
public class MinusControllerTest {

    @Autowired()
    private MockMvc mockMvc;

    @Test
    public void testMinusReturnMinus() throws Exception {
        mockMvc.perform(get("/minus"))
                .andExpect(status().isOk())
                .andExpect(content().string("minus"));
    }
}
