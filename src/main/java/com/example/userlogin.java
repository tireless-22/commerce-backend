package com.example;
import com.google.gson.JsonObject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.SysexMessage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class userlogin extends HttpServlet{
    public void service(HttpServletRequest req,HttpServletResponse res) throws IOException {
        String mail=req.getParameter("mail");
        String password = req.getParameter("password");
        try {
            if(CommonFunctions.user_existed_or_not(mail)!="false"){
                String dbPassword=CommonFunctions.user_existed_or_not(mail);
                if(CommonFunctions.stringCompare(dbPassword,password)){
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecom", "root", "Knk@1234");
                    String query1 = "select * from  User where email=?";
                    PreparedStatement st1 = con.prepareStatement(query1);
                    st1.setString(1, mail);
                    ResultSet rs = st1.executeQuery();
                    JsonObject obj=new JsonObject();
                    obj.addProperty("msg","successfully");
                    obj.addProperty("stat",1);
                    if(rs.next()){
                        obj.addProperty("firstName",rs.getString(5));
                        obj.addProperty("id",rs.getInt(1));
                    System.out.println(rs.getString(5));
                    }
                    res.getWriter().println(obj);
                }
                else{
                    JsonObject obj=new JsonObject();
                    obj.addProperty("msg","Wrong Password");
                    obj.addProperty("stat",0);
                    res.getWriter().println(obj);
                }
            }
            else{
                JsonObject obj=new JsonObject();
                obj.addProperty("msg","User not found");
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