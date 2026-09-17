package com.eibs.playground.web;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@WebListener
public class DatabaseInitializer implements ServletContextListener {
    @Resource(lookup = "java:comp/env/jdbc/eibsDataSource")
    private DataSource dataSource;

    public void contextInitialized(ServletContextEvent event) {
        try {
            runScript("db/schema.sql");
            runScript("db/data.sql");
        } catch (IOException exception) {
            throw new IllegalStateException("No fue posible leer los scripts de base de datos.", exception);
        } catch (SQLException exception) {
            throw new IllegalStateException("No fue posible inicializar la base de datos.", exception);
        }
    }

    public void contextDestroyed(ServletContextEvent event) {
    }

    private void runScript(String resourceName) throws IOException, SQLException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourceName);
        if (inputStream == null) {
            throw new IOException("No se encontro el recurso " + resourceName);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
             Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            StringBuilder command = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmedLine = line.trim();
                if (trimmedLine.length() == 0 || trimmedLine.startsWith("--")) {
                    continue;
                }
                command.append(trimmedLine).append(' ');
                if (trimmedLine.endsWith(";")) {
                    statement.execute(command.substring(0, command.length() - 2));
                    command.setLength(0);
                }
            }
        }
    }
}
