package com.shop.backend.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: bambastik
 * Date: 2/20/14
 * Time: 10:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class DateUtils {

  public static Date now() {
    return new Date();
  }

  public static String getDateAsStr(Date date) {
    return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date);
  }
}
