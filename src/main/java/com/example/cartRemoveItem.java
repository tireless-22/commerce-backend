








        package com.example;


import java.io.Console;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


public class cartRemoveItem extends HttpServlet{
    public void service(HttpServletRequest req,HttpServletResponse res) throws IOException {
//        int id=parseInt(req.getParameter("userId"));
        String userId=req.getParameter("userId");
        String itemId = req.getParameter("itemId");


        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecom", "root", "Knk@1234");


            String query1="delete from Cart where userId=? AND itemId=? ";
            PreparedStatement st1 = con.prepareStatement(query1);

            st1.setString(1, userId);
            st1.setString(2, itemId);

            int count = st1.executeUpdate();

            if (count == 0) {
                res.getWriter().println("Already removed from cart");
            } else {
                res.getWriter().println("Removed the item from cart");

            }


        } catch (Exception e) {
            res.getWriter().println(e);

        }



    }
}


