import java.io.*;
import java.sql.*;
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        String url = "jdbc:mysql://127.0.0.1:3306/my_database";
        String username = "root";
        String password = "Password";
        String withdrawQuery = "UPDATE accounts SET balance = balance - ? WHERE acount_number = ?";
        String depositQuery = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Drivers loaded successfully!!");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try{
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("connection established successfully");
            con.setAutoCommit(false);
            try {
                PreparedStatement withdrawStatement = con.prepareStatement(withdrawQuery);
                PreparedStatement depositStatement = con.prepareStatement(depositQuery);
                withdrawStatement.setDouble(1, 500);
                withdrawStatement.setString(2, "account123");
                depositStatement.setDouble(1, 500);
                depositStatement.setString(2, "account456");
                withdrawStatement.executeUpdate();
                depositStatement.executeUpdate();
                con.commit();
                System.out.println("Transaction successfully");
            }catch (SQLException e){
                con.rollback();
                System.out.println("Transaction failed");
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
}