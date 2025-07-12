package com.example.ticket_utils.v1.domain.model;

public abstract class Dc extends ComparableEntity {

  public abstract String getName();

  public String getDescription() {
    return null;
  }

  public Long getParentId() {
    return null;
  }

  public boolean isActive() {
    return true;
  }
}

