package org.example.gamecenter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class OddOrEvenTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testOddOrEvenReturnOddOrEven() throws Exception {
        mockMvc.perform(get("/oddOrEven"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("oddOrEven"));
    }
}
