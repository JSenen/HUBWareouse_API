package com.jsenen.hubwarehouse.config;

import com.jsenen.hubwarehouse.service.BackupService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {


    private final BackupService backupService;

    public AppConfig(BackupService backupService) {
        this.backupService = backupService;
    }

    @Bean
    public ApplicationRunner backupDatabaseOnStartup() {
        return args -> {
            System.out.println("Iniciando copia de seguridad de la base de datos...");
            backupService.createDatabaseBackup();
            System.out.println("Copia de seguridad completada.");
        };
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
