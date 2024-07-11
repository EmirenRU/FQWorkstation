package ru.emiren.infosystemdepartment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.emiren.infosystemdepartment.Config.KafkaConfig;
import ru.emiren.infosystemdepartment.Controller.API.ApplicationProgrammingInterfaceController;
import ru.emiren.infosystemdepartment.Controller.API.InterAppLayerController;
import ru.emiren.infosystemdepartment.Controller.Protocol.FunctionsController;
import ru.emiren.infosystemdepartment.Controller.SQL.SqlController;
import ru.emiren.infosystemdepartment.Controller.Support.SupportController;
import ru.emiren.infosystemdepartment.Service.Kafka.KafkaProducerService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class InfoSystemDepartmentApplicationTests {
	@Autowired
	private SqlController sqlController;
	@Autowired
	private ApplicationProgrammingInterfaceController apiController;
	@Autowired
	private InterAppLayerController iaplayerController;
	@Autowired
	private FunctionsController functionsController;
	@Autowired
	private SupportController supportController;
	@Autowired
	private KafkaProducerService producerService;

	@Autowired
	private KafkaConfig kafkaConfig;
	private MockMvc mvc;

	@Test
	void contextLoads() {
		assertNotNull(sqlController);
		assertNotNull(apiController);
		assertNotNull(iaplayerController);
		assertNotNull(functionsController);
		assertNotNull(supportController);
		assertNotNull(kafkaConfig);
		assertNotNull(producerService.getKafkaTemplate());
	}

	@Test
	void givenKafkaIsRunning_whenCheckedForConnection_thenConnectionIsVerified() throws  Exception {
		boolean alive = kafkaConfig.verifyConnection();
		assertTrue(alive);
 	}

}
