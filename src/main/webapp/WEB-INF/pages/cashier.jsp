<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<a href="<c:url value="/logout?logout=true" />">Logout</a>
	<c:if test="${error != null}">
		<div style="color: red; font-style: italic">${error.getCause()}</div>
	</c:if>
	<c:if test="${dataTransfer == null}">
		<form id="check" action="/Bank/cashier/AccountCheckAddSum"
			method="post">
			<input name="idAccountTo" placeholder="number of account"
				pattern="[0-9]{0,18}" required
				title="number from 0 and less  999999999999999999"> <input
				name="amount" placeholder="amount" pattern="[0-9]{0,6}" required
				title="number from 0 and less  999999"> <input type="hidden"
				name="denied" value="false"> <input type="submit"
				value="check" form="check" style="width: 90px;">
		</form>
	</c:if>

	<c:if test="${dataTransfer != null}">
		<form id="add" action="/Bank/cashier/AccountAddSum" method="post">
			<input name="idAccountToCheck" value="${dataTransfer.getIdAccountTo()}" readonly>
			<input name="amountCheck"
				value="${dataTransfer.getAmountClientAccountTo()}" readonly> <input
				name="nameCheck" value="${dataTransfer.getNameOfClientTo()}" readonly> 
				<input type="hidden" name="idAccountTo" value="${dataTransfer.getIdAccountTo()}">
				<input type="hidden" name="amount" value="${dataTransfer.getAmountClientAccountTo()}">
				<input type="hidden" name="name" value="${dataTransfer.getNameOfClientTo()}">
				<input type="hidden" name="denied" value="${dataTransfer.getDenied()}">
			<input type="submit" value="add amount" form="add"
				style="width: 90px;">
		</form>
		<form id="denied" action="/Bank/"
			method="get">


			<input type="hidden" name="denied" value="true"> <input
				type="submit" value="denied" form="denied" style="width: 90px;">
		</form>

	</c:if>


</body>
</html>