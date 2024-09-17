package org.example.gamecenter;

import jakarta.servlet.http.HttpSession;
import org.example.gamecenter.service.SubtractionGameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest
public class SubtractionGameServiceTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    private SubtractionGameService service;



    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }



    @Test
    public void testCheckIfCorrectAnswer()  {
        HashMap<String,Object> testMap = new HashMap<>();
        testMap.put("question","20-10");
        testMap.put("answer", List.of(10,5,3));
        testMap.put("roundCounter",1);
        testMap.put("endGame",5);

        when(service.gameLogic(any(Integer.class))).thenReturn(testMap);

        assertEquals("20-10", testMap.get("question"));
        assertTrue(((List<?>)testMap.get("answer")).contains(10));
    }

    @Test
    public void testCheckIfGameEndWhenRoundCounterIsHigherThanFive() {


        Map<String,Object> testResult = new HashMap<>();
        testResult.put("roundCounter",6);

        when(service.gameLogic(any(Integer.class))).thenReturn(testResult);


        assertNull(testResult.get("question"));
        assertNull(testResult.get("answer"));
        assertEquals(6, testResult.get("roundCounter"));
    }

    @Test
    public void testIfAnswerListIsLessThenThreeCheckIfItAddsAnswer(){
        Random random = new Random();
        List<Integer> testAnswer = new ArrayList<>();
        int correctNumber = 10;

        testAnswer.add(correctNumber);

        while(testAnswer.size() <3){
            int randomNumber = random.nextInt(100)+1;
            testAnswer.add(randomNumber);

            if(randomNumber != correctNumber && !testAnswer.contains(randomNumber)){
                testAnswer.add(randomNumber);
            }
        }
        assertEquals(3,testAnswer.size());
        assertTrue(testAnswer.contains(correctNumber));
    }

}
