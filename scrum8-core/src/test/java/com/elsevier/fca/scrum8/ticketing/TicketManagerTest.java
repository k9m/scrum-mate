package com.elsevier.fca.scrum8.ticketing;

import org.junit.Test;
import org.mockito.Mockito;

public class TicketManagerTest {

  private static final String TICKET_ID = "SCRM8-1";
  private static final String TARGET_STATE = "TO DO";

  @Test
  public void updates_ticket_state_when_ticket_in_sprint_and_transition_possible() {
    SprintTicketCheck sprintTicketCheck = Mockito.mock(SprintTicketCheck.class);
    Mockito.when(sprintTicketCheck.isInActiveSprint(Mockito.anyString()))
        .thenReturn(true);

    TicketTransitionCheck ticketTransitionCheck = Mockito.mock(TicketTransitionCheck.class);
    Mockito
        .when(ticketTransitionCheck.canTransitionToState(Mockito.anyString(), Mockito.anyString()))
        .thenReturn(true);

    TicketStateUpdater ticketStatusUpdater = Mockito.mock(TicketStateUpdater.class);
    TicketManager ticketManager =
        new TicketManager(sprintTicketCheck, ticketTransitionCheck, ticketStatusUpdater);
    ticketManager.updateState(TICKET_ID, TARGET_STATE);
    Mockito.verify(ticketStatusUpdater, Mockito.times(1)).updateState(TICKET_ID, TARGET_STATE);
  }

  @Test(expected = RuntimeException.class)
  public void throws_when_ticket_not_in_sprint() {
    SprintTicketCheck sprintTicketCheck = Mockito.mock(SprintTicketCheck.class);
    Mockito.when(sprintTicketCheck.isInActiveSprint(Mockito.anyString())).thenReturn(false);
    TicketTransitionCheck ticketTransitionCheck = Mockito.mock(TicketTransitionCheck.class);
    TicketStateUpdater ticketStatusUpdater = Mockito.mock(TicketStateUpdater.class);
    TicketManager ticketManager =
        new TicketManager(sprintTicketCheck, ticketTransitionCheck, ticketStatusUpdater);
    ticketManager.updateState(TICKET_ID, TARGET_STATE);
  }

  @Test(expected = RuntimeException.class)
  public void throws_when_ticket_unable_to_transition_to_target_state() {
    SprintTicketCheck sprintTicketCheck = Mockito.mock(SprintTicketCheck.class);
    Mockito.when(sprintTicketCheck.isInActiveSprint(Mockito.anyString()))
        .thenReturn(true);

    TicketTransitionCheck ticketTransitionCheck = Mockito.mock(TicketTransitionCheck.class);
    Mockito
        .when(ticketTransitionCheck.canTransitionToState(Mockito.anyString(), Mockito.anyString()))
        .thenReturn(false);

    TicketStateUpdater ticketStatusUpdater = Mockito.mock(TicketStateUpdater.class);
    TicketManager ticketManager =
        new TicketManager(sprintTicketCheck, ticketTransitionCheck, ticketStatusUpdater);
    ticketManager.updateState(TICKET_ID, TARGET_STATE);
  }

}
