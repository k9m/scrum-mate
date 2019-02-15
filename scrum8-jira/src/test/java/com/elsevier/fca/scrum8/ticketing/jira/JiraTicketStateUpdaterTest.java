package com.elsevier.fca.scrum8.ticketing.jira;

import java.util.ArrayList;
import java.util.List;
import com.elsevier.fca.scrum8.ticketing.jira.model.Transition;
import org.junit.Test;
import org.mockito.Mockito;

public class JiraTicketStateUpdaterTest {

  private static final String TICKET_IN_SPRINT_KEY = "SCRM8-1";
  private static final String TARGET_STATE = "To Do";

  @Test
  public void updates_state_when_ticket_can_transition_to_target_state() {
    Transition transition = new Transition();
    transition.setName(TARGET_STATE);
    List<Transition> transitionsList = new ArrayList<>();
    transitionsList.add(transition);
    RestClient restClient = Mockito.mock(RestClient.class);
    Mockito.when(restClient.retrieveTransitionsByIssue(TICKET_IN_SPRINT_KEY))
        .thenReturn(transitionsList);
    JiraTicketStateUpdater jiraTicketStateUpdater = new JiraTicketStateUpdater(restClient);
    jiraTicketStateUpdater.updateState(TICKET_IN_SPRINT_KEY, TARGET_STATE);
    Mockito.verify(restClient, Mockito.times(1)).updateIssueState(TICKET_IN_SPRINT_KEY, transition);
  }

  @Test(expected = RuntimeException.class)
  public void doesnt_update_state_when_ticket_cannot_transition_to_target_state() {
    Transition transition = new Transition();
    transition.setName(TARGET_STATE);
    RestClient restClient = Mockito.mock(RestClient.class);
    Mockito.when(restClient.retrieveTransitionsByIssue(TICKET_IN_SPRINT_KEY))
        .thenReturn(new ArrayList<>());
    JiraTicketStateUpdater jiraTicketStateUpdater = new JiraTicketStateUpdater(restClient);
    jiraTicketStateUpdater.updateState(TICKET_IN_SPRINT_KEY, TARGET_STATE);
    Mockito.verify(restClient, Mockito.times(0)).updateIssueState(TICKET_IN_SPRINT_KEY, transition);
  }

}
