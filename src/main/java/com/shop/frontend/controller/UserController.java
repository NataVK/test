package com.shop.frontend.controller;

import com.shop.backend.domain.Account;
import com.shop.backend.service.ShopService;
import com.shop.frontend.bean.OrderBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/user")
public class UserController {

  @Autowired private ShopService service;

  @RequestMapping(value = "/orders")
  public String getOrdersList(ModelMap map, HttpServletRequest request) {
    Account account = getCurrentUser(request);
    map.put("orders", service.getOrdersList(account));
    map.put("items", service.getItemsList());
    return "user/orders";
  }

  @RequestMapping(value = "/createOrder", produces = "application/json")
  @ResponseBody
  public OrderBean createOrder(@RequestParam Map<String,String> items, HttpServletRequest request) {
    Account account = getCurrentUser(request);
    long orderId = service.createOrder(account, items);
    return service.buildOrderBean(orderId, account);
  }

  @RequestMapping(value = "/delOrder", produces = "application/json")
  @ResponseBody
  public void delOrder(@RequestParam long id) {
    service.deleteOrder(id);
  }

  @RequestMapping(value = "/getOrder", produces = "application/json")
  @ResponseBody
  public OrderBean getOrder(@RequestParam long id) {
    return service.buildOrderBean(id, null);
  }

  @RequestMapping(value = "/addItems", produces = "application/json")
  @ResponseBody
  public OrderBean addItems(@RequestParam long orderId, @RequestParam Map<String,String> items, HttpServletRequest request) {
    Account account = getCurrentUser(request);
    service.createItemOrderInfos(orderId, items, account);
    return service.buildOrderBean(orderId, account);
  }

  @RequestMapping(value = "/delItem", produces = "application/json")
  @ResponseBody
  public OrderBean delItem(@RequestParam long orderId, @RequestParam List<Long> ids) {
    service.deleteItemOrderInfos(ids);
    return service.buildOrderBean(orderId, null);
  }

  @RequestMapping(value = "/updateItemInfoNum", method = RequestMethod.POST, produces = "text/html; charset=UTF-8")
  @ResponseBody
  public String updateItemInfoNum(@RequestParam("id") long id, @RequestParam("value") int num) {
    return String.valueOf(service.updateItemOrderInfoNum(id, num));
  }

  private Account getCurrentUser(HttpServletRequest request) {
    String userName = request.getUserPrincipal().getName();//SecurityContextHolder.getContext().getAuthentication().getName();
    return service.getUserByEmail(userName);
  }

}
