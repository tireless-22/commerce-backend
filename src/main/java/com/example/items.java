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

public class items extends HttpServlet {
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {

        String filter=req.getParameter("filter");
        String sort=req.getParameter("sort");
        System.out.println(filter);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecom", "root", "Knk@1234");
            String query1;
            if(CommonFunctions.stringCompare(filter,"true")){

                if(CommonFunctions.stringCompare(sort,"priceDesc")){
                    query1 = "select * from Item  where quantity < 6 ORDER BY price DESC";

                }
                else if(CommonFunctions.stringCompare(sort,"quantityDesc")){
                    query1 = "select * from Item  where quantity < 6  ORDER BY quantity DESC";


                }
                else if(CommonFunctions.stringCompare(sort,"priceAsc")){
                    query1 = "select * from Item  where quantity < 6 ORDER BY price ASC";

                }
                else{
                    query1 = "select * from Item  where quantity < 6  ORDER BY quantity ASC";
                }



            }
            else{
                if(CommonFunctions.stringCompare(sort,"priceDesc")){

                    query1 = "select * from Item ORDER BY price DESC";

                }
                else if(CommonFunctions.stringCompare(sort,"quantityDesc")){
                    System.out.println("filter true");
                    query1 = "select * from Item ORDER BY quantity DESC";

                }
                else if(CommonFunctions.stringCompare(sort,"priceAsc")){

                    query1 = "select * from Item ORDER BY price ASC";

                }
                else{
                    System.out.println("filter true");
                    query1 = "select * from Item ORDER BY quantity ASC";

                }
            }

            PreparedStatement st1 = con.prepareStatement(query1);
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