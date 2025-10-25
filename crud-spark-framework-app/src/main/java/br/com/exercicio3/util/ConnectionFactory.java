package br.com.exercicio3.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	public static Connection getConnection() {
        try {
            // Lembre-se de ajustar para seus dados de conexão!
            String url = "jdbc:postgresql://localhost:5432/db_eclipse_app";
            String user = "ti2cc"; // seu usuário
            String password = "ti@cc"; // sua senha
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }
}
