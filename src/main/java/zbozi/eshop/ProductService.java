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
        Collection<Product> resultList = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM product");

            while (resultSet.next()) {
                Product product = extractProductData(resultSet);
                resultList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
        try {
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM product WHERE id = " + id);

            if (resultSet.next()) {
                return extractProductData(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Product saveNewItem(Product product) throws SQLException {
        Statement statement = connection.createStatement();
        try {
            statement.executeUpdate(
                    "INSERT INTO product(partNo, name, description, isForSale, price) VALUES ("+
                            product.getPartNo() + ", '" + product.getName() + "', '" + product.getDescription() +
                            "', " + product.getIsForSale() + ", " + product.getPrice() +")",
                    Statement.RETURN_GENERATED_KEYS);

//        return statement.getGeneratedKeys().getLong("id");
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            product.setId(generatedKeys.getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    public void updatePriceById(int id, BigDecimal price) throws SQLException {
        Statement statement = connection.createStatement();
        try {
            statement.executeUpdate(
                    "UPDATE product SET price = " + price + " WHERE id = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public void deleteOutOfSaleItems(boolean isForSale) throws  SQLException {
//        Statement statement = connection.createStatement();
//        try {
//            statement.executeUpdate(
//                    "DELETE FROM product WHERE isForSale = " + isForSale);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

//    public void deleteOutOfSaleItems() throws  SQLException {
//        Statement statement = connection.createStatement();
//
//        statement.executeUpdate(
//                "DELETE FROM product WHERE isForSale = false");
//    }


}
