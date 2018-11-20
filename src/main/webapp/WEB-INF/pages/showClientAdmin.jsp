<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>show client for admin</title>
	</head>
	<body>
	   
	   
	    <div align="center"> 
		    <h1>The client of a Bank</h1>
		     <a href="<c:url value="/logout" />">Logout</a>
		    <h2><a>${client.getData()}</a></h2>
		    <c:if test = "${error != null}">
				<div  style="color:red;font-style:italic" >${error.getCause()}</div>
			</c:if>
		    	<form id="delete" action="/Bank/admin/deleteAccount" method="post">
	 				<input name="idAccount" placeholder="number of account">
	                <input type="hidden" name="idClient" value="${client.getId()}">
	                <input type="submit" value="delete account" form="delete" style="width: 104px; ">  
                </form>
				<form id="show" action="/Bank/admin/showHistories" method="post">
               	<input  name="idAccount" placeholder="number of account">
                 <input type="hidden" name="idClient" value="${client.getId()}">
                 <input type="submit" value="show" form="show" style="width: 104px; ">  
               </form>

 
		    <table border="1">
		        <tr>
		      
		           <th >Number of Cards</th>
		           <th style="width: 154px; ">Sum</th>
		           <th style="width: 150px; ">Count of histories</th>
		        </tr>
			    <c:forEach var="account" items="${client.getAccounts()}">
				    <tr>
				           
                           <td>${account.getNumber()} </td>
                           <td>${account.getSum()}</td>
                           <td> <label >${account.getHistoriesSize()}</label><br>  </td>
                    
				     </tr>
		         </c:forEach>
		    </table>
		         
           <c:if test = "${currentAccount != null}">
                <p>Histories for:  <c:out value = "${currentAccount.getNumber()}"/><p>
                <table border="1" style="width: 768px; ">
	                <tr>
	                   
	                   <th style="width: 154px; ">date</th>
	                   <th style="width: 80px; ">operation</th>
	                   <th style="width: 68px; ">place</th>
	                   <th style="width: 67px; ">Sum</th>
	                </tr>
	                <c:forEach var="story" items="${currentAccount.getHistories()}" >
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