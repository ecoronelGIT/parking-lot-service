package assessment.parkinglot;

import org.junit.jupiter.api.BeforeEach;
import org.mockserver.client.MockServerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BaseTest {

    @Autowired
    protected MockMvc mvc;

    @BeforeEach
    public void beforeEach(MockServerClient mockServer) {
        mockServer.reset();
        cleanData();
    }

    protected void cleanData() {
    }

    protected String fileFromResources(String filePath) throws URISyntaxException, IOException {
        return Files.readString(Path.of(
                Objects.requireNonNull(getClass().getClassLoader().getResource(filePath))
                        .toURI()));
    }

}
