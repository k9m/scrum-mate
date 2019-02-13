package com.elsevier.fca.scrum8.ticketing.jira;

import com.elsevier.fca.scrum8.ticketing.TicketStateUpdater;

public class JiraTicketStateUpdaterTest {

//  @Test
  public void updateState() {
    RestClient restClient = new RestClient();
    TicketStateUpdater update = new JiraTicketStateUpdater(restClient);
    update.updateState("SCRM8-2", "in progress");
  }

}