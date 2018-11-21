<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<meta charset="ISO-8859-1">
<title>show client for admin</title>
</head>
<body>


	<div align="center">

		<h1>The client of a Bank</h1>
		<h2>
			<a>${client.getData()}</a>
		</h2>
		<table>
			<tr>
				<th><a href="<c:url value="/home" />">Home</a></th>
				<th><a href="<c:url value="/logout" />">Logout</a></th>
			</tr>
		</table>

		<c:if test="${error != null}">
			<div style="color: red; font-style: italic">${error.getCause()}</div>
		</c:if>

		<form id="show" action="/Bank/client/showHistories" method="post">
			<input name="idAccount" placeholder="number of account"
				pattern="[0-9]{0,18}" required
				title="number from 0 and less  9223372036854775807"> <input
				type="hidden" name="idClient" value="${client.getId()}"> <input
				type="submit" value="show" form="show">
		</form>

		<form id="transfer" action="/Bank/client/transfer" method="post">
			<input type="hidden" name="idClient" value="${client.getId()}">
			<p>
				<input type="submit" value="transfer" form="transfer">
			</p>
		</form>

		<table border="1">
			<tr>

				<th>Number of Cards</th>
				<th style="width: 154px;">Sum</th>
				<th style="width: 150px;">Count of histories</th>
			</tr>
			<c:forEach var="account" items="${client.getAccounts()}">
				<tr>

					<td>${account.getNumber()}</td>
					<td>${account.getSum()}</td>
					<td><label>${account.getHistoriesSize()}</label><br></td>

				</tr>
			</c:forEach>
		</table>
		<c:if test="${sendMoneyForm != null}">

			<form:form action="/Bank/client/sendMoney" method="post"
				modelAttribute="sendMoneyForm">
				<table>

					<tr>
						<td>From Bank Account Id</td>
						<td><input name="fromAccountId"
							value="${sendMoneyForm.getFromAccountId()}" pattern="[0-9]{0,18}"
							required title="number from 0 and less  999999999999999999" /></td>
					</tr>
					<tr>
						<td>To Bank Account Id</td>
						<td><input name="toAccountId"
							value="${sendMoneyForm.getToAccountId()}" pattern="[0-9]{0,18}"
							required title="number from 0 and less  999999999999999999" /></td>
					</tr>
					<tr>
						<td>Amount</td>
						<td><input name="amount" value="${sendMoneyForm.getAmount()}"
							pattern="[0-9]{0,6}" required
							title="number from 0 and less  999999" /></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td><input type="hidden" name="id" value="${client.getId()}"></td>
						<td><input type="submit" value="Send" /></td>
					</tr>
				</table>
			</form:form>
		</c:if>
		<c:if test="${currentAccount != null}">
			<p>
				Histories for:
				<c:out value="${currentAccount.getNumber()}" />
			<p>
			<table border="1" style="width: 768px;">
				<tr>

					<th style="width: 154px;">date</th>
					<th style="width: 80px;">operation</th>
					<th style="width: 68px;">place</th>
					<th style="width: 67px;">Sum</th>
				</tr>
				<c:forEach var="story" items="${currentAccount.getHistories()}">
					<tr>

						<td>${story.getDate()}</td>
						<td>${story.getOperation()}</td>
						<td>${story.getPlace()}</td>
						<td>${story.getSum()}</td>

					</tr>
				</c:forEach>
			</table>
		</c:if>
	</div>
</body>
</html>