package com.elsevier.fca.scrum8.services.ticketing;

import com.elsevier.fca.scrum8.services.ticketing.dto.StateTransitionRequest;
import com.elsevier.fca.scrum8.ticketing.TicketManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class TicketingController {

  @Autowired
  private final TicketManager manager;

  @PostMapping("/tickets/{ticketId}")
  public void transitionTicket(@PathVariable String ticketId,
      @RequestBody StateTransitionRequest request) {

    log.info("Requesting status update for {} with status {}", ticketId, request.getTargetState());
    manager.updateState(ticketId, request.getTargetState());
  }

}
