package com.vo2.javatest.integration;

import com.vo2.javatest.domain.dto.SampleDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JavaTestOnRandomPortIT {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void onContextLoadsRestHelloVO2IsAvailable() {
		//GIVEN : a REST template client ready to request this environment
		assertThat(restTemplate).isNotNull();

		//WHEN : calling GET /rest/hellovo2
		ResponseEntity<SampleDto> response = restTemplate.getForEntity("/rest/hellovo2", SampleDto.class);

		//THEN : the server return a status 200 and json must be as expected
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		SampleDto helloVo2 = response.getBody();
		assertThat(helloVo2).isNotNull();
		assertThat(helloVo2.getId()).isEqualTo(123L);
		assertThat(helloVo2.getMessage()).startsWith("REST GET called. Loaded on ");
	}

}
