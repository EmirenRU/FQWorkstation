package ru.emiren.infosystemdepartment;

import io.gatling.javaapi.core.OpenInjectionStep;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.util.List;

import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class SimulationTests extends Simulation {

    private final HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:13131")
            .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8,application/json")
            .contentTypeHeader("application/json");

    ScenarioBuilder scenarioBuilder = scenario("Lecturers test")
            .exec(http("Get Test").get("/api/v2/receive-selectors")
                    .check(status().is(200)) )
            .pause(5)
            .exec(
                http("Post Test with getting tables").post("/api/v1/receive-by-params")
                        .body(StringBody("{\"orientation\": [], \"department\": [], \"from\": \"2019\", \"till\": \"2025\", \"themes\": []}")).asJson()
                        .check(status().is(200))
            );
    {
        setUp(
                scenarioBuilder
                        .injectOpen(OpenInjectionStep.atOnceUsers(20))
                        .protocols(httpProtocol)
        );
    }
}