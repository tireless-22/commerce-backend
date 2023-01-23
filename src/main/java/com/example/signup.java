package com.example;
import com.google.gson.JsonObject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
public class signup extends HttpServlet {
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String firstName=req.getParameter("firstName");
        String lastName=req.getParameter("lastName");
        String mail=req.getParameter("mail");
        String password = req.getParameter("password");
        String role=req.getParameter("role");
        try {
            if(CommonFunctions.user_existed_or_not(mail)=="false") {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecom", "root", "Knk@1234");
                String query1 = "insert into User(email,password,firstName,lastName,role) values(?,?,?,?,?)";
                PreparedStatement st1 = con.prepareStatement(query1);
                st1.setString(1, mail);
                st1.setString(2, password);
                st1.setString(3, firstName);
                st1.setString(4, lastName);
                st1.setString(5, role);
                int count = st1.executeUpdate();
                JsonObject obj=new JsonObject();
                obj.addProperty("msg","Account Created Successfully");
                obj.addProperty("stat",1);
                res.getWriter().println(obj);
            }
            else {
                    JsonObject obj=new JsonObject();
                    obj.addProperty("msg","This email is Registered, try login with this");
                    obj.addProperty("stat",0);
                    res.getWriter().println(obj);
            }
        } catch (Exception e) {
            System.out.println(e);
            JsonObject obj=new JsonObject();
            obj.addProperty("msg","Something went wrong");
            obj.addProperty("stat",0);
            res.getWriter().println(obj);
        }
    }
}