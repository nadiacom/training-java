<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ attribute name="url" required="true"%>
<%@ attribute name="pgStart" required="true" type="java.lang.Integer"%>
<%@ attribute name="pgEnd" required="true" type="java.lang.Integer"%>
<%@ attribute name="currentPage" required="true" type="java.lang.Integer"%>
<%@ attribute name="lastPage" required="true" type="java.lang.Boolean"%>

<ul class="pagination">
    <c:if test="${currentPage > 1}">
        <li>
            <a href="${url}?currentPage=${currentPage-1}" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
            </a>
        </li>
    </c:if>
    <c:forEach var="i" begin="${pgStart}" end="${pgEnd-1}">
        <li class="${i == currentPage ? 'active' : ''}"><a href="${url}?currentPage=${i}">${i}</a></li>
    </c:forEach>

    <c:if test="${not lastPage}">
        <li>
            <a href="${url}?currentPage=${currentPage+1}" aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
            </a>
        </li>
    </c:if>
</ul>

<div class="btn-group btn-group-sm pull-right" role="group">
    <a href="<%=request.getContextPath()%>${url}?limit=0" class="btn btn-default ${sessionScope.paginateLimit == 10 ? 'active' : ''}">10</a>
    <a href="<%=request.getContextPath()%>${url}?limit=1" class="btn btn-default ${sessionScope.paginateLimit == 50 ? 'active' : ''}">50</a>
    <a href="<%=request.getContextPath()%>${url}?limit=2" class="btn btn-default ${sessionScope.paginateLimit == 100 ? 'active' : ''}">100</a>
</div>