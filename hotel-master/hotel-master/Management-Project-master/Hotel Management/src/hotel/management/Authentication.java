package hotel.management;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author sumair
 */
public class Authentication {

   Connection con;
    PreparedStatement pstmt;
    Statement stmt;
    String query;
    ResultSet rs;
    boolean authentication_status;

    public boolean auhtenticate(int user_id, String password) {
        try {
            con = DBConnection.getConnection();
            query = "select password from login where id = '"+user_id+"'";
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs != null && rs.next()) {
                String db_pass = rs.getString("password");
                authentication_status = db_pass.equals(password);
            }
        } catch (java.sql.SQLException exp) {
            Logger.getLogger(Authentication.class.getName()).log(Level.SEVERE, null, exp);
        }
        return authentication_status;
    }
}
