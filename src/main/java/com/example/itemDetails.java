package com.example;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
public class itemDetails extends HttpServlet {


    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {

        String id = req.getParameter("id");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecom", "root", "Knk@1234");
            String query1 = "select * from Item where id=? ORDER BY created_at DESC";


            PreparedStatement st1 = con.prepareStatement(query1);
            st1.setString(1, id);
            ResultSet rs = st1.executeQuery();
            JsonArray jsonArray = new JsonArray();
            while (rs.next()) {
                JsonObject row=new JsonObject();
                row.addProperty("id",rs.getInt(1));
                row.addProperty("name", rs.getString(2));
                row.addProperty("description", rs.getString(3));
                row.addProperty("price", rs.getInt(5));
                row.addProperty("image_id", rs.getString(6));
                row.addProperty("quantity", rs.getInt(7));
                jsonArray.add(row);
            }
            res.getWriter().println(jsonArray);
        } catch (Exception e) {
            res.getWriter().println(e);
        }
    }
}
