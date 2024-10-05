package com.jsenen.hubwarehouse.service;


import com.jsenen.hubwarehouse.domain.FarnellComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FarnellQueryServiceImpl implements FarnellQueryService {

    @Autowired
    ExternalFarnellService externalFarnellService;

    @Override
    public Iterable<FarnellComponent> findAll() {
        return null;
    }

    @Override
    public FarnellComponent findOne(String partNumber) {
        System.out.println("Calling ExternalFarnellService with part number: " + partNumber); // Añadir log
        return externalFarnellService.getComponentData(partNumber);
    }
}
