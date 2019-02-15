package com.elsevier.fca.scrum8.ticketing.jira;

import java.util.ArrayList;
import java.util.List;
import com.elsevier.fca.scrum8.ticketing.jira.model.Transition;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class JiraTicketTransitionCheckTest {

  private static final String TICKET_IN_SPRINT_KEY = "SCRM8-1";
  private static final String TARGET_STATE = "To Do";
  private static final String TARGET_STATE_NOT_IN_TRANSITIONS = "In Progress";

  @Test
  public void returns_true_when_target_state_in_transitions() {
    Transition transition = new Transition();
    transition.setName(TARGET_STATE);
    List<Transition> transitionList = new ArrayList<>();
    transitionList.add(transition);

    RestClient restClient = Mockito.mock(RestClient.class);
    Mockito.when(restClient.retrieveTransitionsByIssue(TICKET_IN_SPRINT_KEY))
        .thenReturn(transitionList);
    JiraTicketTransitionCheck jiraTicketTransitionCheck = new JiraTicketTransitionCheck(restClient);
    final boolean canTransitionToState =
        jiraTicketTransitionCheck.canTransitionToState(TICKET_IN_SPRINT_KEY, TARGET_STATE);
    Assert.assertTrue(canTransitionToState);
  }

  @Test
  public void returns_false_when_target_state_not_in_transitions() {
    Transition transition = new Transition();
    transition.setName(TARGET_STATE);
    List<Transition> transitionList = new ArrayList<>();
    transitionList.add(transition);

    RestClient restClient = Mockito.mock(RestClient.class);
    Mockito.when(restClient.retrieveTransitionsByIssue(TICKET_IN_SPRINT_KEY))
        .thenReturn(transitionList);
    JiraTicketTransitionCheck jiraTicketTransitionCheck = new JiraTicketTransitionCheck(restClient);
    final boolean canTransitionToState =
        jiraTicketTransitionCheck.canTransitionToState(TICKET_IN_SPRINT_KEY, TARGET_STATE_NOT_IN_TRANSITIONS);
    Assert.assertFalse(canTransitionToState);
  }

  @Test
  public void returns_false_when_transitions_are_empty() {
    RestClient restClient = Mockito.mock(RestClient.class);
    Mockito.when(restClient.retrieveTransitionsByIssue(TICKET_IN_SPRINT_KEY))
        .thenReturn(new ArrayList<>());
    JiraTicketTransitionCheck jiraTicketTransitionCheck = new JiraTicketTransitionCheck(restClient);
    final boolean canTransitionToState =
        jiraTicketTransitionCheck.canTransitionToState(TICKET_IN_SPRINT_KEY, TARGET_STATE_NOT_IN_TRANSITIONS);
    Assert.assertFalse(canTransitionToState);
  }
}
