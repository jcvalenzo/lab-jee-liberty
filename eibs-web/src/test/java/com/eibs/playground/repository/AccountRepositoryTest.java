package com.eibs.playground.repository;

import com.eibs.playground.model.Account;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AccountRepositoryTest {
    private AccountRepository repository;

    @Before
    public void setUp() throws Exception {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:eibs-test;DB_CLOSE_DELAY=-1");
        loadDataset(dataSource, "db/test-schema.sql");
        loadDataset(dataSource, "db/test-data.sql");
        repository = new AccountRepository(dataSource);
    }

    @Test
    public void findsSeededAccounts() throws Exception {
        List<Account> accounts = repository.findAll();

        assertEquals(2, accounts.size());
        assertEquals("LAB-1001", accounts.get(0).getAccountNumber());
    }

    @Test
    public void createsAnAccount() throws Exception {
        repository.create("LAB-1003", "Carla Diaz", new BigDecimal("99.99"));

        assertEquals(3, repository.findAll().size());
    }

    private void loadDataset(DataSource dataSource, String resourceName) throws Exception {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream(resourceName), StandardCharsets.UTF_8));
             Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() > 0) {
                    statement.execute(line);
                }
            }
        }
    }
}
