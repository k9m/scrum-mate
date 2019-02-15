package com.elsevier.fca.scrum8.ticketing.jira;

import com.elsevier.fca.scrum8.ticketing.SprintTicketCheck;
import com.elsevier.fca.scrum8.ticketing.jira.model.Issue;
import com.elsevier.fca.scrum8.ticketing.jira.model.Sprint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JiraSprintTicketCheck implements SprintTicketCheck {

  private final RestClient restClient;

  @Override
  public boolean isInActiveSprint(String key) {
    Sprint sprint = restClient.retrieveActiveSprint();
    if (sprint == null) {
      throw new RuntimeException("No Sprint is active in Jira");
    }

    List<Issue> issues = restClient.retrieveIssuesBySprint(sprint.getId());
    if (issues == null) {
      final String message = String
          .format("Unexpected response from Jira when retrieving issues in Sprint %s",
              sprint.getName());
      throw new RuntimeException(message);
    }

    return issues.stream().anyMatch(x -> x.getKey().equals(key));
  }

}
