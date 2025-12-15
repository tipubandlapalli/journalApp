package net.engineeringdigest.journalapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
        "weather.api.key=dummy-key-for-test",
        "weather.api.string=dummy-api-url.com"
})
class JournalAppApplicationTests {
    /**
     * This test ensures the Spring Boot application context loads successfully.
     * * If the application context loads without throwing exceptions, the test
     * is considered a success. It serves as a fundamental smoke test.
     */
    @Test
    void contextLoads(){}
}
