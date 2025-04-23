package com.jsenen.hubwarehouse.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class BackupService {

    private static final Logger logger = LoggerFactory.getLogger(BackupService.class);

    // Backup diario a las 3:00 AM
    @Scheduled(cron = "0 0 3 * * *")
    public void scheduledBackup() {
        logger.info("Iniciando backup programado");
        createDatabaseBackup();
        logger.info("Backup programado completado");
    }

    public void createDatabaseBackup() {
        try {
            // Nombre del archivo con timestamp
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String backupDirPath = "/var/backups/hubwarehouse";
            String backupFilePath = backupDirPath + "/db_backup_" + timestamp + ".sql";

            // Crear carpeta si no existe
            File backupDir = new File(backupDirPath);
            if (!backupDir.exists()) {
                backupDir.mkdirs();
            }

            // Comando mysqldump

            ProcessBuilder pb = new ProcessBuilder(
                    "/usr/bin/mysqldump", // Ruta común en Linux (ajústala si es distinta)
                    "-u", "myuser",
                    "-p" + "mypass",
                    "hubwarehousedb"
            );

            pb.redirectOutput(new File(backupFilePath));

            Process process = pb.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                logger.info("Backup creado exitosamente en: {}", backupFilePath);
            } else {
                logger.error("Error al crear el backup. Código de salida: " + exitCode);
            }

        } catch (Exception e) {
            logger.error("Excepción durante el backup: ", e);
        }
    }
}
