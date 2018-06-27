package com.discordee.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

@WebServlet(name = "HelloServlet", urlPatterns = {"/headers"})
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<String> requestHeader = Collections.list(req.getHeaderNames());
        PrintWriter writer = resp.getWriter();

        for (String header : requestHeader) {
            writer.append(header).append('\n');
        }
    }
}
