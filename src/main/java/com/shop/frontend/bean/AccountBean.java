package com.shop.frontend.bean;

/**
 * Created with IntelliJ IDEA.
 * User: Natalia
 * Date: 25.05.14
 * Time: 14:54
 * To change this template use File | Settings | File Templates.
 */
public class AccountBean {

  private long id;
  private String email;
  private String fistName;
  private String lastName;
  private String password;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
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

}
