package zbozi.eshop;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
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

    public Collection<Product> loadAllAvailableItems() throws SQLException {
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(
                "SELECT * FROM product");

        Collection<Product> resultList = new ArrayList<>();

        while (resultSet.next()) {
            Product product = extractProductData(resultSet);

            resultList.add(product);
        }

        return resultList;
    }

    private static Product extractProductData(ResultSet resultSet) throws SQLException {
        return new Product(
                resultSet.getInt("id"),
                resultSet.getInt("partNo"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getBoolean("isForSale"),
                resultSet.getBigDecimal("price"));
    }

    public Product loadProductById(int id) throws SQLException {
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(
                "SELECT * FROM product WHERE id = " + id);

        if (resultSet.next()) {
            return extractProductData(resultSet);
        }

        return null;
    }

    public Product saveNewItem(Product product) throws SQLException {
        Statement statement = connection.createStatement();

        statement.executeUpdate(
                "INSERT INTO product(partNo, name, description, isForSale, price) VALUES ("+
                        product.getPartNo() + ", '" + product.getName() + "', '" + product.getDescription() +
                        "', " + product.getIsForSale() + ", " + product.getPrice() +")",
                Statement.RETURN_GENERATED_KEYS);

//        return statement.getGeneratedKeys().getLong("id");
        ResultSet generatedKeys = statement.getGeneratedKeys();
        generatedKeys.next();
        product.setId(generatedKeys.getInt(1));

        return product;
    }

    public void updatePriceById(int id, BigDecimal price) throws SQLException {
        Statement statement = connection.createStatement();

        statement.executeUpdate(
                "UPDATE product SET price = " + price + " WHERE id = " + id);
    }

    public void deleteOutOfSaleItems(boolean isForSale) throws  SQLException {
        Statement statement = connection.createStatement();

        statement.executeUpdate(
                "DELETE FROM product WHERE isForSale = " + isForSale);
    }
}
