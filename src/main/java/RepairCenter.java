import database.DataBase;

import java.sql.Connection;
import java.sql.SQLException;

public class RepairCenter {

    public static void main(String[] args) {
       //take a new device for repair
        Device device = new Device("Asus", "Ноутбук", "F3j", "123456", "device issue");

        device.print();
        try (Connection con = DataBase.getConnection()) {

        }
        catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Облом с получением Connection");
            throw new RuntimeException();
        }
    }
}
