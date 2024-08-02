package utez.edu.mx.market.daos;

import utez.edu.mx.market.utils.DBConnection;
import utez.edu.mx.market.utils.PasswordEncoding;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoLogin {
    private Connection con;
    private PreparedStatement pstm;
    private ResultSet rs;
    private final DBConnection DB_CONNECTION = new DBConnection();
    private final String[] QUERIES = {
            "SELECT * FROM user WHERE password = ? AND (username = ? OR email = ?);",
            "SELECT password FROM user WHERE username = ? OR email = ?;"
    };

    public boolean findUserByUsernameAndPassword(String username, String password) {
        try {
            con = DB_CONNECTION.getConnection();
            pstm = con.prepareStatement(QUERIES[0]);
            pstm.setString(1, password);
            pstm.setString(2, username);
            pstm.setString(3, username);
            rs = pstm.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection();
        }
    }

    public boolean verifyPassword(String username, String password) {
        try {
            con = DB_CONNECTION.getConnection();
            pstm = con.prepareStatement(QUERIES[1]);
            pstm.setString(1, username);
            pstm.setString(2, username);
            rs = pstm.executeQuery();
            if(rs.next() && new PasswordEncoding().checkPassword(password, rs.getString("password"))) {
                return findUserByUsernameAndPassword(username, rs.getString("password"));
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection();
        }
    }

    public void closeConnection() {
        try {
            if(con != null) {
                con.close();
            }
            if(pstm != null) {
                pstm.close();
            }
            if(rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
