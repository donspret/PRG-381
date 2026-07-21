package com.cleaninginventory.controller;

import com.cleaninginventory.dao.UserDAO;
import com.cleaninginventory.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {


        String username = request.getParameter("username");
        String password = request.getParameter("password");


        UserDAO userDAO = new UserDAO();

        User user = userDAO.loginUser(username, password);



        if (user != null) {


            HttpSession session = request.getSession();


            session.setAttribute("user", user);

            session.setAttribute("username",
                    user.getUsername());

            session.setAttribute("role",
                    user.getRole());



            response.sendRedirect("dashboard.jsp");


        } else {


            response.sendRedirect("login.jsp");


        }


    }
}
