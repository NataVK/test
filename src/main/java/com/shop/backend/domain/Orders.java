package com.shop.backend.domain;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.shop.backend.utils.DateUtils;
import org.hibernate.annotations.Formula;
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
  private Date created = DateUtils.now();
  private Date updated;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderId", fetch = FetchType.EAGER)
  Set<ItemOrderInfo> itemOrderInfos = new HashSet<>();
  @Transient
  private int totalPrice;

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
    int res = 0;
    for (ItemOrderInfo itemOrderInfo : itemOrderInfos) {
      res += itemOrderInfo.getPrice();
    }
    return res;
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
