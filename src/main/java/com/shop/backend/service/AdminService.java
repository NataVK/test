package com.shop.backend.service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.shop.backend.utils.DateUtils;
import com.shop.backend.domain.*;
import com.shop.backend.repository.*;
import com.shop.backend.utils.ShopUtils;
import com.shop.frontend.bean.ItemBean;
import com.shop.frontend.bean.OrderBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class AdminService {

  private static final Logger LOGGER = LoggerFactory.getLogger(AdminService.class);

  @Resource private OrderRepository orderRepository;
  @Resource private ItemRepository itemRepository;
  @Resource private AccountRepository accountRepository;
  @Resource private ItemMeasureRepository measureRepository;

  public List<OrderBean> getOrdersList() {
    List<Orders> orders = orderRepository.findAll(new Sort(new Sort.Order(Sort.Direction.DESC, "id")));
    return Lists.transform(orders, new Function<Orders, OrderBean>() {
      @Override
      public OrderBean apply(Orders order) {
        OrderBean bean = new OrderBean();
        BeanUtils.copyProperties(order, bean);
        Account account = accountRepository.findOne(order.getUserId());
        bean.setUserFistName(account.getFistName());
        bean.setUserLastName(account.getLastName());
        bean.setUserEmail(account.getEmail());
        return bean;
      }
    });
  }
  public List<String> getMeasures() {
    return Lists.transform(measureRepository.findAll(new Sort(new Sort.Order(Sort.Direction.ASC, "name"))), new Function<ItemMeasure, String>() {
      @Override
      public String apply(ItemMeasure measure) {
        return measure.getName();
      }
    });
  }

  @Transactional
  public void createItems(List<ItemBean> itemBeans) {
    List<Item> items = new ArrayList<>();
    for (ItemBean itemBean : itemBeans) {
      items.add(new Item(itemBean.getName(), itemBean.getMeasure(), itemBean.getPrice()));
    }
    itemRepository.save(items);
  }

  @Transactional
  public void deleteItems(List<Long> ids) {
    itemRepository.delete(itemRepository.findAll(ids));
  }

  @Transactional
  public String updateItemName(long id, String name) {
    Item item = itemRepository.findOne(id);
    item.setName(name);
    item = itemRepository.save(item);
    return item.getName();
  }

  @Transactional
  public String updateItemMeasure(long id, String measure) {
    Item item = itemRepository.findOne(id);
    item.setMeasure(measure);
    item = itemRepository.save(item);
    return item.getMeasure();
  }

  @Transactional
  public Integer updateItemPrice(long id, int price) {
    Item item = itemRepository.findOne(id);
    item.setPrice(price);
    item = itemRepository.save(item);
    return item.getPrice();
  }
}