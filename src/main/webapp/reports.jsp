<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
  <title>Reports</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h1>Inventory Reports</h1>

<c:if test="${not empty errorMessage}">
  <p style="color:red;"><c:out value="${errorMessage}"/></p>
</c:if>

<h2>Inventory Report</h2>
<table border="1" cellpadding="6" cellspacing="0">
  <tr>
    <th>Material</th>
    <th>Quantity</th>
    <th>Reorder Level</th>
    <th>Unit</th>
  </tr>
  <c:forEach var="m" items="${inventoryReport}">
    <tr>
      <td><c:out value="${m.name}"/></td>
      <td><c:out value="${m.quantity}"/></td>
      <td><c:out value="${m.reorderLevel}"/></td>
      <td><c:out value="${m.unit}"/></td>
    </tr>
  </c:forEach>
</table>

<h2>Low-Stock Report</h2>
<table border="1" cellpadding="6" cellspacing="0">
  <tr>
    <th>Material</th>
    <th>Quantity</th>
    <th>Reorder Level</th>
    <th>Unit</th>
  </tr>
  <c:forEach var="m" items="${lowStockReport}">
    <tr>
      <td><c:out value="${m.name}"/></td>
      <td><c:out value="${m.quantity}"/></td>
      <td><c:out value="${m.reorderLevel}"/></td>
      <td><c:out value="${m.unit}"/></td>
    </tr>
  </c:forEach>
</table>

<h2>Material Usage Report</h2>
<table border="1" cellpadding="6" cellspacing="0">
  <tr>
    <th>Material</th>
    <th>Total Issued</th>
  </tr>
  <c:forEach var="row" items="${usageReport}">
    <tr>
      <td><c:out value="${row[0]}"/></td>
      <td><c:out value="${row[1]}"/></td>
    </tr>
  </c:forEach>
</table>

<h2>Issuance History</h2>
<table border="1" cellpadding="6" cellspacing="0">
  <tr>
    <th>Date</th>
    <th>Material</th>
    <th>Cleaner</th>
    <th>Quantity</th>
    <th>Issued By</th>
  </tr>
  <c:forEach var="h" items="${issuanceHistory}">
    <tr>
      <td><c:out value="${h.issuedDate}"/></td>
      <td><c:out value="${h.materialName}"/></td>
      <td><c:out value="${h.cleanerName}"/></td>
      <td><c:out value="${h.quantityIssued}"/></td>
      <td><c:out value="${h.issuedBy}"/></td>
    </tr>
  </c:forEach>
</table>

<p><a href="issueStock">Back to Issue Stock</a></p>
</body>
</html>
