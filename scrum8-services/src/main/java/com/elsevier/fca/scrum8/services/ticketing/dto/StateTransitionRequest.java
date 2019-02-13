package com.elsevier.fca.scrum8.services.ticketing.dto;

public class StateTransitionRequest {

  private String targetState;

  public String getTargetState() {
    return targetState;
  }

  public void setTargetState(String targetState) {
    this.targetState = targetState;
  }

}
