import java.sql.Connection;
import java.util.Scanner;

public class Customer {
    private Connection connection;

    private Scanner scanner;

    public Customer(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }
}