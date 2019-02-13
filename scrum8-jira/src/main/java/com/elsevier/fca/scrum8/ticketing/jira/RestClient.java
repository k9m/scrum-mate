package com.elsevier.fca.scrum8.ticketing.jira;

import java.util.List;
import com.elsevier.fca.scrum8.ticketing.jira.model.Issue;
import com.elsevier.fca.scrum8.ticketing.jira.model.Issues;
import com.elsevier.fca.scrum8.ticketing.jira.model.Sprint;
import com.elsevier.fca.scrum8.ticketing.jira.model.Sprints;
import com.elsevier.fca.scrum8.ticketing.jira.model.Transition;
import com.elsevier.fca.scrum8.ticketing.jira.model.Transitions;
import com.elsevier.fca.scrum8.ticketing.jira.model.UpdateStateRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class RestClient {

  private static final String BASE_URL = "http://10.185.21.18:8080/rest";

  private static final String AUTHORIZATION = "Basic VHNha2lyaXNHOjEyMzQ1Njc4QWE=";

  private final RestTemplate restTemplate = new RestTemplate();
  private final HttpHeaders httpHeaders;

  public RestClient() {
    httpHeaders = new HttpHeaders();
    httpHeaders.set("Authorization", AUTHORIZATION);
  }

  private <T> T simpleGet(String uri, Class<T> type) {
    HttpEntity<Object> requestEntity = new HttpEntity<>(httpHeaders);
    return restTemplate.exchange(uri, HttpMethod.GET, requestEntity, type).getBody();
  }

  public Sprint retrieveActiveSprint() {
    String uri = BASE_URL + "/agile/latest/board/1/sprint?state=active&maxResults=1";
    return simpleGet(uri, Sprints.class).getValues().get(0);
  }

  public List<Issue> retrieveIssuesBySprint(int sprintId) {
    String uri = BASE_URL + "/api/latest/search?jql=Sprint=" + sprintId + "&maxResults=1000";
    return simpleGet(uri, Issues.class).getIssues();
  }

  public List<Transition> retrieveTransitionsByIssue(String key) {
    String uri = BASE_URL + "/api/latest/issue/" + key + "/transitions";
    return simpleGet(uri, Transitions.class).getTransitions();
  }

  public void updateIssueState(String key, Transition transition) {
    String uri = BASE_URL + "/api/latest/issue/" + key + "/transitions";
    UpdateStateRequest request = UpdateStateRequest.builder()
        .withTransition(transition)
        .build();
    HttpEntity<Object> requestEntity = new HttpEntity<>(request, httpHeaders);
    restTemplate.exchange(uri, HttpMethod.POST, requestEntity, Void.class);
  }

}
