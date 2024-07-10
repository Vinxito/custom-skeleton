package com.mock.shared.infrastructure;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration
public abstract class AcceptanceTestCase {

    @Autowired
    protected WebApplicationContext context;
    protected MockMvc               mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .build();
    }

    @AfterEach
    public void clean() {
    }

    protected void assertResponse(String endpoint,
                                  Integer expectedStatusCode,
                                  String expectedResponse) throws Exception {
        ResultMatcher response = expectedResponse.isEmpty() ? content().string("")
                                                            : content().json(expectedResponse);
        mockMvc.perform(get(endpoint))
               .andDo(print())
               .andExpect(status().is(expectedStatusCode))
               .andExpect(response);
    }

}
