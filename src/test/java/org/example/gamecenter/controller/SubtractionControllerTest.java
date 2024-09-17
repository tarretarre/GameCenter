package org.example.gamecenter.controller;

import org.example.gamecenter.service.SubtractionGameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@AutoConfigureMockMvc
@SpringBootTest
public class SubtractionControllerTest {



    @Autowired
    MockMvc mockMvc;

    @MockBean
    private SubtractionGameService service;



    @Test
    public void testMinusAddsAttributesToModel() throws Exception {
        Map<String, Object> mockData = new HashMap<>();
        mockData.put("question", "10-5");
        mockData.put("answer", new int[]{5,3,2});
        mockData.put("roundCounter", 1);
        mockData.put("endGame",5);

        when(service.gameLogic()).thenReturn(mockData);
        mockMvc.perform(MockMvcRequestBuilders.get("/minus"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("minus"))
                .andExpect(model().attribute("question","10-5"))
                .andExpect(model().attribute("answer",new int[]{5,3,2}))
                .andExpect(model().attribute("roundCounter",1))
                .andExpect(model().attribute("endGame",5));
    }


    @Test
    public void testPostToCheckUserAnswerRoundCounterAndMinusView() throws Exception {
        Map<String, Object> testMap = new HashMap<>();
        testMap.put("endGame",5);

        when(service.gameLogic()).thenReturn(testMap);

        MockHttpSession session = new MockHttpSession();
        mockMvc.perform(MockMvcRequestBuilders.post("/checkAnswer")
                        .param("selectedAnswer","10")
                        .session(session))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("minus"))
                .andExpect(model().attribute("roundCounter",1))
                .andExpect(model().attributeExists("endGame"));

    }

}
