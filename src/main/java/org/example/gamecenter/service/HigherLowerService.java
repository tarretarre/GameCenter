package org.example.gamecenter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@RequiredArgsConstructor
@Service
public class HigherLowerService {

    public int randomNumberGenerator(){
        Random rN = new Random();
        return rN.nextInt(101);
    }

    public int controllGuess(String guess, int randomNumber){
        int guessInt = Integer.parseInt(guess);
        if(guessInt > randomNumber){
            return 2;
        }else if(guessInt < randomNumber){
            return 1;
        } else return 0;
    }
}
