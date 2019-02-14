package com.elsevier.fca.scrum8;

import com.elsevier.fca.scrum8.services.ticketing.dto.StateTransitionRequest;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import lombok.extern.slf4j.Slf4j;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.mockserver.model.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.matchers.Times.unlimited;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@Slf4j
@CukeSteps
public class UpdateSteps {

    @LocalServerPort
    private int serverPort;
    private int mockPort = 9999;

    @Autowired
    private RestTemplate restTemplate;

    private ClientAndServer mockServer;


    @Before
    public void beforeScenario() {
        log.info("Before Scenario");
        mockServer = startClientAndServer(mockPort);
        setupGetSprintMockResponse();
        setupSearchtMockResponse();

    }

    @Given("the system has started up")
    public void theSystemHasStartedUp() {

    }

    @Given("an update for ticket (.*) with status (.*) is requested")
    public void givenASampleFileIsUploaded(final String ticketNumber, final String status) {
        setupTransitionsMockResponse(ticketNumber);
        setupTransitionRewuestMockResponse(ticketNumber);

        final StateTransitionRequest transitionRequest = new StateTransitionRequest();
        transitionRequest.setTargetState(status);
        restTemplate.postForObject("http://localhost:" + serverPort + "/tickets/" + ticketNumber, transitionRequest, Void.class);

    }

    @After
    public void afterScenario() {
        log.info("After Scenario");
        mockServer.stop();
    }

    private void setupGetSprintMockResponse() {
        final String response = JsonUtils.readJSON("test-data/sprint.json");
        new MockServerClient("127.0.0.1", mockPort)
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/rest/agile/latest/board/1/sprint")
                                .withQueryStringParameters(
                                        new Parameter("state", "active"),
                                        new Parameter("maxResults", "1")
                                ),
                        exactly(1))
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeader(new Header("Content-Type", "application/json"))
                                .withBody(response)
                );
    }

    private void setupSearchtMockResponse() {
        final String response = JsonUtils.readJSON("test-data/search.json");
        new MockServerClient("127.0.0.1", mockPort)
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/rest/api/latest/search")
                                .withQueryStringParameters(
                                        new Parameter("jql", "Sprint=1"),
                                        new Parameter("maxResults", "1000")
                                ),
                        exactly(1))
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeader(new Header("Content-Type", "application/json"))
                                .withBody(response)
                );
    }

    private void setupTransitionsMockResponse(final String ticketNr) {
        final String response = JsonUtils.readJSON("test-data/transitions.json");
        new MockServerClient("127.0.0.1", mockPort)
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/rest/api/latest/issue/" + ticketNr + "/transitions"),
                        unlimited())
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeader(new Header("Content-Type", "application/json"))
                                .withBody(response)
                );
    }

    private void setupTransitionRewuestMockResponse(final String ticketNr) {
        final String response = JsonUtils.readJSON("test-data/transitions.json");
        new MockServerClient("127.0.0.1", mockPort)
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/rest/api/latest/issue/" + ticketNr + "/transitions"),
                        unlimited())
                .respond(
                        response()
                                .withStatusCode(204)
                                .withHeader(new Header("Content-Type", "application/json"))
                                .withBody(response)
                );
    }



}
