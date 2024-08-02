package utez.edu.mx.market.daos;

import utez.edu.mx.market.entities.User;
import utez.edu.mx.market.utils.DBConnection;
import utez.edu.mx.market.utils.PasswordEncoding;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DaoUser {
    private Connection con;
    private PreparedStatement pstm;
    private ResultSet rs;
    private final DBConnection DB_CONNECTION = new DBConnection();
    private final PasswordEncoding PASSWORD_ENCODING = new PasswordEncoding();
    private final String[] QUERIES = {
            "SELECT * FROM user WHERE email = ?;",
            "UPDATE user SET recovery = ?, recovery_expire_date = ? WHERE email = ?;",
            "UPDATE user SET password = ? WHERE email = ?;",
            "SELECT * FROM user WHERE recovery = ?;"
    };

    public User findUsernameByEmailToRecover(String email) {
        try {
            con = DB_CONNECTION.getConnection();
            pstm = con.prepareStatement(QUERIES[0]);
            pstm.setString(1, email);
            rs = pstm.executeQuery();
            if(rs.next()) {
                return new User(
                        rs.getString("username"),
                        rs.getString("recovery"),
                        rs.getDate("recovery_expire_date")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeConnection();
        }
    }

    public boolean manageRecoveryCode(String email, boolean isRemoving) {
        if(findUsernameByEmailToRecover(email) != null) {
            String expirationDate = generateRecoveryCodeExprationDate();
            String recoveryCode = generateRecoveryCode(email, expirationDate);
            try {
                con = DB_CONNECTION.getConnection();
                pstm = con.prepareStatement(QUERIES[1]);
                pstm.setString(1,  isRemoving ? null : recoveryCode);
                pstm.setString(2, isRemoving ? null : expirationDate);
                pstm.setString(3, email);
                return pstm.executeUpdate() == 1;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            } finally {
                closeConnection();
            }
        } else {
            return false;
        }
    }

    public boolean changePassword(String newPassword, String userEmail) {
        try {
            con = DB_CONNECTION.getConnection();
            pstm = con.prepareStatement(QUERIES[2]);
            pstm.setString(1,  new PasswordEncoding().encodePassword(newPassword));
            pstm.setString(2, userEmail);
            boolean flag = pstm.executeUpdate() == 1;
            if(flag) {
                manageRecoveryCode(userEmail, true);
            }
            return flag;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection();
        }
    }

    public boolean findRecoveryCode(String recoveryCode) {
        try {
            con = DB_CONNECTION.getConnection();
            pstm = con.prepareStatement(QUERIES[3]);
            pstm.setString(1,  recoveryCode);
            rs = pstm.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection();
        }
    }

    public String generateRecoveryCode(String email, String expirationDate) {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return code + "|" + email + "|" + expirationDate;
    }

    public String generateRecoveryCodeExprationDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", new Locale("es-MX"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        return sdf.format(calendar.getTime());
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
