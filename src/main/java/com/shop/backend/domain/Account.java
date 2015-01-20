package com.shop.backend.domain;

import com.shop.backend.utils.DateUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Natalia
 * Date: 11.05.14
 * Time: 21:31
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Account implements Serializable {

  @Id @GeneratedValue private long id;
  @Column(unique=true)
  private String email;
  private String fistName;
  private String lastName;
  private String password;
  private Date created = DateUtils.now();

  public Account() {
  }

  public long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String name) {
    this.email = name;
  }

  public String getFistName() {
    return fistName;
  }

  public void setFistName(String fistName) {
    this.fistName = fistName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

}
