package com.jsenen.hubwarehouse.repository;

import com.jsenen.hubwarehouse.domain.Component;
import com.jsenen.hubwarehouse.domain.ServiceOrders;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceOrderRepository extends CrudRepository<ServiceOrders,Long> {


    @Query("SELECT so FROM service_orders so JOIN so.serviceOrderComponents soc WHERE soc.component.idComponent = :idComponent")
    List<ServiceOrders> findByComponentId(@Param("idComponent") long idComponent);

}
