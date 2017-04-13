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
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="${pageContext.request.contextPath}/css/dataTables.bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet" media="screen">
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
<header class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <a class="navbar-brand" href="/dashboard"> Application - Computer Database </a>
    </div>
</header>

<section id="main">
    <div class="container">
        <c:if test="${not empty errorMsg}">
            <div class="alert alert-warning">
                <strong>Error : </strong>${errorMsg}
            </div>
        </c:if>
        <h1 id="homeTitle">
            <div id="nbComputers" style="display:inline-block;">${nbComputer}</div>
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
                <a class="btn btn-success" id="addComputer" href="${pageContext.request.contextPath}/add">Add
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
                <th><my_tags:sort_link currentPage="${currentPage}" search="${search}" click="name" id="computerName"
                                       order="c.name" url="${pageContext.request.contextPath}/dashboard"/>
                    Computer name
                </th>
                <th><my_tags:sort_link currentPage="${currentPage}" search="${search}" click="introduced"
                                       id="computerIntroduced" order="c.introduced"
                                       url="${pageContext.request.contextPath}/dashboard"/>
                    Introduced date
                </th>
                <!-- Table header for Discontinued Date -->
                <th><my_tags:sort_link currentPage="${currentPage}" search="${search}" click="discontinued"
                                       id="computerDiscontinued" order="c.discontinued"
                                       url="${pageContext.request.contextPath}/dashboard"/>
                    Discontinued date
                </th>
                <!-- Table header for Company -->
                <th><my_tags:sort_link currentPage="${currentPage}" search="${search}" click="company" id="companyName"
                                       order="company.name" url="${pageContext.request.contextPath}/dashboard"/>
                    Company
                </th>

            </tr>
            </thead>
            <!-- Browse attribute computers -->
            <tbody id="results">
            <c:forEach var="computer" items="${listComputer}">
                <tr>
                    <td class="editMode">
                        <input type="checkbox" name="cb" class="cb" value="10"/>
                    </td>
                    <td>
                        <a name="edit" href="edit?id=10">test</a>
                    </td>
                    <td>teest</td>
                    <td>tesst</td>
                    <td>tesst</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </div>
</section>

<footer class="navbar-fixed-bottom">
    <div class="container text-center">

        <my_tags:paginator currentPage="${currentPage}" pgEnd="${pgEnd}" lastPage="${lastPage}" pgStart="${pgStart}"
                           search="${search}" order="${order}" url="${pageContext.request.contextPath}/dashboard"/>

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
<jsp:include page="${pageContext.request.contextPath}/views/partials/scripts.jsp"></jsp:include>
<script src="${pageContext.request.contextPath}/js/dashboard.js"></script>
</body>

</html>
