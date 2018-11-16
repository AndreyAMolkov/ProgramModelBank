<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Banking</title>
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

<table>
  <tr>
    <th colspan="2">${client.getId()} <a href="/Bank/logout?id=${client}">log out</a></th>  
        
  </tr>
  <tr>
    <td style="width: 400px; ">Card</td>
    <td style="width: 172px; ">Sum</td>    
  </tr>  
  <tr>
   <c:forEach var="account" items="${client.getAccounts()}" varStatus="i">
                    <tr>
                           
                           <td style="width: 400px; ">${client.getNumber()}</td>
                       
                           <td>
                               <a href="/Bank/transfer?id=${account.getId() }">transfer</a>
                           </td>
                           
                           <td style="width: 172px; ">${account.getSum()}</td>
                       
                           <td>
                               <a href="/Bank/history?id=${account.getHistory() }">history</a>
                           </td>
                           
                     </tr>
    </c:forEach>
  </tr>  
</table>
</body>
</html>