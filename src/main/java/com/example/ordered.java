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
import static java.lang.Integer.parseInt;
public class ordered extends HttpServlet {
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
        int id=parseInt(req.getParameter("userId"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecom", "root", "Knk@1234");
            String query1 = "select * from OrderItem where userId=? order by created_at desc;";
            PreparedStatement st1 = con.prepareStatement(query1);
            st1.setInt(1, id);
            ResultSet rs = st1.executeQuery();
            JsonArray jsonArray = new JsonArray();
            while (rs.next()) {
                JsonObject row=new JsonObject();

//                orderId,itemId,quantity,image_id,price,userId,created_at


                row.addProperty("orderId",rs.getString(1));
                row.addProperty("itemId", rs.getInt(2));
                row.addProperty("quantity", rs.getInt(3));
                row.addProperty("image_id", rs.getString(4));
                row.addProperty("price", rs.getInt(5));
                row.addProperty("userId", rs.getInt(6));
                row.addProperty("created_at", rs.getString(7));
                row.addProperty("name", rs.getString(8));
                jsonArray.add(row);
            }
            res.getWriter().println(jsonArray);
        } catch (Exception e) {
            res.getWriter().println(e);
        }
    }
}