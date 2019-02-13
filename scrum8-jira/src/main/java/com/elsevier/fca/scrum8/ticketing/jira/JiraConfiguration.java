package com.elsevier.fca.scrum8.ticketing.jira;

import com.elsevier.fca.scrum8.ticketing.SprintTicketCheck;
import com.elsevier.fca.scrum8.ticketing.TicketManager;
import com.elsevier.fca.scrum8.ticketing.TicketStateUpdater;
import com.elsevier.fca.scrum8.ticketing.TicketTransitionCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JiraConfiguration {

    private final SprintTicketCheck sprintTicketCheck;
    private final TicketTransitionCheck ticketTransitionCheck;
    private final TicketStateUpdater ticketStateUpdater;

    @Bean
    public TicketManager createManager(){
        return new TicketManager(
                sprintTicketCheck,
                ticketTransitionCheck,
                ticketStateUpdater);
    }


}
