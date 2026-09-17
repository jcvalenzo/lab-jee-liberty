package com.eibs.playground.web;

import com.eibs.playground.repository.AccountRepository;
import com.eibs.playground.service.AccountService;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/accounts")
public class AccountServlet extends HttpServlet {
    @Resource(lookup = "java:comp/env/jdbc/eibsDataSource")
    private DataSource dataSource;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        showAccounts(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            service().registerAccount(
                    request.getParameter("accountNumber"),
                    request.getParameter("holderName"),
                    request.getParameter("balance"));
            response.sendRedirect(request.getContextPath() + "/accounts?created=true");
        } catch (IllegalArgumentException exception) {
            request.setAttribute("error", exception.getMessage());
            showAccounts(request, response);
        } catch (SQLException exception) {
            throw new ServletException("No fue posible registrar la cuenta.", exception);
        }
    }

    private void showAccounts(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("accounts", service().listAccounts());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/accounts.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException exception) {
            throw new ServletException("No fue posible consultar las cuentas.", exception);
        }
    }

    private AccountService service() {
        return new AccountService(new AccountRepository(dataSource));
    }
}
