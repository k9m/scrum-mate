package com.elsevier.fca.scrum8.ticketing.jira;

public class JiraSprintTicketCheckTest {

//  @Test
  public void isInActiveSprint() {
    RestClient restClient = new RestClient();
    JiraSprintTicketCheck ticketCheck = new JiraSprintTicketCheck(restClient);
    ticketCheck.isInActiveSprint("SCRM8-1");
  }

}