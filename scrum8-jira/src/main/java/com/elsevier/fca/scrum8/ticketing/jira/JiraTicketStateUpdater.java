package com.elsevier.fca.scrum8.ticketing.jira;

import java.util.List;
import com.elsevier.fca.scrum8.ticketing.TicketStateUpdater;
import com.elsevier.fca.scrum8.ticketing.jira.model.Transition;

public class JiraTicketStateUpdater implements TicketStateUpdater {

  private final RestClient restClient;

  public JiraTicketStateUpdater(RestClient restClient) {
    this.restClient = restClient;
  }

  @Override
  public void updateState(String key, String newState) {
    List<Transition> transitions = restClient.retrieveTransitionsByIssue(key);
    Transition transition = transitions.stream()
        .filter(x -> x.getName().equalsIgnoreCase(newState))
        .findAny()
        .get();
    restClient.updateIssueState(key, transition);
  }

}
