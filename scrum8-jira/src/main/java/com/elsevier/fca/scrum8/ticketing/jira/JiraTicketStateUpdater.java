package com.elsevier.fca.scrum8.ticketing.jira;

import com.elsevier.fca.scrum8.ticketing.TicketStateUpdater;
import com.elsevier.fca.scrum8.ticketing.jira.model.Transition;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JiraTicketStateUpdater implements TicketStateUpdater {

  private final RestClient restClient;

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
