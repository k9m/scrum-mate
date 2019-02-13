package com.elsevier.fca.scrum8.ticketing;

public class TicketManager {

  private final SprintTicketCheck sprintTicketCheck;
  private final TicketTransitionCheck ticketTransitionCheck;
  private final TicketStateUpdater ticketStateUpdater;

  public TicketManager(SprintTicketCheck sprintTicketCheck, TicketTransitionCheck ticketTransitionCheck, TicketStateUpdater ticketStateUpdater) {
    this.sprintTicketCheck = sprintTicketCheck;
    this.ticketTransitionCheck = ticketTransitionCheck;
    this.ticketStateUpdater = ticketStateUpdater;
  }

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
