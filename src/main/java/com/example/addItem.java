package com.example;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
public class addItem extends HttpServlet {
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {

        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String price = req.getParameter("price");
        String category="general";
        String image_id = req.getParameter("image_id");
        String quantity = req.getParameter("quantity");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecom", "root", "Knk@1234");
            String query1 = "insert into Item(name,description,category,price,image_id,quantity) values(?,?,?,?,?,?)";
            PreparedStatement st1 = con.prepareStatement(query1);
            st1.setString(1, name);
            st1.setString(2, description);
            st1.setString(3, category);
            st1.setString(4, price);
            st1.setString(5, image_id);
            st1.setString(6, quantity);

            int count = st1.executeUpdate();
            if (count == 0) {
                res.getWriter().println("unable to add");
            } else {
                res.getWriter().println("Item created");
            }
        } catch (Exception e) {
            res.getWriter().println(e);
        }
    }
}