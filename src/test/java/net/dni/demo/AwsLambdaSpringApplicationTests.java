package net.dni.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AwsLambdaSpringApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void testAccessDenied() {
        String url = "http://localhost:" + port + "/myRole";
        ResponseEntity<String> response = testRestTemplate.getForEntity(url, String.class);
        assertThat(response.getBody()).isEqualTo("Access Denied");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK); //todo: local response should be 403
    }

    @Test
    void testAdmin() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "password");
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String url = "http://localhost:" + port + "/myRole";
        ResponseEntity<String> response = testRestTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        assertThat(response.getBody()).isEqualTo("[ROLE_ADMIN]");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK); //todo: local response should be 403
    }

}
