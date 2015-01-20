package com.shop.security;

import com.shop.backend.domain.Account;
import com.shop.backend.domain.UserRoleEnum;
import com.shop.backend.repository.AccountRepository;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: Natalia
 * Date: 21.05.14
 * Time: 20:49
 * To change this template use File | Settings | File Templates.
 */
public class UserDetailsServiceImpl implements UserDetailsService {

  @Resource private AccountRepository accountRepository;

  @Override
  public UserDetails loadUserByUsername(String sId) throws UsernameNotFoundException {
    Account account = accountRepository.findByEmail(sId);
    if (account == null) {
      throw new UsernameNotFoundException("Account is not found, email is " + sId);
    }
    org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(sId, account.getPassword(), true, true, true, true, AuthorityUtils.commaSeparatedStringToAuthorityList(UserRoleEnum.ROLE_USER.name()));
    return userDetails;
  }
}