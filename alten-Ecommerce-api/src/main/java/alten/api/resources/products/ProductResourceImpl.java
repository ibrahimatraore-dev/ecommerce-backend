package alten.api.resources.products;

import alten.core.dtos.product.ProductRequestDTO;
import alten.core.dtos.product.ProductResponseDTO;
import alten.core.services.products.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class ProductResourceImpl implements IProductResource {

    private final IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<Page<ProductResponseDTO>> list(@RequestParam(required = false) String keyword, Pageable pageable) {
        log.info("GET /products/all called with keyword='{}'", keyword);
        Page<ProductResponseDTO> result = productService.list(keyword, pageable);
        log.info("Returning {} products", result.getTotalElements());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> get(@PathVariable Long id) {
        log.info("GET /products/{} called", id);
        ProductResponseDTO response = productService.findById(id);
        log.info("Found product: {}", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@RequestBody ProductRequestDTO dto) {
        log.info("POST /products called with data: {}", dto);
        ProductResponseDTO created = productService.create(dto);
        log.info("Product created: {}", created);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id, @RequestBody ProductRequestDTO dto) {
        log.info("PUT /products/{} called with data: {}", id, dto);
        ProductResponseDTO updated = productService.update(id, dto);
        log.info("Product updated: {}", updated);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("DELETE /products/{} called", id);
        productService.delete(id);
        log.info("Product {} deleted", id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/quantity")
    public ResponseEntity<ProductResponseDTO> updateQuantity(@PathVariable Long id, @RequestParam int quantity) {
        log.info("PATCH /products/{}/quantity called with quantity={}", id, quantity);
        ProductResponseDTO updated = productService.updateQuantity(id, quantity);
        log.info("Product quantity updated: {}", updated);
        return ResponseEntity.ok(updated);
    }
}