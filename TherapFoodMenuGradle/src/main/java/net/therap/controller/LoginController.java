package net.therap.controller;

import net.therap.domain.User;
import net.therap.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: sanjoy.saha
 * Date: 5/20/14
 * Time: 3:59 PM
 * To change this template use File | Settings | File Templates.
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {

    UserService userService;

    public LoginController() {
        userService = new UserService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {        User user = new User();
        user.setUserName (request.getParameter("user_name"));
        user.setUserPass (request.getParameter("user_password"));

        User authenticatedUser = userService.verifyUser(user);

        if (authenticatedUser != null) {
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("authenticatedUser", authenticatedUser);
            response.sendRedirect(request.getContextPath()+ "/home");
        } else {
            request.setAttribute("message", "Please enter correct combination of username and password");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
        requestDispatcher.forward(request, response);
    }
}
