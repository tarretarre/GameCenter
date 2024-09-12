package org.example.gamecenter;

import jakarta.servlet.http.HttpSession;
import org.example.gamecenter.controller.MinusController;
import org.example.gamecenter.service.SubtractionGameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class MinusControllerTest {

    @Mock
    private SubtractionGameService service;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @InjectMocks
    private MinusController minusController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMinusGetData() throws Exception {

    }

    @Autowired()
    private MockMvc mockMvc;

    @Test
    public void testMinusReturnMinus() throws Exception {
        mockMvc.perform(get("/minus"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("minus"));
    }

    @Test
    public void testCheckGuess() throws Exception {
        mockMvc.perform(post("/subtractionguess")
                .param("guess","10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("minus"))
                .andExpect(flash().attribute("guess","10"));

    }


}
