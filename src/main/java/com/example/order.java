package com.example;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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

                 Class.forName("com.mysql.cj.jdbc.Driver");
                 Connection con4= DriverManager.getConnection("jdbc:mysql://localhost:3306/ecom", "root", "Knk@1234");
                 String query4 = "select itemId,quantity from Cart where userId=?";
                 PreparedStatement st4 = con4.prepareStatement(query4);

                 st4.setString(1, userId);
                 ResultSet rs4 = st4.executeQuery();

                 ArrayList<ArrayList<Integer>> matrix= new ArrayList<ArrayList<Integer> >();

                 while (rs4.next()) {

                     ArrayList<Integer> row = new ArrayList<Integer>();
                     row.add(rs4.getInt(1));
                     row.add(rs4.getInt(2));
                     matrix.add(row);
                 }
                 System.out.println(matrix);
                 for(int i=0;i<matrix.size();i++){
                     ArrayList<Integer> list=matrix.get(i);
                     Class.forName("com.mysql.cj.jdbc.Driver");
                     Connection con5 = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecom", "root", "Knk@1234");
                     String query5="update Item set quantity=quantity-? where id=?";
                     PreparedStatement st5 = con5.prepareStatement(query5);
                     st5.setInt(1, list.get(1));
                     st5.setInt(2,list.get(0));

                     int count5 = st5.executeUpdate();
                     if(count5==0){
                         System.out.println("count2 is 0");
                     }
                     else{
                         System.out.println("count2 is not zero");
                     }
                 }

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