package com.caterpillars.StayConnect;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("dev")
@TestPropertySource(locations = "file:./.env")
class StayConnectApplicationTests {

}
