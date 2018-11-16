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
		    <h2><a>${client.getData()}</a></h2>
		                     <form id="transfer" action="/Bank/client/transfer" method="post">
                            <input type="hidden" name="idClient" value="${client.getId()}">
                           <p><input type="submit" value="transfer" form="transfer"></p>  
                           </form> 
		    <table border="1">
		        <tr>
		      
		           <th >Number of Cards</th>
		           <th style="width: 154px; ">Sum</th>
		           <th style="width: 150px; ">Count of histories</th>
		        </tr>
			    <c:forEach var="account" items="${client.getAccounts()}">
				    <tr>
				           
                           <td>
                            
                            ${account.getNumber()}
                           </td>
                           <td>${account.getSum()}</td>
                           <td>
                          
                          
                           <label >${account.getHistoriesSize()}</label><br>
                        
                           <form id="showHistory" action="/Bank/admin/showClient" method="post">
                            <input type="hidden" name="idAccount" value="${account.getNumber()}" >
                            <input type="hidden" name="id" value="${client.getId()}">
                           <p><input type="submit" value="show" form="showHistory"></p>  
                           </form>					   

                               
                           </td>
                          
                           
	                   
				     </tr>
		         </c:forEach>
		    </table>
		    <c:if test = "${sendMoneyForm != null}">

			<div th:if="${errorMessage!=null}"
			           style="color:red;font-style:italic" th:utext="${errorMessage}">..</div>

			     <form:form action="/Bank/client/sendMoney"  method="post" modelAttribute="sendMoneyForm" >
			         <table>
			 
			           <tr>
			              <td>From Bank Account Id</td>
			              <td><form:input path="fromAccountId" value="${sendMoneyForm.getFromAccountId()}" /></td>
			           </tr>
			           <tr>
			              <td>To Bank Account Id</td>
			              <td><form:input path="toAccountId" value="${sendMoneyForm.getToAccountId()}"/></td>
			           </tr>
			            <tr>
			              <td>Amount</td>
			              <td><form:input path="amount" value="${sendMoneyForm.getAmount()}" /></td>
			           </tr>          
			           <tr>
			              <td>&nbsp;</td>
			               <input type="hidden" name="id" value="${client.getId()}" >
			              <td><input type="submit" value="Send"/></td>
			           </tr>      
			         </table>      
			      </form:form>
			</c:if>      
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