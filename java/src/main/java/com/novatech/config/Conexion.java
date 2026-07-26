package com.novatech.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    public static Connection conectar() throws SQLException {

        return DriverManager.getConnection(
                DbConfig.getUrl(),
                DbConfig.getUsuario(),
                DbConfig.getPassword()
        );

    }

}
