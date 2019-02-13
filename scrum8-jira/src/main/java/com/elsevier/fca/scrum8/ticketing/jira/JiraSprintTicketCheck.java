package com.elsevier.fca.scrum8.ticketing.jira;

import java.util.List;
import com.elsevier.fca.scrum8.ticketing.SprintTicketCheck;
import com.elsevier.fca.scrum8.ticketing.jira.model.Issue;
import com.elsevier.fca.scrum8.ticketing.jira.model.Sprint;

public class JiraSprintTicketCheck implements SprintTicketCheck {

  private final RestClient restClient;

  public JiraSprintTicketCheck(RestClient restClient) {
    this.restClient = restClient;
  }

  @Override
  public boolean isInActiveSprint(String key) {
    Sprint sprint = restClient.retrieveActiveSprint();
    List<Issue> issues = restClient.retrieveIssuesBySprint(sprint.getId());
    return issues.stream().anyMatch(x -> x.getKey().equals(key));
  }

}
