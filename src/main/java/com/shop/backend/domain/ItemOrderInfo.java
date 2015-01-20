package com.shop.backend.domain;

import com.shop.backend.utils.DateUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Natalia
 * Date: 16.01.15
 * Time: 0:46
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class ItemOrderInfo implements Serializable{

  @Id @GeneratedValue private long id;
  private long orderId;
  private long itemId;
  private int num;
  private int price;
  private Date created = DateUtils.now();

  public long getId() {
    return id;
  }

  public long getItemId() {
    return itemId;
  }

  public void setItemId(long itemId) {
    this.itemId = itemId;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public int getNum() {
    return num;
  }

  public void setNum(int num) {
    this.num = num;
  }

  public long getOrderId() {
    return orderId;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public void setOrderId(long orderId) {
    this.orderId = orderId;
  }
}
