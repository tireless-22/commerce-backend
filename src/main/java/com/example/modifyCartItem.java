
        package com.example;

        import java.io.IOException;
        import javax.servlet.http.HttpServlet;
        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;

        import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.PreparedStatement;


public class modifyCartItem extends HttpServlet{
    public void service(HttpServletRequest req,HttpServletResponse res) throws IOException {

        String userId=req.getParameter("userId");
        String itemId = req.getParameter("itemId");
        String quantity = req.getParameter("quantity");

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecom", "root", "Knk@1234");


            String query1="update Cart set quantity=? where userId=? AND itemId=?";
            PreparedStatement st1 = con.prepareStatement(query1);

            st1.setString(2, userId);
            st1.setString(3, itemId);
            st1.setString(1, quantity);

            int count = st1.executeUpdate();

            if (count == 0) {
                res.getWriter().println("already Added to cart");

            } else {

                res.getWriter().println("message sent successfully");
            }

        } catch (Exception e) {
            res.getWriter().println(e);

        }

    }
}


