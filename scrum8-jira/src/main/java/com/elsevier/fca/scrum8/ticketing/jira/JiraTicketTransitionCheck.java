package com.elsevier.fca.scrum8.ticketing.jira;

import com.elsevier.fca.scrum8.ticketing.TicketTransitionCheck;

public class JiraTicketTransitionCheck implements TicketTransitionCheck {

  private final RestClient restClient;

  public JiraTicketTransitionCheck(RestClient restClient) {
    this.restClient = restClient;
  }

  @Override
  public boolean canTransitionToState(String key, String newState) {
    return restClient.retrieveTransitionsByIssue(key).stream()
        .anyMatch(x -> x.getName().equalsIgnoreCase(newState));
  }

}
