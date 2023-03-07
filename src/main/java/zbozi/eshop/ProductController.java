package zbozi.eshop;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Collection;

@RestController
public class ProductController {

    ProductService productService;

    public ProductController() throws SQLException {
        productService = new ProductService();
    }

    @GetMapping("/products")
    public Collection<Product> getAllItems() throws SQLException {
        return productService.loadAllAvailableItems();
    }

    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable(value = "id") int id) throws SQLException {
        return productService.loadProductById(id);
    }

    @PostMapping("/new-product")
    public Product newItem(@RequestBody Product product) throws SQLException {
        return productService.saveNewItem(product);
    }

    @PatchMapping("/product/{id}")
    public void updatePrice(@PathVariable(value = "id") int id, @RequestParam(value = "price", required = true) BigDecimal price) throws SQLException {
        productService.updatePriceById(id, price);

    }

//    @DeleteMapping("/product")
//    public Product deleteItems(@RequestParam("isForSale") boolean isForSale) throws SQLException {
//        productService.deleteOutOfSaleItems(isForSale);
//        return deleteItems(isForSale);
//    }

//    @DeleteMapping("/product")
//    public Product deleteItems() throws SQLException {
////        return productService.deleteOutOfSaleItems();
//        return deleteItems();
//    }

    @PatchMapping("/sell-or-stock")
    public void isForSaleChange(@RequestParam(value = "isForSale") boolean isForSale) throws SQLException {
        productService.SellOrStock(isForSale);
    }
}
