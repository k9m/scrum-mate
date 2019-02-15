Feature: Testing JIRA ticket update feature

  Background:
    Given the system has started up

  Scenario: The OCR application requests the update of a JIRA ticket
    Given an update for ticket SCRM8-1 with status Done is requested
    Then the response should be 200 with message Updated


  Scenario: The OCR application requests the update of a non-existing JIRA ticket
    Given an update for ticket WRONG_TICKET_ID with status Done is requested
    Then an exception should be raised with message Ticket 'WRONG_TICKET_ID' not in active sprint


  Scenario: The OCR application requests the update of a JIRA ticket with a non-existing state
    Given an update for ticket SCRM8-1 with status WRONG_STATE is requested
    Then an exception should be raised with message Ticket 'SCRM8-1' cannot transition to state 'WRONG_STATE'



