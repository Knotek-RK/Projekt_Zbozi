package zbozi.eshop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ProductService {

    Connection connection;

    public ProductService() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/products",
                "products_crud",
                "localhost2002"
        );
    }


}
