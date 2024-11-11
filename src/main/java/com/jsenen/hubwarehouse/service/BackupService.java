package com.jsenen.hubwarehouse.service;

import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class BackupService {

    public void createDatabaseBackup() {
        try {
            // Configura el nombre y ubicación del archivo de respaldo
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String backupPath = "backup/db_backup_" + timestamp + ".sql";

            // Crea el directorio de respaldo si no existe
            File backupDir = new File("backup");
            if (!backupDir.exists()) {
                backupDir.mkdirs();
            }

            // Configura los argumentos para mysqldump
            ProcessBuilder pb = new ProcessBuilder(
                    "C:\\xampp\\mysql\\bin\\mysqldump",
                    "-u", "myuser",
                    "-p" + "mypass",  // Usa "-p" + "password" sin espacio
                    "hubwarehousedb"
            );

            // Establece la salida del proceso en un archivo
            File backupFile = new File(backupPath);
            pb.redirectOutput(backupFile);

            // Ejecuta el proceso
            Process process = pb.start();
            process.waitFor();

            System.out.println("Backup creado exitosamente en " + backupFile.getAbsolutePath());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
