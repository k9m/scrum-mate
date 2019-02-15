package com.elsevier.fca.scrum8.ticketing.jira;

import java.util.ArrayList;
import java.util.List;
import com.elsevier.fca.scrum8.ticketing.jira.model.Issue;
import com.elsevier.fca.scrum8.ticketing.jira.model.Sprint;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class JiraSprintTicketCheckTest {

  private static final int SPRINT_ID = 1;
  private static final String TICKET_IN_SPRINT_KEY = "SCRM8-1";
  private static final String TICKET_NOT_IN_SPRINT_KEY = "SCRM8-2";

  @Test
  public void returns_true_when_ticket_in_active_sprint() {
    Sprint sprint = new Sprint();
    sprint.setId(SPRINT_ID);
    Issue issue = new Issue();
    issue.setKey(TICKET_IN_SPRINT_KEY);
    List<Issue> ticketsInSprint = new ArrayList<>();
    ticketsInSprint.add(issue);

    RestClient restClient = Mockito.mock(RestClient.class);
    Mockito.when(restClient.retrieveActiveSprint())
        .thenReturn(sprint);

    Mockito.when(restClient.retrieveIssuesBySprint(SPRINT_ID))
        .thenReturn(ticketsInSprint);

    JiraSprintTicketCheck ticketCheck = new JiraSprintTicketCheck(restClient);
    boolean inActiveSprint = ticketCheck.isInActiveSprint(TICKET_IN_SPRINT_KEY);
    Assert.assertTrue(inActiveSprint);
  }

  @Test
  public void returns_false_when_ticket_not_in_active_sprint() {
    Sprint sprint = new Sprint();
    sprint.setId(SPRINT_ID);
    Issue issue = new Issue();
    issue.setKey(TICKET_IN_SPRINT_KEY);
    List<Issue> ticketsInSprint = new ArrayList<>();
    ticketsInSprint.add(issue);

    RestClient restClient = Mockito.mock(RestClient.class);
    Mockito.when(restClient.retrieveActiveSprint())
        .thenReturn(sprint);
    Mockito.when(restClient.retrieveIssuesBySprint(SPRINT_ID))
        .thenReturn(ticketsInSprint);

    JiraSprintTicketCheck ticketCheck = new JiraSprintTicketCheck(restClient);
    final boolean inActiveSprint = ticketCheck.isInActiveSprint(TICKET_NOT_IN_SPRINT_KEY);
    Assert.assertFalse(inActiveSprint);
  }

  @Test
  public void returns_false_when_active_sprint_has_no_tickets() {
    Sprint sprint = new Sprint();
    sprint.setId(SPRINT_ID);
    RestClient restClient = Mockito.mock(RestClient.class);
    Mockito.when(restClient.retrieveActiveSprint())
        .thenReturn(sprint);

    Mockito.when(restClient.retrieveIssuesBySprint(SPRINT_ID))
        .thenReturn(new ArrayList<>());

    JiraSprintTicketCheck ticketCheck = new JiraSprintTicketCheck(restClient);
    boolean inActiveSprint = ticketCheck.isInActiveSprint(TICKET_NOT_IN_SPRINT_KEY);
    Assert.assertFalse(inActiveSprint);
  }

  @Test(expected = RuntimeException.class)
  public void throws_exception_when_no_active_sprints() {
    RestClient restClient = Mockito.mock(RestClient.class);
    Mockito.when(restClient.retrieveActiveSprint())
        .thenReturn(null);
    JiraSprintTicketCheck ticketCheck = new JiraSprintTicketCheck(restClient);
    ticketCheck.isInActiveSprint(TICKET_IN_SPRINT_KEY);
  }

  @Test(expected = RuntimeException.class)
  public void throws_exception_when_active_sprint_tickets_are_null() {
    RestClient restClient = Mockito.mock(RestClient.class);
    Sprint sprint = new Sprint();
    sprint.setId(SPRINT_ID);
    Mockito.when(restClient.retrieveActiveSprint())
        .thenReturn(sprint);

    Mockito.when(restClient.retrieveIssuesBySprint(SPRINT_ID))
        .thenReturn(null);

    JiraSprintTicketCheck ticketCheck = new JiraSprintTicketCheck(restClient);
    ticketCheck.isInActiveSprint(TICKET_IN_SPRINT_KEY);
  }

}
