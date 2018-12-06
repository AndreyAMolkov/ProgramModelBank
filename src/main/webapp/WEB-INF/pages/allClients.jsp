<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta charset="UTF-8">
<title>All clients for admin</title>
<style>
table {
	border-collapse: collapse;
	width: 100%;
}

th, td {
	border: 1px solid black;
	padding: 5px;
}
</style>
</head>
<body>
	<div align="center">
		<h1>Clients of a Bank</h1>
		<a href="<c:url value="/logout?logout=true" />">Logout</a>
		<h2>
			<a href="/Bank/admin/newClient">New Client</a>
		</h2>
		<c:if test="${error != null}">
			<div style="color: red; font-style: italic">${error.getCause()}</div>
		</c:if>
		<form id="show" action="/Bank/admin/showClient" method="post">
			<input name="id" placeholder="id of client" pattern="[0-9]{0,18}"
				required title="number from 0 and less  999999999999999999">

			<input type="submit" value="show" form="show" id="show"
				style="width: 85px;">
		</form>
		<form id="edit" action="/Bank/admin/populateEdit" method="post">
			<input name="id" placeholder="id of client" pattern="[0-9]{0,18}"
				required title="number from 0 and less  999999999999999999">

			<input type="submit" value="edit" form="edit" id="edit"
				style="width: 85px;">
		</form>


		<form id="delete" action="/Bank/admin/deleteClient" method="post">
			<input name="id" placeholder="id of client" pattern="[0-9]{0,18}"
				required title="number from 0 and less  999999999999999999">

			<input type="submit" value="delete" form="delete" id="delete"
				style="width: 85px;">
		</form>
		<form id="addAccount" action="/Bank/admin/addAccount" method="post">
			<input name="idClient" placeholder="id of client"
				pattern="[0-9]{0,18}" required
				title="number from 0 and less  999999999999999999"> <input
				type="submit" value="addAccount" style="width: 85px;">
		</form>
		<table border="1" style="width: 768px;">

			<tr>
				<td style="width: 30px;">Id client</td>
				<td style="width: 40px;">Data</td>
				<td style="width: 40px;">Credential</td>
				<td style="width: 40px;">Role</td>

				<td style="width: 40px;">Number of accounts</td>
				<td style="width: 30px;">Count Histories</td>
			</tr>
			<c:forEach var="client" items="${clients}" varStatus="i">
				<tr>
					<td style="width: 30px;">${client.getId()}</td>

					<td style="width: 40px;">${client.getData()}</td>
					<td style="width: 40px;">${client.getCredential().getLogin()}</td>
					<td style="width: 40px;">${client.getCredential().getRole()}</td>

					<td style="width: 40px;">
						<ul>
							<c:forEach var="account" items="${client.getAccounts()}">

								<li>${account.getNumber()}</li>

							</c:forEach>
						</ul>
					</td>
					<td style="width: 30px;">

						<ul>
							<c:forEach var="account" items="${client.getAccounts()}">

								<li>${account.getHistoriesSize()}</li>

							</c:forEach>
						</ul>
					</td>

				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>