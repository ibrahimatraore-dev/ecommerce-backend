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

    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> list(@RequestParam(required = false) String keyword, Pageable pageable) {
        return ResponseEntity.ok(productService.list(keyword, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@RequestBody ProductRequestDTO dto) {
        return new ResponseEntity<>(productService.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id, @RequestBody ProductRequestDTO dto) {
        return ResponseEntity.ok(productService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/quantity")
    public ResponseEntity<ProductResponseDTO> updateQuantity(@PathVariable Long id, @RequestParam int quantity) {
        return ResponseEntity.ok(productService.updateQuantity(id, quantity));
    }
}
