package com.shop.backend.service;

import com.shop.backend.domain.*;
import com.shop.backend.repository.*;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.shop.backend.utils.DateUtils;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Natalia
 * Date: 21.05.14
 * Time: 22:36
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ShopService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ShopService.class);

  @Resource private AccountRepository accountRepository;
  @Resource private OrderRepository orderRepository;
  @Resource private ItemRepository itemRepository;
  @Resource private ItemOrderInfoRepository itemOrderInfoRepository;

  public List<OrderBean> getOrdersList(final Account account) {
    List<Orders> orders = orderRepository.findByUserIdOrderByIdDesc(account.getId());
    return Lists.transform(orders, new Function<Orders, OrderBean>() {
      @Override
      public OrderBean apply(Orders order) {
        return buildOrderBean(order, account);
      }
    });
  }

  public List<ItemBean> getItemsList(long orderId) {
    List<ItemOrderInfo> itemOrderInfos = itemOrderInfoRepository.findByOrderId(orderId);
    List<ItemBean> beans = Lists.transform(itemOrderInfos, new Function<ItemOrderInfo, ItemBean>() {
      @Override
      public ItemBean apply(ItemOrderInfo itemOrderInfo) {
        return buildItemBean(itemOrderInfo);
      }
    });
    /*Collections.sort(beans, new Comparator<ItemBean>() {
      @Override
      public int compare(ItemBean o1, ItemBean o2) {
        return o1.getName().compareToIgnoreCase(o2.getName());
      }
    });*/
    return beans;
  }

  public List<ItemBean> getItemsList() {
    List<Item> items = itemRepository.findAll(new Sort(new org.springframework.data.domain.Sort.Order("name")));
    return Lists.transform(items, new Function<Item, ItemBean>() {
      @Override
      public ItemBean apply(Item item) {
        return ShopUtils.buildItemBean(item);
      }
    });
  }

  public OrderBean buildOrderBean(long orderId, Account account) {
    return buildOrderBean(orderRepository.findOne(orderId), account);
  }

  private OrderBean buildOrderBean(Orders order, Account account) {
    OrderBean bean = new OrderBean();
    BeanUtils.copyProperties(order, bean);
    bean.setUpdated(DateUtils.getDateAsStr(order.getUpdated() != null ? order.getUpdated() : order.getCreated()));
    if (account != null) {
      bean.setUserFistName(account.getFistName());
      bean.setUserLastName(account.getLastName());
      bean.setUserEmail(account.getEmail());
    }
    return bean;
  }

  private ItemBean buildItemBean(ItemOrderInfo itemOrderInfo) {
    ItemBean bean = new ItemBean();
    BeanUtils.copyProperties(itemOrderInfo, bean);
    bean.setUpdated(DateUtils.getDateAsStr(itemOrderInfo.getCreated()));
    Item item = itemRepository.findOne(itemOrderInfo.getItemId());
    bean.setName(item.getName());
    bean.setMeasure(item.getMeasure());
    return bean;
  }

  public Account getUserByEmail(String email){
    return accountRepository.findByEmail(email);
  }

  @Transactional
  public void createItemOrderInfos(long orderId, Map<String,String> items, Account account) {
    List<ItemOrderInfo> itemOrderInfos = new ArrayList<>();
    for (String itemId : items.keySet()) {
      if (ShopUtils.isNumeric(itemId) && ShopUtils.isNumeric(items.get(itemId))) {
        long id = Long.parseLong(itemId);
        ItemOrderInfo itemOrderInfo = new ItemOrderInfo();
        itemOrderInfo.setOrderId(orderId);
        itemOrderInfo.setItemId(id);
        itemOrderInfo.setNum(Integer.parseInt(items.get(itemId)));
        Item item = itemRepository.findOne(id);
        itemOrderInfo.setPrice(item.getPrice() * itemOrderInfo.getNum());
        itemOrderInfos.add(itemOrderInfo);
      }
    }
    if (!itemOrderInfos.isEmpty()) {
      itemOrderInfoRepository.save(itemOrderInfos);
    }
  }

  @Transactional
  public void deleteItemOrderInfos(List<Long> ids) {
    itemOrderInfoRepository.delete(itemOrderInfoRepository.findAll(ids));
  }

  @Transactional
  public Integer updateItemOrderInfoNum(long itemId, int num) {
    ItemOrderInfo itemOrderInfo = itemOrderInfoRepository.findOne(itemId);
    Item item = itemRepository.findOne(itemOrderInfo.getItemId());
    itemOrderInfo.setNum(num);
    itemOrderInfo.setPrice(num*item.getPrice());
    itemOrderInfo = itemOrderInfoRepository.save(itemOrderInfo);
    return itemOrderInfo.getNum();
  }

  @Transactional
  public long createOrder(Account account, Map<String,String> items) {
    Orders order = new Orders();
    order.setUserId(account.getId());
    order = orderRepository.save(order);
    createItemOrderInfos(order.getId(), items, account);
    return order.getId();
  }

  @Transactional
  public void deleteOrder(long id) {
    orderRepository.delete(id);
  }
}
