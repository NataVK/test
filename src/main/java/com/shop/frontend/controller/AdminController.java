package com.shop.frontend.controller;

import com.shop.backend.service.AdminService;
import com.shop.backend.service.ShopService;
import com.shop.frontend.bean.ItemBeans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Natalia
 * Date: 11.05.14
 * Time: 21:32
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {

  @Autowired private AdminService adminService;
  @Autowired private ShopService service;

  @RequestMapping(value = "/orders")
  public String getOrdersList(ModelMap map) {
    map.put("orders", adminService.getOrdersList());
    return "admin/orders";
  }

  @RequestMapping(value = "/items", method = RequestMethod.GET)
  public String getItemsList(ModelMap map) {
    map.put("items", service.getItemsList());
    map.put("measures", adminService.getMeasures());
    return "admin/items";
  }

  @RequestMapping(value = "/addItems", method = RequestMethod.POST)
  public String addItem(@ModelAttribute ItemBeans itemsList) {
    adminService.createItems(itemsList.getItems());
    return "redirect:items";
  }

  @RequestMapping(value = "/delItems", method = RequestMethod.POST)
  public String delItems(@RequestParam List<Long> ids) {
    adminService.deleteItems(ids);
    return "redirect:items";
  }

  @RequestMapping(value = "/updateItemName", method = RequestMethod.POST, produces = "text/html; charset=UTF-8")
  @ResponseBody
  public String updateItemName(@RequestParam("id") long id, @RequestParam("value") String name) {
    return adminService.updateItemName(id, name);
  }

  @RequestMapping(value = "/updateItemMeasure", method = RequestMethod.POST, produces = "text/html; charset=UTF-8")
  @ResponseBody
  public String updateItemMeasure(@RequestParam("id") long id, @RequestParam("value") String measure) {
    return adminService.updateItemMeasure(id, measure);
  }

  @RequestMapping(value = "/updateItemPrice", method = RequestMethod.POST, produces = "text/html; charset=UTF-8")
  @ResponseBody
  public String updateItemPice(@RequestParam("id") long id, @RequestParam("value") int price) {
    return String.valueOf(adminService.updateItemPrice(id, price));
  }
}
