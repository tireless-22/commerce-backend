package com.example;


import java.io.Console;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

import com.google.gson.Gson;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import static java.lang.Integer.parseInt;


public class listItems extends HttpServlet{
    public void service(HttpServletRequest req,HttpServletResponse res) throws IOException {
        int id=parseInt(req.getParameter("userId"));


        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecom", "root", "Knk@1234");
            String query1 = "select Item.id,Item.name,Item.description,Item.price,Item.quantity,Cart.quantity from Item LEFT JOIN Cart on Item.id=Cart.itemId and Cart.userId=?";
            PreparedStatement st1 = con.prepareStatement(query1);
            st1.setInt(1, id);


            ResultSet rs = st1.executeQuery();
            JsonArray jsonArray = new JsonArray();

            while (rs.next()) {
                JsonObject row=new JsonObject();
                row.addProperty("id",rs.getInt(1));

                row.addProperty("name", rs.getString(2));
                row.addProperty("description", rs.getString(3));
                row.addProperty("price", rs.getInt(4));
                row.addProperty("available", rs.getInt(5));
                row.addProperty("quantity", rs.getInt(6));

                jsonArray.add(row);
            }

            res.getWriter().println(jsonArray);

        } catch (Exception e) {
            res.getWriter().println(e);

        }

    }
}
