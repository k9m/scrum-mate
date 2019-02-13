package com.elsevier.fca.scrum8.ticketing.jira;

import static org.junit.Assert.*;
import org.junit.Test;

public class JiraSprintTicketCheckTest {

  @Test
  public void isInActiveSprint() {
    RestClient restClient = new RestClient();
    JiraSprintTicketCheck ticketCheck = new JiraSprintTicketCheck(restClient);
    ticketCheck.isInActiveSprint("SCRM8-1");
  }

}