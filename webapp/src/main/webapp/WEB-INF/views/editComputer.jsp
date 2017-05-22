<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%--
  Created by IntelliJ IDEA.
  User: ebiz
  Date: 20/03/17
  Time: 13:54
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
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="${pageContext.request.contextPath}/resources/css/dataTables.bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="${pageContext.request.contextPath}/resources/css/font-awesome.css" rel="stylesheet" media="screen">
    <link href="${pageContext.request.contextPath}/resources/css/main.css" rel="stylesheet" media="screen">
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
                <div class="label label-default pull-right">
                    id: ${computer.id}
                </div>
                <h1>Edit Computer</h1>

                <c:if test="${not empty errorMsg}">
                    <div class="alert alert-warning">
                        <strong>Error : </strong>${errorMsg}
                    </div>
                </c:if>

                <form:form id="computer_form" modelAttribute="computerDTO" action="${pageContext.request.contextPath}/edit" method="POST">
                    <fieldset>
                        <form:input path="id" type="hidden" name="id" id="id" value="${computer.id}"/>
                        <div class="form-group">
                            <label for="name">Computer name</label>
                            <form:input path="name" type="text" class="form-control" name="name" id="name" placeholder="Computer name" value="${computer.name}"/>
                            <form:errors class="alert alert-warning" path="name" />
                        </div>
                        <div class="form-group">
                            <label for="introduced">Introduced date</label>
                            <form:input path="introduced" type="date" class="form-control" name="introduced" id="introduced" placeholder="Introduced date" value="${computer.introduced}"/>
                            <form:errors class="alert alert-warning" path="introduced" />
                        </div>
                        <div class="form-group">
                            <label for="discontinued">Discontinued date</label>
                            <form:input path="discontinued" type="date" class="form-control" name="discontinued" id="discontinued" placeholder="Discontinued date" value="${computer.discontinued}"/>
                            <form:errors class="alert alert-warning" path="discontinued" />
                        </div>
                        <div class="form-group">
                            <label for="companyId">Company</label>
                            <form:select class="form-control" path="companyId">
                                <form:option label="--" value="0"/>
                                <form:options items="${companies}" itemValue="id" itemLabel="name" />
                            </form:select>
                        </div>
                    </fieldset>
                    <div class="actions pull-right">
                        <input type="submit" value="Edit" class="btn btn-primary">
                        or
                        <a href=<my_tags:link url="${pageContext.request.contextPath}/dashboard"/> class="btn btn-default">Cancel</a>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</section>
<jsp:include page="partials/scripts.jsp"></jsp:include>
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/form-validation.js"></script>
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
</body>
</html>