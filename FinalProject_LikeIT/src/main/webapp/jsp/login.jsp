<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <link rel="stylesheet"
          href="http://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.3/css/bootstrapValidator.min.css"/>
    <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.3/js/bootstrapValidator.min.js"></script>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/languages.min.css"/>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/navbar_orange.css"/>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/main.css"/>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/login.css"/>
    <script src="${pageContext.servletContext.contextPath}/resources/js/main.js"></script>
    <%--<script src="${pageContext.servletContext.contextPath}/resources/js/login.js"></script>--%>
    <link rel="icon" href="${pageContext.servletContext.contextPath}/resources/img/logo_icon.ico"/>
    <title>Login</title>
</head>
<body>
<%@include file="/jsp/include/menu.jsp" %>
<script type="text/javascript">
    selectMenuItem('#menu_login')
</script>
<div class="container-fluid text-center main-wrapper">
    <div class="row">
        <div class="col-sm-12 col-md-12 col-lg-9 col-lg-offset-1 text-left">
            <div class="form-wrap" id="login">
                <div class="form-main-title ">Log in to LikeIT</div>
                <c:if test="${not empty authError}">
                    <div class="alert alert-danger">
                        <a class="close" data-dismiss="alert" href="#">×</a>
                            ${authError}
                    </div>
                </c:if>

                <form role="form" method="post" id="login-form" data-toggle="validator"
                      action="<c:url value="/Home"/>">
                    <input type="hidden" name="command" value="LOGIN"/>
                    <div class="form-group">
                        <label for="user-login" class="sr-only">Login</label>
                        <input type="text" name="userLogin" id="user-login" class="form-control"
                               placeholder="Login" required>
                    </div>
                    <div class="form-group">
                        <label for="user-password" class="sr-only">Password</label>
                        <input type="password" name="userPassword" id="user-password"
                               class="form-control" placeholder="Password" required>
                    </div>
                    <input type="submit" id="btn-login" class="btn btn-custom btn-lg btn-block"
                           value="Log in">
                </form>
            </div>
        </div>
        <%@include file="/jsp/include/rightSidePanel.jsp" %>
    </div>
    <%@include file="/jsp/include/footer.jsp" %>
</div>
</body>
</html>