package com.elsevier.fca.scrum8.services.ticketing;

import com.elsevier.fca.scrum8.services.ticketing.dto.StateTransitionRequest;
import com.elsevier.fca.scrum8.ticketing.TicketManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TicketingController {

  @Autowired
  private final TicketManager manager;

  @PostMapping("/tickets/{ticketId}")
  public void transitionTicket(@PathVariable String ticketId,
      @RequestBody StateTransitionRequest request) {
    manager.updateState(ticketId, request.getTargetState());
  }

}
