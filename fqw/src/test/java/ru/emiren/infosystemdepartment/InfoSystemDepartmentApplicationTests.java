package ru.emiren.infosystemdepartment;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.ModelAndView;
import ru.emiren.infosystemdepartment.Controller.SQL.SqlController;
import ru.emiren.infosystemdepartment.Service.SQL.SqlService;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class InfoSystemDepartmentApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private SqlService sqlService;

	@Autowired
	private SqlController sqlController;

	@DisplayName("Context Loads Successfully")
	@Test
	public void contextLoads() {
		Assertions.assertNotNull(sqlController,  "SQL Controller should not be null");
		Assertions.assertNotNull(sqlService, "SQL Service should not be null");
	}

	@DisplayName("Validate GET Endpoints")
	@ParameterizedTest
	@ValueSource(strings = {
			"/sql/lecturers",
			"/sql/lecturers/view",
			"/sql/upload-data-form"
	})
	public void testGetEndpoints(String url) {
		performAndValidateGetRequest(url);
	}

	@DisplayName("Validate POST /sql/lecturers Endpoint")
	@Test
	public void testPostLecturersEndpoint() {
		performAndValidatePostRequest("/sql/lecturers", "text/html;charset=UTF-8");
	}

	private void performAndValidateGetRequest(String url) {

		try {
			MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/sql/lecturers")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
			String content = result.getResponse().getContentAsString();
			Assertions.assertNotNull(content, "Response content should not be null");
			log.info("Response Content: {}", content);

		} catch (Exception e) {
			log.error("Error during GET request to {}", url, e);
			throw new RuntimeException(e);
		}
	}

	private void performAndValidatePostRequest(String url, String expectedContentType) {
		try {
			MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(url))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andReturn();

			validateResponse(result, expectedContentType, url);
		} catch (Exception e) {
			log.error("Error during POST request to {}", url, e);
			throw new RuntimeException(e);
		}
	}

	private void validateResponse(MvcResult result, String expectedContentType, String url) {
		try {
		Assertions.assertNotNull(result.getResponse().getContentAsString(),
				"Response content for " + url + " should not be null");
		Assertions.assertEquals(expectedContentType, result.getResponse().getContentType(),
				"Content type mismatch for " + url);
		}catch (Exception e) {
			log.error("Error during GET request to {}", url, e);
		}
	}
}
