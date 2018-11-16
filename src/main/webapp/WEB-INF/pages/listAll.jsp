<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>List</title>
	</head>
	<body>
	    <div align="center"> 
		    <h1>Usser List</h1>
		    <h2><a href="/Bank/new">New User</a></h2>
		    
		    <table border="1">
		        <tr>
		           <th>Id</th>
		           <th>Data of client</th>
		           <th>Login - Password</th>
		           <th>Accounts</th>
		        </tr>
			    <c:forEach var="client" items="${clients}" varStatus="i">
				    <tr>
				           <td>${client.getId()}</td>
                           <td>${client.getData()}</td>
                           <td>${client.getLogin()}</td>
                           <td>${client.getAccounts()}</td>
	                   
                           <td>
				               <a href="/Bank/edit?id=${client}">Edit</a>
				               &nbsp;&nbsp;&nbsp;&nbsp;
				               <a href="/Bank/delete?id=${client.getId() }">Delete</a>
				           </td>
				     </tr>
		         </c:forEach>
		    </table>
	    </div>
	</body>
</html>