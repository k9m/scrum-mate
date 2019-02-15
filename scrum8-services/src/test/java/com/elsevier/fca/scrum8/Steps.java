package com.elsevier.fca.scrum8;

import com.elsevier.fca.scrum8.services.ticketing.dto.StateTransitionRequest;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import lombok.extern.slf4j.Slf4j;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.Header;
import org.mockserver.model.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static com.elsevier.fca.scrum8.Scrum8IntegrationTests.mockPort;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.matchers.Times.unlimited;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@Slf4j
@CukeSteps
public class Steps {

    @LocalServerPort
    private int serverPort;

    @Autowired
    private RestTemplate restTemplate;

    private Exception lastThrownException;


    @Before
    public void beforeScenario() {
        log.info("Before Scenario");
        new MockServerClient("localhost", serverPort).reset();
        setupGetSprintMockResponse();
        setupSearchtMockResponse();

    }

    @Given("the system has started up")
    public void theSystemHasStartedUp() {
        //TODO ping actuator
    }

    @Given("an update for ticket (.*) with status (.*) is requested")
    public void givenASampleFileIsUploaded(final String ticketNumber, final String status) {
        setupTransitionsMockResponse(ticketNumber);
        setupTransitionRewuestMockResponse(ticketNumber);

        final StateTransitionRequest transitionRequest = new StateTransitionRequest();
        transitionRequest.setTargetState(status);
        try {
            restTemplate.postForObject("http://localhost:" + serverPort + "/tickets/" + ticketNumber, transitionRequest, Void.class);
        } catch (RestClientException e) {
            lastThrownException = e;
            log.error("@@@ Exception scenario:", e);
        }

    }

    @Then("^the response should be (\\d+) with message (.*)$")
    public void theResponseShouldBeWithMessageUpdated(final int httpStatus, final String msg) {
        //TODO implement response
    }


    @Then("^an exception should be raised with message (.*)$")
    public void exceptionShouldBeRaised(final String msg) {
        assertThat(lastThrownException.getMessage().equals(msg));
    }

    @After
    public void afterScenario() {
        log.info("After Scenario");
//        mockServer.stop();
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
