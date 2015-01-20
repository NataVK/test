package com.shop.backend.repository;

import com.shop.backend.domain.ItemMeasure;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created with IntelliJ IDEA.
 * User: Natalia
 * Date: 18.01.15
 * Time: 19:38
 * To change this template use File | Settings | File Templates.
 */
public interface ItemMeasureRepository extends JpaRepository<ItemMeasure, Long> {
}
