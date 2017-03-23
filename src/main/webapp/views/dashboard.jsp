<%@ taglib prefix="for" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag" %>
<%--
  Created by IntelliJ IDEA.
  User: ebiz
  Date: 20/03/17
  Time: 13:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Computer Database</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <!-- Bootstrap -->
    <link href="../css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="../css/dataTables.bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="../css/font-awesome.css" rel="stylesheet" media="screen">
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
        <h1 id="homeTitle">
            ${nbComputer} Computer(s) found
        </h1>
        <div id="actions" class="form-horizontal">
            <div class="pull-left">
                <form id="searchForm" action="#" method="GET" class="form-inline">

                    <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name"
                           onkeyup="filterComputer()"/>
                    <input type="submit" id="searchsubmit" value="Filter by name"
                           class="btn btn-primary"/>
                </form>
            </div>
            <div class="pull-right">
                <a class="btn btn-success" id="addComputer" href="/add">Add Computer</a>
                <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
            </div>
        </div>
    </div>

    <form id="deleteForm" action="/dashboard" method="POST">
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
                <th>
                    Computer name
                </th>
                <th>
                    Introduced date
                </th>
                <!-- Table header for Discontinued Date -->
                <th>
                    Discontinued date
                </th>
                <!-- Table header for Company -->
                <th>
                    Company
                </th>

            </tr>
            </thead>
            <!-- Browse attribute computers -->
            <tbody id="results">
            <c:forEach var="computer" items="${listComputer1}">
                <tr>
                    <td class="editMode">
                        <input type="checkbox" name="cb" class="cb" value="${computer.id}">
                    </td>
                    <td>
                        <a href="edit?computer=${computer.id}" onclick="">${computer.name}</a>
                    </td>
                    <td>${computer.introduced}</td>
                    <td>${computer.discontinued}</td>
                    <td>${computer.companyDTO.name}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </div>
</section>

<footer class="navbar-fixed-bottom">
    <div class="container text-center">
        <ul class="pagination">
            <c:if test="${currentPage > 1}">
                <li>
                    <a href="/dashboard?currentPage=${currentPage-1}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
            </c:if>
            <c:forEach var="i" begin="${pgStart}" end="${pgEnd-1}">
                <c:choose>
                    <c:when test="${i == currentPage}">
                        <li class="active"><a href="/dashboard?currentPage=${i}">${i}</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="/dashboard?currentPage=${i}">${i}</a></li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:if test="${not lastPage}">
                <li>
                    <a href="/dashboard?currentPage=${currentPage+1}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </c:if>
        </ul>

        <div class="btn-group btn-group-sm pull-right" role="group">
            <a href="<%=request.getContextPath()%>/dashboard?limit=0" class="btn btn-default ${sessionScope.paginateLimit == 10 ? 'active' : ''}">10</a>
            <a href="<%=request.getContextPath()%>/dashboard?limit=1" class="btn btn-default ${sessionScope.paginateLimit == 50 ? 'active' : ''}">50</a>
            <a href="<%=request.getContextPath()%>/dashboard?limit=2" class="btn btn-default ${sessionScope.paginateLimit == 100 ? 'active' : ''}">100</a>
        </div>

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
<script src="/js/dashboard.js"></script>
</body>

</html>
