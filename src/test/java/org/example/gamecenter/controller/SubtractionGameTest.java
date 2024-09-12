package org.example.gamecenter.controller;

import org.example.gamecenter.service.SubtractionGameService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class SubtractionGameTest {

    private final String question = "10";
    //private final SubtractionGameService service = new SubtractionGameService();

    @Test
    public void testCheckIfCorrectAnswer() throws Exception {
        String userAnswer ="10";
        assertEquals(question,userAnswer);

    }

    @Test
    public void testCheckIfWrongAnswer() throws Exception {
        String userAnswer ="20";
        assertNotEquals(question,userAnswer);
    }


    @Test
    public void testSubtractionGameCenter() {}




}
