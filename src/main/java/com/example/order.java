package com.example;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
public class order extends HttpServlet {
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String id=req.getParameter("id");
        String userId=req.getParameter("userId");
        String price = req.getParameter("price");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecom", "root", "Knk@1234");
            String query1 = "insert into Orders (id,userId,total_amount) values(?,?,?)";
            PreparedStatement st1 = con.prepareStatement(query1);
            st1.setString(1, id);
            st1.setString(2, userId);
            st1.setString(3, price);
            int count = st1.executeUpdate();
            if (count == 0) {
                System.out.println("hello from if");
                res.getWriter().println("already Added to cart");
            } else {
                System.out.println("hello from else");
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecom", "root", "Knk@1234");
                String query2="insert into OrderItem (select ? as orderId, Cart.itemId, Cart.quantity, Item.image_id, Item.price, ? as userId, Cart.created_at,Item.name from Cart inner join Item on Item.id=Cart.itemId where Cart.userId=?)";
                PreparedStatement st2 = con1.prepareStatement(query2);
                st2.setString(1, id);
                st2.setString(2, userId);
                st2.setString(3, userId);
                int count2 = st2.executeUpdate();
                if(count2==0){
                    System.out.println("count2 is 0");
                }
                else{
                    System.out.println("count2 is not zero");
                }
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecom", "root", "Knk@1234");
                String query3 = "delete from Cart where (userId=?);";
                PreparedStatement st3 = con2.prepareStatement(query3);
                st3.setString(1, userId);
                int count3 = st3.executeUpdate();
                res.getWriter().println("message sent successfully");
            }
        } catch (Exception e) {
            res.getWriter().println(e);
        }
    }
}