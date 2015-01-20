package com.shop.backend.repository;

import com.shop.backend.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Natalia
 * Date: 11.05.14
 * Time: 22:17
 * To change this template use File | Settings | File Templates.
 */
public interface OrderRepository extends JpaRepository<Orders, Long> {
  List<Orders> findByUserIdOrderByIdDesc(long userId);
}
