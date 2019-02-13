package com.elsevier.fca.scrum8.ticketing.jira.model;

import java.util.List;

public class Sprints {

  private int maxResults;
  private int startAt;
  private boolean isLast;
  private List<Sprint> values;

  public int getMaxResults() {
    return maxResults;
  }

  public void setMaxResults(int maxResults) {
    this.maxResults = maxResults;
  }

  public int getStartAt() {
    return startAt;
  }

  public void setStartAt(int startAt) {
    this.startAt = startAt;
  }

  public boolean isLast() {
    return isLast;
  }

  public void setLast(boolean last) {
    isLast = last;
  }

  public List<Sprint> getValues() {
    return values;
  }

  public void setValues(List<Sprint> values) {
    this.values = values;
  }

}
