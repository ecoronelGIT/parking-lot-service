package assessment.parkinglot.api;

import assessment.parkinglot.BaseTest;
import org.junit.jupiter.api.Test;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@MockServerSettings(ports = 1080, perTestSuite = true)
class HealthCheckTest extends BaseTest {

    @Test
    public void healthCheckTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/health"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
