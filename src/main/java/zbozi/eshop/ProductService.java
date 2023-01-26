package zbozi.eshop;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService {

    Connection connection;

    public ProductService() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/products",
                "products_crud",
                "localhost2002"
        );
    }

    public List<Product> loadAllAvailableItems() throws SQLException {
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(
                "SELECT * FROM product");

        List<Product> resultList = new ArrayList<>();

        while (resultSet.next()) {
            Product product = extractProductData(resultSet);

            resultList.add(product);
        }

        return resultList;
    }

    private static Product extractProductData(ResultSet resultSet) throws SQLException {
        return new Product(
                resultSet.getLong("id"),
                resultSet.getInt("partNo"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getBoolean("isForSale"),
                resultSet.getBigDecimal("price"));
    }

    public Product loadProductById(Long itemId) throws SQLException {
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(
                "SELECT * FROM product WHERE id = " + itemId);

        if (resultSet.next()) {
            return extractProductData(resultSet);
        }

        return null;
    }

    public Long saveNewItem(Product product) throws SQLException {
        Statement statement = connection.createStatement();

        statement.executeUpdate(
                "INSERT INTO product(partNo, name, description, isForSale, price) VALUES ('"+
                        newProduct.getPartNo() + "', " + newProduct.getName() + "', " + newProduct.getDescription() +
                        "', " + newProduct.getForSale() + "', " + newProduct.getPrice() +")",
                Statement.RETURN_GENERATED_KEYS);

        return statement.getGeneratedKeys().getLong("id");
    }

    public void updatePriceById(Long itemId) throws SQLException {
        Statement statement = connection.createStatement();

        statement.executeUpdate(
                "UPDATE product SET price WHERE id = " + itemId);
    }

    public void deleteOutOfSaleItems(Boolean IsForSale) throws  SQLException {
        Statement statement = connection.createStatement();

        statement.executeUpdate(
                "DELETE FROM * product WHERE isForSale = FALSE");
    }
}
