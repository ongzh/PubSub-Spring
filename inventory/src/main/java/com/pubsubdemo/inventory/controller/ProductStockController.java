import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pubsubdemo.inventory.model.ProductStock;
import com.pubsubdemo.inventory.service.ProductStockService;

@RestController
@RequestMapping("/api/ProductStock")
public class ProductStockController {

    @Autowired
    private ProductStockService ProductStockService;

    @PutMapping("/updateStock/{productId}")
    public ResponseEntity<ProductStock> updateProductStockStockByProductId(@PathVariable Long productId, @RequestBody int updatedQty) {
        
        
        if (updatedQty<0){
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(ProductStockService.updateProductStockByProductId(productId, updatedQty));
    }

}
