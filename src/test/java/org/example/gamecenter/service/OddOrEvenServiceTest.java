package org.example.gamecenter.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class OddOrEvenServiceTest {

    @Autowired
    private OddOrEvenService oddOrEvenService;

    @Test
    public void testGenerateRandomNumberWithinRange() {
        oddOrEvenService.generateRandomNumber();
        int randomNumber = oddOrEvenService.getRandomNumber();

        assertTrue(randomNumber >= 1 && randomNumber <= 100);
    }
}