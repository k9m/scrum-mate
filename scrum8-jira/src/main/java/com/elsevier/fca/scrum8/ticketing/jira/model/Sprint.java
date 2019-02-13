package com.elsevier.fca.scrum8.ticketing.jira.model;

import java.util.Date;

public class Sprint {

  private int id;
  private String self;
  private String state;
  private String name;
  private Date startDate;
  private Date endDate;
  private int originBoardId;
  private String goal;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getSelf() {
    return self;
  }

  public void setSelf(String self) {
    this.self = self;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public int getOriginBoardId() {
    return originBoardId;
  }

  public void setOriginBoardId(int originBoardId) {
    this.originBoardId = originBoardId;
  }

  public String getGoal() {
    return goal;
  }

  public void setGoal(String goal) {
    this.goal = goal;
  }

}
