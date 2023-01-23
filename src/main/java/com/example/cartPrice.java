package com.example;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static java.lang.Integer.parseInt;
public class cartPrice extends HttpServlet {
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
        int id=parseInt(req.getParameter("userId"));
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecom", "root", "Knk@1234");
            String query1 = "select sum(Item.price*Cart.quantity) from Item INNER JOIN Cart on Item.id=Cart.itemId and Cart.userId=?";
            PreparedStatement st1 = con.prepareStatement(query1);
            st1.setInt(1, id);
            ResultSet rs = st1.executeQuery();
            if(rs.next()){
                res.getWriter().println(rs.getInt(1));
            }
        } catch (Exception e) {
            res.getWriter().println(e);
        }
    }
}