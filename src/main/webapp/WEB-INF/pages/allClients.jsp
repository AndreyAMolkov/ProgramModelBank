<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
     <a href="<c:url value="/logout" />">Logout</a>
    <h2><a href="/Bank/admin/newClient">New Client</a></h2>
    	<form  id="show" action="/Bank/admin/showClient" method="post">  
          <input name="id" >                       
          <input type="submit" value="show" form="show" id="show" style="width: 85px; "> 
        </form>
	      <form  id="edit" action="/Bank/admin/populateEdit" method="post">  
          <input name="id" placeholder="id of client">       
          <input type="submit" value="edit" form="edit" id="edit" style="width: 85px; "> 
        </form>
	     
	     
	     <form  id="delete" action="/Bank/admin/deleteClient" method="post">  
          <input name="id" >                       
          <input type="submit" value="delete" form="delete" id="delete" style="width: 85px; "> 
        </form>  
        <form id="addAccount" action="/Bank/admin/addAccount" method="post">
          <input name="idClient" placeholder="input client's id" >
          <input type="submit" value="addAccount" style="width: 85px; ">  
        </form>    
	<table>
	  <tr>
	    <th colspan="7">${login.getLogin()}</th>      
	  </tr>
	  <tr>
	    <td style="width: 34px; ">Id client</td>
	    <td style="width: 89px; ">Data</td> 
	    <td style="width: 60px; ">Login</td>
	   <td style="width: 60px; ">Role</td>
	    
	    <td style="width: 84px; ">Password</td> 
	    <td style="width: 102px; ">number of accounts</td>
	    <td style="width: 77px; ">Count Histories</td>
	  </tr>  
	    <c:forEach var="client" items="${clients}" varStatus="i">
	        <tr>
	           <td style="width: 34px; ">
	               ${client.getId()}</td>
	               
	           <td>
	               ${client.getData()}
	           </td>
	           <td> ${client.getLogin().getLogin()}  </td>
	            <td> ${client.getLogin().getRole()}  </td>
	           
	           <td>${client.getLogin().getPassword()}</td>
	                           
	           <td>
	         		<ul>
						 <c:forEach var="account" items="${client.getAccounts()}" >
						 
						    <li>${account.getNumber()} </li>
						    
						  </c:forEach>  
						</ul>
				           </td>
				            <td>
				           
				                    <ul>
				                     <c:forEach var="account" items="${client.getAccounts()}" >
			
				                        <li> ${account.getHistoriesSize()} </li>
				                        
				                      </c:forEach>  
				                    </ul>
				           </td> 
	           
	        </tr>
	      </c:forEach>
	</table>
</div>
</body>
</html>