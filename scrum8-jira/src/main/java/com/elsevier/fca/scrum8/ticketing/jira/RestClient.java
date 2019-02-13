package com.elsevier.fca.scrum8.ticketing.jira;

import com.elsevier.fca.scrum8.ticketing.jira.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class RestClient {

  @Autowired
  private RestConfiguration restConfiguration;
  
  @Autowired
  private RestTemplate restTemplate;  

  private HttpHeaders httpHeaders;

  @PostConstruct
  public void init() {
    httpHeaders = new HttpHeaders();
    httpHeaders.set("Authorization", restConfiguration.getAuth());
  }

  private <T> T simpleGet(String uri, Class<T> type) {
    HttpEntity<Object> requestEntity = new HttpEntity<>(httpHeaders);
    return restTemplate.exchange(uri, HttpMethod.GET, requestEntity, type).getBody();
  }

  public Sprint retrieveActiveSprint() {
    String uri = restConfiguration.getBaseUrl() + "/agile/latest/board/1/sprint?state=active&maxResults=1";
    return simpleGet(uri, Sprints.class).getValues().get(0);
  }

  public List<Issue> retrieveIssuesBySprint(int sprintId) {
    String uri = restConfiguration.getBaseUrl() + "/api/latest/search?jql=Sprint=" + sprintId + "&maxResults=1000";
    return simpleGet(uri, Issues.class).getIssues();
  }

  public List<Transition> retrieveTransitionsByIssue(String key) {
    String uri = restConfiguration.getBaseUrl() + "/api/latest/issue/" + key + "/transitions";
    return simpleGet(uri, Transitions.class).getTransitions();
  }

  public void updateIssueState(String key, Transition transition) {
    String uri = restConfiguration.getBaseUrl() + "/api/latest/issue/" + key + "/transitions";
    UpdateStateRequest request = UpdateStateRequest.builder()
        .withTransition(transition)
        .build();
    HttpEntity<Object> requestEntity = new HttpEntity<>(request, httpHeaders);
    restTemplate.exchange(uri, HttpMethod.POST, requestEntity, Void.class);
  }

}
