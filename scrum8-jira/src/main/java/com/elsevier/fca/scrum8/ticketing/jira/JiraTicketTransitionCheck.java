package com.elsevier.fca.scrum8.ticketing.jira;

import com.elsevier.fca.scrum8.ticketing.TicketTransitionCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JiraTicketTransitionCheck implements TicketTransitionCheck {

  private final RestClient restClient;

  @Override
  public boolean canTransitionToState(String key, String newState) {
    return restClient.retrieveTransitionsByIssue(key).stream()
        .anyMatch(x -> x.getName().equalsIgnoreCase(newState));
  }

}
