package com.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

// import org.apache.commons.codec.binary.Base64;

import java.security.Key;
import io.jsonwebtoken.*;

import java.util.Base64;
import java.util.Date;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

class CommonFunctions {

    public static String user_existed_or_not(String email) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecom", "root", "Knk@1234");
            String query1 = "select * from  User where email=?";
            PreparedStatement st1 = con.prepareStatement(query1);

            st1.setString(1, email);

            ResultSet rs = st1.executeQuery();

            if (rs.next()) {
                return rs.getString(3);
            } else {
                return "false";
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return "false";
    }



    public static Jws<Claims> parseJwt(String jwtString) {
        String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());

        Jws<Claims> jwt = Jwts.parserBuilder()
                .setSigningKey(hmacKey)
                .build()
                .parseClaimsJws(jwtString);

        return jwt;
    }



    private String createJWT(String id, String issuer, String subject, long ttlMillis) {


        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);


        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("secretet key");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());


        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer);

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }


        return builder.compact();
    }


    public static boolean stringCompare(String str1, String str2)
    {
        int l1 = str1.length();
        int l2 = str2.length();

        int lmin=Math.min(l1,l2);

        if(l1!=l2){
            return  false;
        }

        for (int i = 0; i < lmin; i++) {


            if (str1.charAt(i) != str2.charAt(i)) {
                return false;
            }
        }
        return true;
    }

}