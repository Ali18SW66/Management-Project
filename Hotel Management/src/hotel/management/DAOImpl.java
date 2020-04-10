package hotel.management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class DAOImpl implements DAO
{
    private Statement stat=null;
    private PreparedStatement pst=null;
    private final Connection CONNECTION=DBConnection.getConnection();
    private ResultSet rs=null;
    @Override
    public boolean isValidUser(int userId,String password)
    {
        boolean isValid=false;
        try 
        {
            String sql = "SELECT username,password FROM users WHERE username=? AND password=?";
            pst = CONNECTION.prepareStatement(sql);
            pst.setInt(1, userId);
            pst.setString(2, password);
            rs = pst.executeQuery();
            if (rs.next())
            {   
                isValid=true;
            } 
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return isValid;

    }
}
