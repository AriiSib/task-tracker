<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
    <nav class="navbar navbar-expand-md navbar-dark" style="background-color: rgba(31, 31, 31, 0.9);">
        <div>
            <a href="<%=request.getContextPath()%>/start" class="navbar-brand">Task-Tracker App</a>
        </div>
        <ul class="navbar-nav ms-auto">
            <li class="nav-item">
                <a href="<%= request.getContextPath() %>/login" class="nav-link">Log in</a>
            </li>
            <li class="nav-item">
                <a href="<%= request.getContextPath() %>/register" class="nav-link">Sign up</a>
            </li>
        </ul>
    </nav>
</header>