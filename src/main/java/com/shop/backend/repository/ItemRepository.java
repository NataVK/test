package com.shop.backend.repository;

import com.shop.backend.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.OrderBy;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bambastik
 * Date: 2/20/14
 * Time: 10:34 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ItemRepository extends JpaRepository<Item, Long> {
}
