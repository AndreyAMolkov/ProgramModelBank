<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
 <a href="<c:url value="/logout" />">Logout</a>
   <c:if test = "${error != null}">
                <div  style="color:red;font-style:italic" >${error.getCause()}</div>
   </c:if>
    <c:if test = "${dataTransfer == null}">
   <form id="check" action="/Bank/cashier/AccountCheckAddSum" method="post">
      <input  name="idAccountTo" placeholder="number of account" >
      <input  name="amount" placeholder="amount">
       <input type="hidden" name="denied" value="false">
      <input type="submit" value="check" form="check" style="width: 90px; ">  
    </form>
    </c:if> 
    
    <c:if test = "${dataTransfer != null}">
   <form id="add" action="/Bank/cashier/AccountAddSum" method="post">
      <input  name="idAccountTo"  value="${dataTransfer.getIdAccountTo()}" >
      <input  name="amount"  value="${dataTransfer.getAmountClientAccountTo()}">
      <input name="name" value="${dataTransfer.getNameOfClientTo()}">
      <input type="hidden" name="denied" value="${dataTransfer.getDenied()}">
      <input type="submit" value="add amount" form="add" style="width: 90px; "></form>
     <form id="denied" action="/Bank/cashier/AccountCheckAddSum" method="post">

      
      <input type="hidden" name="denied" value="true"> <input type="submit" value="denied" form="denied" style="width: 90px; ">  
    </form>  
    
    </c:if>
    
   
</body>
</html>