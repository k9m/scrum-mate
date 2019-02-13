package com.elsevier.fca.scrum8.ticketing.jira;

import com.elsevier.fca.scrum8.ticketing.SprintTicketCheck;
import com.elsevier.fca.scrum8.ticketing.TicketManager;
import com.elsevier.fca.scrum8.ticketing.TicketStateUpdater;
import com.elsevier.fca.scrum8.ticketing.TicketTransitionCheck;

public class TicketManagerTest {

//  @Test
  public void updateState() {
    RestClient restClient = new RestClient();
    SprintTicketCheck sprintTicketCheck = new JiraSprintTicketCheck(restClient);
    TicketTransitionCheck ticketTransitionCheck = new JiraTicketTransitionCheck(restClient);
    TicketStateUpdater ticketStateUpdater = new JiraTicketStateUpdater(restClient);
    TicketManager ticketManager =
        new TicketManager(sprintTicketCheck, ticketTransitionCheck, ticketStateUpdater);
    ticketManager.updateState("SCRM8-1", "to do");
  }

}
