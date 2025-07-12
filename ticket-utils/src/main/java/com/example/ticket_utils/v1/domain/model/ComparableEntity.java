package com.example.ticket_utils.v1.domain.model;

import org.hibernate.Hibernate;
import java.io.Serializable;

public abstract class ComparableEntity implements Serializable {

  public abstract Long getId();

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    ComparableEntity that = (ComparableEntity) o;
    return getId().equals(that.getId());
  }

  @Override
  public int hashCode() {
    return getId().hashCode();
  }
}
