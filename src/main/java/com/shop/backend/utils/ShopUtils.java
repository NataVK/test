package com.shop.backend.utils;

import com.shop.backend.domain.Item;
import com.shop.frontend.bean.ItemBean;
import org.springframework.beans.BeanUtils;

/**
 * Created with IntelliJ IDEA.
 * User: Natalia
 * Date: 17.01.15
 * Time: 13:58
 * To change this template use File | Settings | File Templates.
 */
public class ShopUtils {

  public static ItemBean buildItemBean(Item item) {
    ItemBean bean = new ItemBean();
    BeanUtils.copyProperties(item, bean);
    bean.setUpdated(DateUtils.getDateAsStr(item.getUpdated() != null ? item.getUpdated() : item.getCreated()));
    return bean;
  }

  public static boolean isNumeric(String str) {
    return str.matches("-?\\d+(\\.\\d+)?");
  }
}
