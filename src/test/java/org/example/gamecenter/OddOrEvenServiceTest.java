package org.example.gamecenter;

import org.example.gamecenter.service.OddOrEvenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OddOrEvenServiceTest {

    private OddOrEvenService oddOrEvenService;

    @BeforeEach
    void setUp() {
        oddOrEvenService = new OddOrEvenService();
    }

    @Test
    public void testGenerateRandomNumber() {
        oddOrEvenService.generateRandomNumber();
        int randomNumber = oddOrEvenService.getRandomNumber();

        assertTrue(randomNumber >= 1 && randomNumber <= 100);
    }
}
