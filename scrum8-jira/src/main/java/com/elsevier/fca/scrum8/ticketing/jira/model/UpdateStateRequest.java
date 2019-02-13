package com.elsevier.fca.scrum8.ticketing.jira.model;

public class UpdateStateRequest {

  private Transition transition;

  private UpdateStateRequest(Builder builder) {
    this.transition = builder.transition;
  }

  public static Builder builder() {
    return new Builder();
  }

  public Transition getTransition() {
    return transition;
  }

  public void setTransition(Transition transition) {
    this.transition = transition;
  }

  public static final class Builder {

    private Transition transition;

    private Builder() {}

    public UpdateStateRequest build() {
      return new UpdateStateRequest(this);
    }

    public Builder withTransition(Transition transition) {
      this.transition = transition;
      return this;
    }

  }

}
