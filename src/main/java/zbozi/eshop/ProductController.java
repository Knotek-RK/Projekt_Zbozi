package zbozi.eshop;

import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Collection;

@RestController
public class ProductController {

    ProductService productService;

    public ProductController() throws SQLException {
        productService = new ProductService();
    }

    @GetMapping("/Eshop")
    public Collection<Product> getAllItems() throws SQLException {
        return productService.loadAllAvailableItems();
    }

    @GetMapping("/Eshop/{id}")
    public Product getProductById(@PathVariable("id") Long id) throws SQLException {
        return productService.loadProductById(id);
    }

    @GetMapping("/Eshop")
    public Product newItem(@RequestBody Product product) throws SQLException {
        Long generateId = productService.saveNewItem(product);

        product.setId(generateId);

        return product;
    }

    @PutMapping("/Eshop/{id}")
    public void updatePrice(@PathVariable("id") Long id) throws SQLException {
        productService.updatePriceById(id);
    }

    @DeleteMapping("/Eshop")
    public void deleteItems(@PathVariable("isForSale") Boolean isForSale) throws SQLException {
        productService.deleteOutOfSaleItems(Boolean.FALSE);
    }
}
