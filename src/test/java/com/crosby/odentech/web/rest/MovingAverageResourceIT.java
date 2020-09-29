package com.crosby.anontech.web.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MovingAverageResourceIT {

    @Autowired
    private MockMvc restQuoteMockMvc;

    @Test
    void getQuote() throws Exception {
        await().atLeast(Duration.ofMillis(1000L));

        restQuoteMockMvc.perform(get("/cable-diameter"))
            .andExpect(status().isOk()); //weak test, but a test.
    }
}
