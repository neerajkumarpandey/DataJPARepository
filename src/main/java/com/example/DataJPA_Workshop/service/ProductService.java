
package com.example.DataJPA_Workshop.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;

import com.example.DataJPA_Workshop.entity.Product;
import com.example.DataJPA_Workshop.projection.ProductView;
import com.example.DataJPA_Workshop.repository.ProductRepository;

@Service // vs @Component
public class ProductService {

	private final ProductRepository repo2;
	public ProductService(ProductRepository repo2) {
		this.repo2 = repo2;
	}

	
	public List<Product> getAllProducts() {
		return repo2.findAll();
		// Select * from products
	}
	
	public String addProduct(Product product) {
		repo2.save(product);
		// Insert into products (name, price, description) values (?, ?, ?)
		return "Product added successfully";
	}
	
	public String deleteProduct(int id) {
		if(repo2.existsById(id)) { // select count(*) from products where id = ?
			repo2.deleteById(id);
			// Delete from products where id = ?
			return "Product deleted successfully";
		} else {
			return "Product not found";
		}
	}
	
	public String updateProduct(int id, Product updatedProduct) {
		if(repo2.existsById(id)) {
			updatedProduct.setId(id);
			repo2.save(updatedProduct);
			// Update products set name = ?, price = ?, description = ? where id = ?
			return "Product updated successfully";
		} else {
			return "Product not found";
		}
	}
	
	// FindAll with pagination & Sorting
		public List<Product> getProducts(int page, int size) {
			PageRequest pageable = PageRequest.of(page, size, Sort.by("price").descending());
			Page<Product> p = repo2.findAll(pageable);
			System.out.println(p.toString());
			return p.getContent();
		}
		
		public List<ProductView> getProductsByName(String name) {
			return repo2.findByName(name);
			// Select name, price from products where name = ?
		}
}
