<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header>
    <nav class="navbar navbar-expand-md navbar-dark"
         style="background-color: tomato">
        <div>
            <a href="https://github.com/AriiSib/task-tracker" class="navbar-brand"> Task-Tracker App</a>
        </div>

        <ul class="navbar-nav navbar-collapse justify-content-end">
            <li><a href="<%= request.getContextPath() %>/login" class="nav-link">Log in</a></li>
            <li><a href="<%= request.getContextPath() %>/register" class="nav-link">Sign up</a></li>
        </ul>
    </nav>
</header>