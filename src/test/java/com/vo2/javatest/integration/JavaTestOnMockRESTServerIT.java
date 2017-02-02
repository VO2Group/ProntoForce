package com.vo2.javatest.integration;

import com.vo2.javatest.domain.dto.SampleDto;
import com.vo2.javatest.services.SampleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class JavaTestOnMockRESTServerIT {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SampleService sampleService;

	@Test
	public void restCallByIdShouldReturnValidJson() throws Exception {
		//GIVEN : a mocked behaviour for spring data repository
		long id = 123456L;
		String message = "a dummy message for a dummy entity in a mocked server..";
		SampleDto dto = new SampleDto();
		dto.setId(id);
		dto.setMessage(message);

		given(sampleService.load(id)).willReturn(Optional.of(dto));

		//WHEN : calling GET /rest/sample/{id}
		 mockMvc.perform(get("/rest/sample/" + id).accept(MediaType.APPLICATION_JSON_UTF8))

		//THEN : returned dto converted to json is OK and its value is as expected
				 .andExpect(status().isOk())
				 .andExpect(content().json("{\"id\":" + id + "}," +
						 "\"message\": \""+ message + "\""));

		 verify(sampleService).load(id);
	}

}
