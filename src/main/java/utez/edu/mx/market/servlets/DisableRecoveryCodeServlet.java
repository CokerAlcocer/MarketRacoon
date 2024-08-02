package utez.edu.mx.market.servlets;

import utez.edu.mx.market.daos.DaoUser;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

@WebServlet(name = "DisableRecoveryCodeServlet", value = "/DisableRecoveryCodeServlet")
public class DisableRecoveryCodeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean flag = (boolean) request.getAttribute("flag");
        request.getRequestDispatcher(flag ? "/view/login.jsp" : "/view/changePassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String password = request.getParameter("password");
        String confirmation = request.getParameter("conf");
        String email = request.getParameter("email");
        request.setAttribute("flag", password.equals(confirmation) && new DaoUser().changePassword(password, email));

        doGet(request, response);
    }
}
