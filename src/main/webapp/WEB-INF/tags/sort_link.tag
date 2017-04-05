<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ attribute name="url" required="true"%>
<%@ attribute name="currentPage" required="true" type="java.lang.String"%>
<%@ attribute name="search" required="true" type="java.lang.String"%>
<%@ attribute name="order" required="true" type="java.lang.String"%>
<%@ attribute name="click" required="true" type="java.lang.String"%>
<%@ attribute name="id" required="true" type="java.lang.String"%>

<a href="${url}?currentPage=${currentPage}&search=${search}&order=${order}&click=${click}"><i id="${id}" name="sort" class="fa fa-fw fa-sort"></i></a>