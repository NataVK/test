package com.shop.backend.domain;

import com.shop.backend.utils.DateUtils;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Natalia
 * Date: 11.05.14
 * Time: 21:35
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Orders implements Serializable {

  @Id @GeneratedValue private long id;
  private long userId;
  private int totalPrice;
  private Date created = DateUtils.now();
  private Date updated;

  public long getId() {
    return id;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public int getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(int totalPrice) {
    this.totalPrice = totalPrice;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Date getUpdated() {
    return updated;
  }

  public void setUpdated(Date updated) {
    this.updated = updated;
  }
}
