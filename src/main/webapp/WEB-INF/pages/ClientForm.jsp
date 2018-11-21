<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>New or edit Users</title>
</head>
<body>

	<div align="center">
		<h1>New/Edit User</h1>
		<table>
			<tr>
				<th><a href="<c:url value="/home" />">Home</a></th>
				<th><a href="<c:url value="/logout" />">Logout</a></th>
			</tr>
		</table>
		<c:if test="${error != null}">

			<div style="color: red; font-style: italic">${error.getCause()}</div>

		</c:if>

		<table>
			<form:form action="/Bank/admin/edit" method="POST"
				modelAttribute="client">
				<form:hidden path="id" />
				<form:hidden path="login.id" />
				<form:hidden path="data.id" />
				<tr>
					<td>login</td>
					<td><input name="login.login"
						value="${client.getLogin().getLogin()}" pattern="[a-z]{0,9}"
						required title="all small letters, no more than 9" /></td>
				</tr>
				<tr>
					<td>password</td>
					<td><input name="login.password" value=""
						pattern="[a-zA-Z0-9]{0,10}" required
						title="all small or capital letters or number, no more than 10" /></td>
				</tr>
				<tr>
					<td>role</td>
					<td><input name="login.role"
						value="${client.getLogin().getRole()}" pattern="[A-Z]{0,9}"
						required title="all capital letters, no more than 9" /></td>
				</tr>
				<tr>
				<tr>
					<td>FirstName</td>
					<td><input name="data.firstName"
						value="${client.getData().getFirstName()}"
						pattern="[A-Z][a-z]{0,20}" required
						title="the first letter is capitalized, and the others are small, but not more than 20" /></td>
				</tr>
				<tr>
					<td>Second name</td>
					<td><input name="data.secondName"
						value="${client.getData().getSecondName()}"
						pattern="[A-Z][a-z]{0,20}" required
						title="the first letter is capitalized, and the others are small, but not more than 20" /></td>
				</tr>
				<tr>
					<td>Last Name</td>
					<td><input name="data.lastName"
						value="${client.getData().getLastName()}"
						pattern="[A-Z][a-z]{0,20}" required
						title="the first letter is capitalized, and the others are small, but not more than 20" /></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit"
						value="Save Changes"></td>
				</tr>
			</form:form>
		</table>
	</div>
</body>
</html>