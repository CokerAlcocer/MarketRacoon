package utez.edu.mx.market.servlets.redirect;

import utez.edu.mx.market.daos.DaoUser;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@WebServlet(name = "RedirectToRecoveryFormServlet", value = "/RedirectToRecoveryFormServlet")
public class RedirectToRecoveryFormServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        try {
            String code = request.getParameter("recoveryCode");
            request.setAttribute("email", code.split("\\|")[1]);
            Date expirationDate = new SimpleDateFormat("yyyy/MM/dd", new Locale("es-MX")).parse(code.split("\\|")[2].replace("-", "/"));
            if(new Date().getTime() <= expirationDate.getTime() && new DaoUser().findRecoveryCode(code)) {
                request.setAttribute("success", true);
            } else {
                request.setAttribute("error", true);
            }
        } catch (ParseException e) {
            System.out.println("Error al convertir la fecha");
            request.setAttribute("error", true);
        }

        request.getRequestDispatcher("/view/changePassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
