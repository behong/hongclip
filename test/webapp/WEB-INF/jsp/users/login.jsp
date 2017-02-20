<%@ page language="java" contentType="text/html; charset=UTF-8" 	pageEncoding="UTF-8"%>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../commons/_head.jspf" %>
</head>
<body>
    <%@ include file="../commons/_top.jspf" %>
 
	<div class="container">
		<div class="row">
			<div class="span12">
				
				<div class="page-header">
					<h1>로그인</h1>
				</div>
				
				<form:form modelAttribute="authenticate" cssClass="form-horizontal" action="/users/login" method="post">
				
					<div class="control-group">
						<label class="control-label" for="userId">사용자 아이디</label>
						<div class="controls">
							<form:input path="userId"/>
							<form:errors path="userId" cssClass="error"></form:errors>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="password">비밀번호</label>
						<div class="controls">
							<form:password path="password"/>
							<form:errors path="password" cssClass="error"></form:errors>
						</div>
					</div>
					<c:if test="${not empty errorMessage}" >
						<div class="control-group">
							<div class="controls">
								<div class="error">${errorMessage}</div>
							</div>
						</div>
					</c:if>
					
					<div class="control-group">
						<div class="controls">
							<button type="submit" class="btn btn-primary">로그인</button>
						</div>
					</div>
				</form:form>

			</div>
		</div>
	</div>
</body>
</html>