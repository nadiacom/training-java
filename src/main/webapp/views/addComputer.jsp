<%@ taglib prefix="for" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ebiz
  Date: 21/03/17
  Time: 15:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Computer Database</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="../css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="../css/font-awesome.css" rel="stylesheet" media="screen">
    <link href="../css/jquery-ui.min.css" rel="stylesheet" media="screen">
    <link href="../css/main.css" rel="stylesheet" media="screen">
</head>
<body>
<header class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <a class="navbar-brand" href="/dashboard"> Application - Computer Database </a>
    </div>
</header>

<section id="main">
    <div class="container">
        <div class="row">
            <div class="col-xs-8 col-xs-offset-2 box">
                <h1>Add Computer</h1>
                <form action="/add" method="POST">
                    <fieldset>
                        <div class="form-group">
                            <label for="computerName">Computer name</label>
                            <input type="text" class="form-control" name="name" id="computerName" placeholder="Computer name">
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
                                <c:forEach var="company" items="${companies}">
                                    <option value="${company.id}"  ${company.id == computer.company.id ? 'selected="selected"' : ''}>${company.id} - ${company.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </fieldset>
                    <div class="actions pull-right">
                        <input type="submit" value="Add" class="btn btn-primary">
                        or
                        <a href="/dashboard" class="btn btn-default">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>
<script>
    window.onload = function() {
        $('#introduced').datepicker({
            dateFormat: 'yy-mm-dd',
            altField: '#thealtdate',
            altFormat: 'yy-mm-dd'
        });
        $('#discontinued').datepicker({
            dateFormat: 'yy-mm-dd',
            altField: '#thealtdate',
            altFormat: 'yy-mm-dd'
        });
    }
</script>
<jsp:include page="partials/scripts.jsp"></jsp:include>
</body>
</html>
