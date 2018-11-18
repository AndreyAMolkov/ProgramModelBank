<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>New or edit Users</title>
</head>
<body>
    <div align="center">
        <h1>New/Edit User</h1>
            <c:if test = "${error != null}">
			
			<div  style="color:red;font-style:italic" >${error.getCause()}</div>

			</c:if>
        
        <table>
            <form:form action="/Bank/admin/edit" method="POST" modelAttribute="client" >
                <form:hidden path="id"/>
                <tr>
                    <td>login</td>
                    <td><form:input path="login.login" value="${client.getLogin().getLogin()}"/></td>
                </tr>
                <tr>    
                    <td>password</td>
                    <td><form:input path="login.password" value=" "/></td>
                 </tr>
                  <tr>    
                    <td>role</td>
                    <td><form:input path="login.role" value="${client.getLogin().getRole()}"/></td>
                 </tr>
                 <tr> 
                 <tr>
                    <td>FirstName</td>
                    <td><form:input path="data.firstName" value="${client.getData().getFirstName()}"/></td>
                </tr>
                <tr>    
                    <td>Second name</td>
                    <td><form:input path="data.secondName" value="${client.getData().getSecondName()}"/></td>
                 </tr>
                  <tr>    
                    <td>Last Name</td>
                    <td><form:input path="data.lastName" value="${client.getData().getLastName()}"/></td>
                 </tr>
                 <tr>     
                    <td colspan="2" align="center">
                    <input type="submit" value="Save Changes">
                    </td>
                 </tr> 
            </form:form>
        </table>
    </div>
</body>
</html>