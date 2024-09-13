package org.example.gamecenter.service;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.Random;

@Data
@Service
@Configuration
public class OddOrEvenService {

    private Random random = new Random();
    private int randomNumber;

    public void generateRandomNumber() {
        randomNumber = random.nextInt(100) + 1;
    }
}
