package com.elsevier.fca.scrum8.ticketing.jira;

import com.elsevier.fca.scrum8.ticketing.SprintTicketCheck;
import com.elsevier.fca.scrum8.ticketing.TicketStateUpdater;
import com.elsevier.fca.scrum8.ticketing.TicketTransitionCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TicketManager {

  private final SprintTicketCheck sprintTicketCheck;
  private final TicketTransitionCheck ticketTransitionCheck;
  private final TicketStateUpdater ticketStateUpdater;

  public void updateState(String ticketId, String newState) {
    if (!sprintTicketCheck.isInActiveSprint(ticketId)) {
      throw new RuntimeException("Ticket '" + ticketId + "' not in active sprint");
    }
    if (!ticketTransitionCheck.canTransitionToState(ticketId, newState)) {
      throw new RuntimeException(
          "Ticket '" + ticketId + "' cannot transition to state '" + newState + "'");
    }
    ticketStateUpdater.updateState(ticketId, newState);
  }

}
