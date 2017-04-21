<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="url" required="true" %>
<%@ attribute name="currentPage" %>
<%@ attribute name="search" %>
<%@ attribute name="limit" %>
<%@ attribute name="order" %>
<%@ attribute name="id" %>

<c:set var="first" value="${1}"/>${url}<c:if test="${!empty currentPage}"><c:if test="${first==1}">?<c:set var="first" value="${0}"/></c:if>page=${page}</c:if><c:if test="${!empty search}"><c:choose><c:when test="${first==1}"><c:set var="first" value="${0}"/>?</c:when><c:otherwise>&</c:otherwise></c:choose>search=${search}</c:if><c:if test="${!empty limit}"><c:choose><c:when test="${first==1}"><c:set var="first" value="${0}"/>?</c:when><c:otherwise>&</c:otherwise></c:choose>limit=${limit}</c:if><c:if test="${!empty order}"><c:choose><c:when test="${first==1}"><c:set var="first" value="${0}"/>?</c:when><c:otherwise>&</c:otherwise></c:choose>order=${order}</c:if><c:if test="${!empty id}"><c:choose><c:when test="${first==1}"><c:set var="first" value="${0}"/>?</c:when><c:otherwise>&</c:otherwise></c:choose>id=${id}</c:if>