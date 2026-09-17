package com.eibs.playground.repository;

import com.eibs.playground.model.Account;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository {
    private final DataSource dataSource;

    public AccountRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Account> findAll() throws SQLException {
        String sql = "SELECT id, account_number, holder_name, balance FROM account ORDER BY id";
        List<Account> accounts = new ArrayList<Account>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                accounts.add(mapAccount(resultSet));
            }
        }
        return accounts;
    }

    public void create(String accountNumber, String holderName, BigDecimal balance) throws SQLException {
        String sql = "INSERT INTO account (account_number, holder_name, balance) VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, accountNumber);
            statement.setString(2, holderName);
            statement.setBigDecimal(3, balance);
            statement.executeUpdate();
        }
    }

    private Account mapAccount(ResultSet resultSet) throws SQLException {
        return new Account(
                Long.valueOf(resultSet.getLong("id")),
                resultSet.getString("account_number"),
                resultSet.getString("holder_name"),
                resultSet.getBigDecimal("balance"));
    }
}
