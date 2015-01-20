package com.shop.backend.repository;

import com.shop.backend.domain.ItemOrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Natalia
 * Date: 19.05.14
 * Time: 22:12
 * To change this template use File | Settings | File Templates.
 */
public interface ItemOrderInfoRepository extends JpaRepository<ItemOrderInfo, Long> {
  List<ItemOrderInfo> findByOrderId(long orderId);
}
