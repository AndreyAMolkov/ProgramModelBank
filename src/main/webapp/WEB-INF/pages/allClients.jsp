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
    <h2><a href="/Bank/admin/newClient">New Client</a></h2>
	<table>
	  <tr>
	    <th colspan="7">${login.getLogin()}</th>      
	  </tr>
	  <tr>
	    <td style="width: 34px; ">Id client</td>
	    <td style="width: 89px; ">Data</td> 
	    <td style="width: 60px; ">Login</td>
	    <td style="width: 84px; ">Password</td> 
	    <td style="width: 102px; ">number of accounts</td>
	    <td style="width: 77px; ">Count Histories</td>
	    <td style="width: 68px; ">Delete</td>
	  </tr>  
	    <c:forEach var="client" items="${clients}" varStatus="i">
	        <tr>
	           <td style="width: 34px; ">
	               ${client.getId()}</td>
	               
	           <td>
	               ${client.getData()}
	           </td>
	           <td>
	               ${client.getLogin().getLogin()} 
	           </td>
	           <td>
	               ${client.getLogin().getPassword()}
	           </td>
	                           
	           <td>
	           <form id="add", action="/Bank/admin/addAccount" method="post" style="width: 51px; height: 44px; ">
                    <input type="hidden" name="id" value="${client.getId()}">
                     <p><input type="submit" value="add" form="add"></p> 
               </form> 
						<ul>
						 <c:forEach var="account" items="${client.getAccounts()}" >
						 
						    <li>${account.getNumber()} </li>
						    
						  </c:forEach>  
						</ul>
				           </td>
				            <td>
				             <form id="show" action="/Bank/admin/showClient" method="post" style="width: 51px; height: 44px; ">
			                                    
			                                     <p><input type="submit" value="show" form="show" id="show"></p> 
			                               </form> 
				                    <ul>
				                     <c:forEach var="account" items="${client.getAccounts()}" >
			
				                        <li> ${account.getHistoriesSize()} </li>
				                        
				                      </c:forEach>  
				                    </ul>
				           </td> 
	           <td>
                  
	              <a href="/Bank/admin/deleteClient?id=${client.getId() }">Delete</a>
	               <form  id="show" action="/Bank/admin/showClient" method="post" style="width: 51px; height: 44px; ">
                         <input type="hidden" name="id" value="${client.getId() }">                       
                        <input type="submit" value="show" form="show" id="show"> 
                   </form> 
	           </td>
	        </tr>
	      </c:forEach>
	</table>
</div>
</body>
</html>