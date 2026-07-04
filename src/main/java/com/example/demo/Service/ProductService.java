package com.example.demo.Service;


import com.example.demo.Dto.ProductRequestDto;
import com.example.demo.Dto.ProductResponseDto;
import com.example.demo.Entity.Product;
import com.example.demo.Exception.ProductNotFoundException;
import com.example.demo.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public ProductResponseDto createProduct(
            ProductRequestDto request) {

        Product product = new Product();

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());
        product.setStock(request.getStock());

        Product savedProduct = productRepository.save(product);

        ProductResponseDto dto = new ProductResponseDto();

        dto.setId(savedProduct.getId());
        dto.setName(savedProduct.getName());
        dto.setPrice(savedProduct.getPrice());
        dto.setCategory(savedProduct.getCategory());
        dto.setStock(savedProduct.getStock());

        return dto;
    }
    public List<ProductResponseDto> searchResult(String name){
        List<Product> products =
                productRepository.findByNameContainingIgnoreCase(name);
        return products.stream().map(product -> {
            ProductResponseDto dto = new ProductResponseDto();

            dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setCategory(product.getCategory());
            dto.setPrice(product.getPrice());
            dto.setStock(product.getStock());

            return dto;

        }).toList();

    }
    public List<ProductResponseDto> getStock(){
        List<Product> products = productRepository.findByStockGreaterThan(0);

        return products.stream().map(product -> {

            ProductResponseDto dto = new ProductResponseDto();

            dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setCategory(product.getCategory());
            dto.setPrice(product.getPrice());
            dto.setStock(product.getStock());

            return dto;

        }).toList();

    }
    public List<ProductResponseDto> getCategoryDetail(String Category){
      List<Product> product= productRepository.findByCategory(Category);
        return product.stream().map(products -> {

            ProductResponseDto dto = new ProductResponseDto();

            dto.setId(products.getId());
            dto.setName(products.getName());
            dto.setCategory(products.getCategory());
            dto.setPrice(products.getPrice());
            dto.setStock(products.getStock());

            return dto;

        }).toList();
     }
    public List<ProductResponseDto> getProduct(){
        List<Product> products = productRepository.findAll();

        return products.stream().map(product -> {

            ProductResponseDto dto =
                    new ProductResponseDto();

            dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setPrice(product.getPrice());
            dto.setCategory(product.getCategory());
            dto.setStock(product.getStock());

            return dto;

        }).toList();

    }
    public ProductResponseDto getByID(Long id){
        Product product= productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("product is not found"));
        ProductResponseDto dto =
                new ProductResponseDto();

        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setCategory(product.getCategory());
        dto.setStock(product.getStock());

        return dto;
    }
    public ProductResponseDto updateProduct(Long id,ProductRequestDto request){
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found"));

        // update entity
//        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());
        product.setStock(request.getStock());

        // save
        Product updatedProduct =
                productRepository.save(product);

        // entity → response dto
        ProductResponseDto response =
                new ProductResponseDto();

        response.setId(updatedProduct.getId());
        response.setName(updatedProduct.getName());
        response.setPrice(updatedProduct.getPrice());
        response.setCategory(updatedProduct.getCategory());
        response.setStock(updatedProduct.getStock());

        return response;

    }
    public String deleteProduct(Long id){

        if(!productRepository.existsById(id)){
            throw new RuntimeException("Product not found");
        }

        productRepository.deleteById(id);

        return "Deleted Successfully";
    }

}
