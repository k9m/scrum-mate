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
    List<Issue> issues = restClient.retrieveIssuesBySprint(sprint.getId());
    return issues.stream().anyMatch(x -> x.getKey().equals(key));
  }

}
