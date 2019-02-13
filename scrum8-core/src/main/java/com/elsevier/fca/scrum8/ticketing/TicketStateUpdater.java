package com.elsevier.fca.scrum8.ticketing;

public interface TicketStateUpdater {

  void updateState(String key, String newState);

}
