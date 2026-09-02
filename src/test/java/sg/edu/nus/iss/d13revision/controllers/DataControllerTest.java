package sg.edu.nus.iss.d13revision.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(DataController.class)
class DataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void healthCheck_returnsOkMessage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string("HEALTH CHECK OK!"));
    }

    @Test
    void version_returnsVersionString() throws Exception {
        mockMvc.perform(get("/version"))
                .andExpect(status().isOk())
                .andExpect(content().string("The actual version is 1.0.0"));
    }

    @Test
    void nations_returnsJsonArrayOfTen() throws Exception {
        mockMvc.perform(get("/nations"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(10)));
    }

    @Test
    void nations_eachEntryHasExpectedFields() throws Exception {
        mockMvc.perform(get("/nations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nationality").isNotEmpty())
                .andExpect(jsonPath("$[0].capitalCity").isNotEmpty())
                .andExpect(jsonPath("$[0].flag").isNotEmpty())
                .andExpect(jsonPath("$[0].language").isNotEmpty())
                .andExpect(jsonPath("$[9].nationality").isNotEmpty());
    }

    @Test
    void currencies_returnsJsonArrayOfTwenty() throws Exception {
        mockMvc.perform(get("/currencies"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(20)));
    }

    @Test
    void currencies_eachEntryHasNameAndCode() throws Exception {
        mockMvc.perform(get("/currencies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").isNotEmpty())
                .andExpect(jsonPath("$[0].code").isNotEmpty())
                .andExpect(jsonPath("$[19].name").isNotEmpty())
                .andExpect(jsonPath("$[19].code").isNotEmpty());
    }

    @Test
    void unknownEndpoint_returnsNotFound() throws Exception {
        mockMvc.perform(get("/does-not-exist"))
                .andExpect(status().isNotFound());
    }
}
