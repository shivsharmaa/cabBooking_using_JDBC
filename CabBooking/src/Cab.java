import java.sql.Connection;
import java.util.Scanner;

public class Cab {
    private Connection connection;

    private Scanner scanner;

    public Cab(Connection connection) {
        this.connection = connection;
    }
}
