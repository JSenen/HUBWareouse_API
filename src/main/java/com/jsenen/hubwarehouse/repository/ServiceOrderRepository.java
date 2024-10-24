package com.jsenen.hubwarehouse.repository;

import com.jsenen.hubwarehouse.domain.Component;
import com.jsenen.hubwarehouse.domain.ServiceOrders;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceOrderRepository extends CrudRepository<ServiceOrders,Long> {


}
