<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
  <title>Issue Stock</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h1>Issue Stock to Cleaner</h1>

<c:if test="${not empty errorMessage}">
  <p style="color:red;"><c:out value="${errorMessage}"/></p>
</c:if>
<c:if test="${not empty successMessage}">
  <p style="color:green;"><c:out value="${successMessage}"/></p>
</c:if>

<form method="post" action="issueStock">
  <label for="materialId">Material:</label>
  <select name="materialId" id="materialId" required>
    <option value="">-- select material --</option>
    <c:forEach var="m" items="${materials}">
      <option value="${m.materialId}">
        <c:out value="${m.name}"/> (available: ${m.quantity} ${m.unit})
      </option>
    </c:forEach>
  </select>
  <br/><br/>

  <label for="cleanerId">Cleaner:</label>
  <select name="cleanerId" id="cleanerId" required>
    <option value="">-- select cleaner --</option>
    <c:forEach var="c" items="${cleaners}">
      <option value="${c.cleanerId}">
        <c:out value="${c.firstName} ${c.lastName}"/>
      </option>
    </c:forEach>
  </select>
  <br/><br/>

  <label for="quantity">Quantity:</label>
  <input type="number" name="quantity" id="quantity" min="1" required/>
  <br/><br/>

  <button type="submit">Issue Stock</button>
</form>

<h2>Issuance History</h2>
<table border="1" cellpadding="6" cellspacing="0">
  <tr>
    <th>Date</th>
    <th>Material</th>
    <th>Cleaner</th>
    <th>Quantity</th>
    <th>Issued By</th>
  </tr>
  <c:forEach var="h" items="${history}">
    <tr>
      <td><c:out value="${h.issuedDate}"/></td>
      <td><c:out value="${h.materialName}"/></td>
      <td><c:out value="${h.cleanerName}"/></td>
      <td><c:out value="${h.quantityIssued}"/></td>
      <td><c:out value="${h.issuedBy}"/></td>
    </tr>
  </c:forEach>
</table>

<p><a href="reports">View Reports</a></p>
</body>
</html>

