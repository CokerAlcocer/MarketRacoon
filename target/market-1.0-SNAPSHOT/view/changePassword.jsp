<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String context = request.getContextPath();
    if(request.getSession(false).getAttribute("user") != null) {
        response.sendRedirect(context + "/view/home.jsp");
    }
    boolean success = request.getAttribute("success") != null && (boolean) request.getAttribute("success");
    boolean error = request.getAttribute("error") != null && (boolean) request.getAttribute("error");
    boolean flag = request.getAttribute("flag") != null && (boolean) request.getAttribute("flag");
    String email = request.getAttribute("email") != null ? (String) request.getAttribute("email") : null;
%>
<html>
<head>
    <title>Restablecer contraseña</title>
    <link rel="shortcut icon" href="<%=context%>/src/img/app_icon.png" type="image/x-icon">
    <link rel="stylesheet" href="<%=context%>/src/css/generals.css">
    <link rel="stylesheet" href="<%=context%>/src/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=context%>/src/css/bootstrap-icons.min.css">
</head>
<body>
<main class="main d-flex flex-column align-items-center justify-content-center">
    <img src="<%=context%>/src/img/app_icon.png" height="90" width="90" alt="">
    <div class="card border-0 shadow" style="width: 350px">
        <div class="card-body">
            <p class="text-center fw-bold fs-5 mb-4">La tienda del mapache</p>
            <%if(error) {%>
            <div class="col-12">
                <div class="alert alert-danger mb-0">Este código de recuperación no existe o ya expiró.</div>
            </div>
            <%}%>
            <%if(success || (request.getAttribute("flag") != null && !flag)) {%>
            <form action="<%=context%>/DisableRecoveryCodeServlet" method="post" class="mb-0">
                <div class="row g-3">
                    <input type="hidden" id="email" name="email" value="<%=email%>" class="form-control" autocomplete="false">
                    <div class="col-12">
                        <label for="password"><small>Nueva contraseña</small></label>
                        <input type="password" id="password" name="password" class="form-control" autocomplete="false">
                    </div>
                    <div class="col-12">
                        <label for="conf"><small>Confirmar contraseña</small></label>
                        <input type="password" id="conf" name="conf" class="form-control" autocomplete="false">
                    </div>
                    <%if(request.getAttribute("flag") != null && !flag) {%>
                    <div class="col-12">
                        <div class="alert alert-danger mb-0">Ocurrió un error al cambiar la contraseña</div>
                    </div>
                    <%}%>
                    <div class="col-12 text-center">
                        <button type="submit" class="mt-3 btn btn-primary col-12">Restablecer contraseña</button>
                    </div>
                </div>
            </form>
            <%}%>
        </div>
    </div>
</main>
</body>
</html>
