package com.example.demo.Controller;

import com.example.demo.Dto.ProductRequestDto;
import com.example.demo.Dto.ProductResponseDto;
import com.example.demo.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductCrud {


    private final ProductService productService;

    // constructor injection
    public ProductCrud(ProductService productService) {
        this.productService = productService;
    }


    // CREATE
    @PostMapping
    public ProductResponseDto create(@Valid @RequestBody ProductRequestDto productRequestDto) {
        return productService.createProduct(productRequestDto);
    }
    @GetMapping("/category/{id}")
    public List<ProductResponseDto> categoryId(@PathVariable String Category){
        return productService.getCategoryDetail(Category);

    }
    @GetMapping("/in-stock")
    public List<ProductResponseDto> getInStockProduct(){
        return productService.getStock();
    }
    @GetMapping("/search")
    public List<ProductResponseDto> search(@RequestParam String name){
        return productService.searchResult(name);
    }
    // GET ALL
    @GetMapping
    public List<ProductResponseDto> getAll() {
       return productService.getProduct();
    }


    // GET BY ID
    @GetMapping("/{id}")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productService.getByID(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ProductResponseDto update(
            @PathVariable Long id,
           @Valid @RequestBody ProductRequestDto request) {
        return productService.updateProduct(id, request);
    }
    // DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){

       return productService.deleteProduct(id);
    }
}