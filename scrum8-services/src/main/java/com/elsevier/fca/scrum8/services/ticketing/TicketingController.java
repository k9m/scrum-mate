package com.elsevier.fca.scrum8.services.ticketing;

import com.elsevier.fca.scrum8.services.ticketing.dto.StateTransitionRequest;
import com.elsevier.fca.scrum8.ticketing.SprintTicketCheck;
import com.elsevier.fca.scrum8.ticketing.TicketManager;
import com.elsevier.fca.scrum8.ticketing.TicketStateUpdater;
import com.elsevier.fca.scrum8.ticketing.TicketTransitionCheck;
import com.elsevier.fca.scrum8.ticketing.jira.JiraSprintTicketCheck;
import com.elsevier.fca.scrum8.ticketing.jira.JiraTicketStateUpdater;
import com.elsevier.fca.scrum8.ticketing.jira.JiraTicketTransitionCheck;
import com.elsevier.fca.scrum8.ticketing.jira.RestClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketingController {

  private final TicketManager manager;

  public TicketingController() {
    RestClient restClient = new RestClient();
    SprintTicketCheck sprintTicketCheck = new JiraSprintTicketCheck(restClient);
    TicketTransitionCheck ticketTransitionCheck = new JiraTicketTransitionCheck(restClient);
    TicketStateUpdater ticketStateUpdater = new JiraTicketStateUpdater(restClient);
    manager = new TicketManager(sprintTicketCheck, ticketTransitionCheck, ticketStateUpdater);
  }

  @RequestMapping(path = "/tickets/{ticketId}", method = RequestMethod.POST)
  public void transitionTicket(@PathVariable String ticketId,
      @RequestBody StateTransitionRequest request) {
    manager.updateState(ticketId, request.getTargetState());
  }

}
