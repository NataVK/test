package com.shop.frontend.controller;

import com.shop.backend.domain.*;
import com.shop.backend.repository.AccountRepository;
import com.shop.backend.utils.DateUtils;
import com.shop.frontend.bean.AccountBean;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: Natalia
 * Date: 21.05.14
 * Time: 21:28
 * To change this template use File | Settings | File Templates.
 */

/**
 * Entry point for authentication.
 * Pay an attention: all authorization processing by Spring Security.
 * AuthenticationController does some business logic: send changePasswordTokens and handle a changing password.
 * Plan to generalize this Controller and use it with other product components.
 */
@Controller
@RequestMapping(value = "/auth")
public class AuthenticationController {

  @Resource private AccountRepository accountRepository;

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String doLogin(@RequestParam(required = false) boolean fail, @RequestParam(required = false) String email, HttpServletRequest request, ModelMap modelMap) {
    modelMap.put("account", new AccountBean());
    modelMap.put("fail", fail);
    modelMap.put("email", email);
    return "login";
  }

  @RequestMapping(value = "/checkEmail", method = RequestMethod.POST)
  @ResponseBody
  public boolean checkEmail(@RequestParam String email) {
    Account account = accountRepository.findByEmail(email);
    return account == null;
  }

  @RequestMapping(value = "/register", method = RequestMethod.POST)
  @ResponseBody
  public boolean doRegister(@ModelAttribute("account") AccountBean accountBean, ModelMap modelMap, HttpServletRequest request) {
    Account account = accountRepository.findByEmail(accountBean.getEmail());
    if (account == null) {
      account = new Account();
      BeanUtils.copyProperties(accountBean, account);
      account = accountRepository.save(account);
      return true;
    } else {
      return false;
    }
  }

}