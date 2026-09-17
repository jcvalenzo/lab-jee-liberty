<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>EIBS Playground</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 2rem auto; max-width: 900px; }
        form, table { width: 100%; margin-top: 1rem; }
        input { margin: 0.25rem; padding: 0.4rem; }
        th, td { border: 1px solid #bbb; padding: 0.5rem; text-align: left; }
        .success { color: #176b2a; } .error { color: #a12622; }
    </style>
</head>
<body>
<h1>EIBS Playground</h1>
<p>Laboratorio Java EE 6 con Liberty, JSP, Servlets, JDBC y H2.</p>
<c:if test="${param.created == 'true'}"><p class="success">Cuenta registrada.</p></c:if>
<c:if test="${not empty error}"><p class="error"><c:out value="${error}" /></p></c:if>

<form action="${pageContext.request.contextPath}/accounts" method="post">
    <label>Numero de cuenta <input name="accountNumber" maxlength="20" required></label>
    <label>Titular <input name="holderName" maxlength="100" required></label>
    <label>Saldo <input name="balance" type="number" min="0" step="0.01" required></label>
    <button type="submit">Registrar cuenta</button>
</form>

<table>
    <thead><tr><th>ID</th><th>Cuenta</th><th>Titular</th><th>Saldo</th></tr></thead>
    <tbody>
    <c:forEach items="${accounts}" var="account">
        <tr><td><c:out value="${account.id}" /></td><td><c:out value="${account.accountNumber}" /></td><td><c:out value="${account.holderName}" /></td><td><c:out value="${account.balance}" /></td></tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
