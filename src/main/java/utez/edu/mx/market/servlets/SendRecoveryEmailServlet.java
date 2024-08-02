package utez.edu.mx.market.servlets;

import utez.edu.mx.market.daos.DaoUser;
import utez.edu.mx.market.entities.User;
import utez.edu.mx.market.utils.EmailService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "SendRecoveryEmailServlet", value = "/SendRecoveryEmailServlet")
public class SendRecoveryEmailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/view/recovery.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        EmailService emailService = new EmailService();

        String userEmail = request.getParameter("email");

        if(new DaoUser().manageRecoveryCode(userEmail, false)) {
            request.setAttribute("success", true);
            User user = new DaoUser().findUsernameByEmailToRecover(userEmail);
            emailService.sendRecoveryEmail(userEmail, user.getUsername(), user.getRecovery());
        } else {
            request.setAttribute("error", true);
        }

        doGet(request, response);
    }
}
