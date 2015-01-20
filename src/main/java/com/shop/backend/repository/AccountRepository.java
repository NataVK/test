package com.shop.backend.repository;

import com.shop.backend.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created with IntelliJ IDEA.
 * User: Natalia
 * Date: 11.05.14
 * Time: 22:23
 * To change this template use File | Settings | File Templates.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
  Account findByEmail(String email);
}
