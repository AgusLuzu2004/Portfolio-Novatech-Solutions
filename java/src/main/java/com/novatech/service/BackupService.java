package com.novatech.service;

import com.novatech.util.BackupUtil;

public class BackupService {

    public void crearBackup(String rutaDestino) {

        BackupUtil.crearBackup(rutaDestino);

    }

    public void restaurarBackup(String archivoBackup) {

        BackupUtil.restaurarBackup(archivoBackup);

    }

}
