<%-- 
    Document   : login
    Created on : 21/11/2024, 1:34:21 a. m.
    Author     : Yesid Martinez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <h1>Iniciar Sesión</h1>
        <%--mensaje de error en caso de credenciales incorrectas--%>
        <% if(request.getAttribute("errorMessage") != null){%>
            <p style="color:red"><%=request.getAttribute("errorMessage")%></p>
       <%}%>
       <%--formulario de login--%>
        <form action="<%=request.getContextPath() %>/Controllers/UserController.jsp?action=authenticate" method="post">
 
            <label for="email">Email</label><br>
            <input type="email" id ="email" name="email" required><br><br>

            <label for="password">Contraseña</label><br>
            <input type="password" id ="password" name="password" required><br><br>

            <input type="submit" value="Iniciar Sesión">
            </form>
            <br>
            <a href="<%=request.getContextPath() %>/index.jsp">Volver a la página de creación de inicio</a>
        </body>
</html>
