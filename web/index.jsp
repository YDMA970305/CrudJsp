<%-- 
    Document   : index
    Created on : 21/11/2024, 1:34:58 a. m.
    Author     : Yesid Martinez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@page import = "Domain.Model.User"%>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Página de inicio</title>
    </head>
    <body>
        <h1>Bienvenido a la Gestión de Usuarios</h1>
        <%--Verificamos si el usuario ha iniciado sesión--%>
        <% User LoggedInUser = (User) session.getAttribute("LoggedInUser");%>
        <% if(LoggedInUser == null){%>
        <%--si no ha iniciado sesión, mostramos la opción de login--%>
        <h3>Por favor, inicie sesión para continuar</h3>
        <a href="<%=request.getContextPath() %>/Controllers/UserController.jsp?action=login">Iniciar Sesión</a>
        <%}else{%>
        <%--si ha iniciado sesión, mostramos el menu de Gestión de Usuarios--%>
        <h3>Bienvenido <%=LoggedInUser.getName() %> Has iniciado sesión</h3>
        <ul>
        <a href="<%=request.getContextPath() %>/Controllers/UserController.jsp?action=createForm">Agregar Usuario</a>
        <a href="<%=request.getContextPath() %>/Controllers/UserController.jsp?action=showFindForm">Buscar Usuario</a>
        <a href="<%=request.getContextPath() %>/Controllers/UserController.jsp?action=listAll">Listar Todos los Usuarios</a>
        </ul>
        <br>
        <a href="<%=request.getContextPath() %>/Controllers/UserController.jsp?action=logout">Cerrar Sesión</a>
    <%}%>
    </body>
</html>
