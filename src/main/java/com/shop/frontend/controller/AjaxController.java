package com.shop.frontend.controller;

import com.shop.backend.service.ShopService;
import com.shop.frontend.bean.ItemBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Natalia
 * Date: 17.01.15
 * Time: 13:47
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class AjaxController {

  @Autowired private ShopService service;

  @RequestMapping(value = {"/admin/itemOrderInfos", "/user/itemOrderInfos"}, produces = "application/json")
  @ResponseBody
  public List<ItemBean> getItemOrderInfos(@RequestParam long orderId) {
    return service.getItemsList(orderId);
  }


}
