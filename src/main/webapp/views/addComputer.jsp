<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ebiz
  Date: 21/03/17
  Time: 15:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my_tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Computer Database</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet" media="screen">
    <link href="${pageContext.request.contextPath}/css/jquery-ui.min.css" rel="stylesheet" media="screen">
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
<header class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <a class="navbar-brand" href=<my_tags:link url="${pageContext.request.contextPath}/dashboard"/>> Application - Computer Database </a>
    </div>
</header>

<section id="main">
    <div class="container">
        <div class="row">
            <div class="col-xs-8 col-xs-offset-2 box">
                <h1>Add Computer</h1>
                <c:if test="${not empty errorMsg}">
                        <div class="alert alert-warning">
                        <strong>Error : </strong>${errorMsg}
                    </div>
                </c:if>
                <form id="computer_form" action="${pageContext.request.contextPath}/add" method="POST">
                    <fieldset>
                        <div class="form-group">
                            <label for="name">Computer name</label>
                            <input type="text" class="form-control" name="name" id="name" placeholder="Computer name">
                        </div>
                        <div class="form-group">
                            <label for="introduced">Introduced date</label>
                            <input type="date" class="form-control" name="introduced" id="introduced" placeholder="Introduced date">
                        </div>
                        <div class="form-group">
                            <label for="discontinued">Discontinued date</label>
                            <input type="date" class="form-control" name="discontinued" id="discontinued" placeholder="Discontinued date">
                        </div>
                        <div class="form-group">
                            <label for="companyId">Company</label>
                            <select class="form-control" name="companyId" id="companyId">
                                <option value="null">- None -</option>
                                <c:forEach var="company" items="${companies}">
                                    <option value="${company.id}">${company.id} - ${company.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </fieldset>
                    <div class="actions pull-right">
                        <input type="submit" value="Add" class="btn btn-primary">
                        or
                        <a href=<my_tags:link url="${pageContext.request.contextPath}/dashboard"/> class="btn btn-default">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>
<jsp:include page="partials/scripts.jsp"></jsp:include>
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
<!-- <script src="${pageContext.request.contextPath}/js/form-validation.js"></script> -->
<script>
    $(function() {
        $('#introduced').datepicker({
            dateFormat: 'yy-mm-dd',
            altField: '#thealtdate',
            altFormat: 'yy-mm-dd',
        });
        $('#discontinued').datepicker({
            dateFormat: 'yy-mm-dd',
            altField: '#thealtdate',
            altFormat: 'yy-mm-dd'
        });
    });
</script>

</body>
</html>
