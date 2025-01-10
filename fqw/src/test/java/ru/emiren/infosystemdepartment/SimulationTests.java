package ru.emiren.infosystemdepartment;

import io.gatling.javaapi.core.OpenInjectionStep;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.util.List;

import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class SimulationTests extends Simulation {
    private final HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8080")
            .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
            .contentTypeHeader("application/x-www-form-urlencoded");

    ScenarioBuilder scenarioBuilder = scenario("Lecturers test")
            .exec(http("Get several").get("/sql/lecturers")
                    .formParam("orientation", List.of("value1","value2"))
//                        .formParam("department", List.of("dep1","dep2")
                    .formParam("from", "2020")
                    .formParam("to", "2023")
//                        .formParam("themes", List.of("theme1","theme2"))
//                        .formParam("lecturer", List.of("lecturer1","lecturer2"))
                    .check(status().is(200)) )
            .pause(5)
            .exec(
                http("Get several tables").post("/sql/lecturers")
//                        .formParam("orientation", List.of("value1","value2"))
//                        .formParam("department", List.of("dep1","dep2")
                        .formParam("from", "2020")
                        .formParam("to", "2023")
//                        .formParam("themes", List.of("theme1","theme2"))
//                        .formParam("lecturer", List.of("lecturer1","lecturer2"))
                        .check(status().is(200))
            );
    {
        setUp(
                scenarioBuilder.injectOpen(OpenInjectionStep.atOnceUsers(25)).protocols(httpProtocol)
        );
    }
}