package com.elsevier.fca.scrum8.ticketing;

public interface TicketTransitionCheck {

  boolean canTransitionToState(String key, String newState);

}
