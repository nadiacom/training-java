<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my_tags" %>
<%--
  Created by IntelliJ IDEA.
  User: ebiz
  Date: 20/03/17
  Time: 13:54
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html>
<head>
    <title>Computer Database</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="${pageContext.request.contextPath}/resources/css/dataTables.bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="${pageContext.request.contextPath}/resources/css/font-awesome.css" rel="stylesheet" media="screen">
    <link href="${pageContext.request.contextPath}/resources/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
<header class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <a class="navbar-brand" href="/dashboard"> Application - Computer Database </a>
    </div>
</header>

<section id="main">
    <div class="container">
        <c:if test="${not empty message}">
            <div class="alert alert-success">
                ${message}
            </div>
        </c:if>
        <h1 id="homeTitle">
            <div id="nbComputers" style="display:inline-block;">${pageRequest.nbComputers}</div>
            Computer(s) found
        </h1>

        <div id="actions" class="form-horizontal">
            <div class="pull-left">
                <form id="searchForm" action="${pageContext.request.contextPath}/dashboard" method="GET"
                      class="form-inline">
                    <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name"/>
                    <input type="submit" id="searchsubmit" value="Filter by name"
                           class="btn btn-primary"/>
                </form>
            </div>
            <div class="pull-right">
                <a class="btn btn-success" id="addComputer1" href=<my_tags:link url="${pageContext.request.contextPath}/add"/>>Add
                    Computer</a>
                <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
            </div>
        </div>
    </div>

    <form id="deleteForm" action="${pageContext.request.contextPath}/dashboard" method="POST">
        <input type="hidden" name="selection" value="">
    </form>

    <div class="container" style="margin-top: 10px;">
        <table id="computers" class="display table table-striped table-bordered" cellspacing="0" width="100%">
            <thead>
            <tr>
                <!-- Variable declarations for passing labels as parameters -->
                <!-- Table header for Computer Name -->

                <th class="editMode" style="width: 60px; height: 22px;">
                    <input type="checkbox" id="selectall"/>
                    <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                </th>
                <th><my_tags:sort_link currentPage="${pageRequest.currentPage}" search="${pageRequest.search}" click="name" id="computerName"
                                       order="name" url="${pageContext.request.contextPath}/dashboard"/>
                    Computer name
                </th>
                <th><my_tags:sort_link currentPage="${pageRequest.currentPage}" search="${pageRequest.search}" click="introduced"
                                       id="computerIntroduced" order="introduced"
                                       url="${pageContext.request.contextPath}/dashboard"/>
                    Introduced date
                </th>
                <!-- Table header for Discontinued Date -->
                <th><my_tags:sort_link currentPage="${pageRequest.currentPage}" search="${pageRequest.search}" click="discontinued"
                                       id="computerDiscontinued" order="discontinued"
                                       url="${pageContext.request.contextPath}/dashboard"/>
                    Discontinued date
                </th>
                <!-- Table header for Company -->
                <th><my_tags:sort_link currentPage="${pageRequest.currentPage}" search="${pageRequest.search}" click="company" id="companyName"
                                       order="company" url="${pageContext.request.contextPath}/dashboard"/>
                    Company
                </th>

            </tr>
            </thead>
            <!-- Browse attribute computers -->
            <tbody id="results">
            <c:forEach var="computer" items="${pageRequest.listComputers}">
                <tr>
                    <td class="editMode">
                        <input type="checkbox" class="cb" value="${computer.id}">
                    </td>
                    <td>
                        <a class="edit" href=<my_tags:link url="${pageContext.request.contextPath}/edit" id="${computer.id}"/>>${computer.name}</a>
                    </td>
                    <td>${computer.introduced}</td>
                    <td>${computer.discontinued}</td>
                    <td>${computer.companyName}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </div>
</section>

<footer class="navbar-fixed-bottom">
    <div class="container text-center">

        <my_tags:paginator currentPage="${pageRequest.currentPage}" pgEnd="${pageRequest.pgEnd}" lastPage="${pageRequest.lastPage}" pgStart="${pageRequest.pgStart}"
                           search="${pageRequest.search}" order="${pageRequest.order}" url="${pageContext.request.contextPath}/dashboard"/>

    </div>
</footer>
<script>
    "use strict";
    function filterComputer() {
        console.log("search input triggered");
        // Declare variables
        var input, filter, tbody, tr, td, a, i;
        input = document.getElementById('searchbox');
        filter = input.value.toUpperCase();
        tbody = document.getElementById("results");
        tr = tbody.getElementsByTagName('tr');
        // Loop through all list items, and hide those who don't match the search query
        for (i = 0; i < tr.length; i++) {
            a = tr[i].getElementsByTagName("a")[0];
            console.log(filter);
            if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
</script>
<jsp:include page="partials/scripts.jsp"></jsp:include>
<script src="${pageContext.request.contextPath}/resources/js/dashboard.js"></script>
</body>

</html>
