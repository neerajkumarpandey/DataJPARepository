package com.example.DataJPA_Workshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.DataJPA_Workshop.entity.Product;
import com.example.DataJPA_Workshop.projection.ProductView;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	//Derived Queries
	List<ProductView> findByName(String name); // Projection
		// Select * from products where name = ?
		
		List<Product> findByPriceGreaterThan(double price);
		// Select * from products where price > ?
		
		List<Product> findByDescriptionContaining(String keyword);
		// Select * from products where description like %keyword%
		
		List<Product> findByPriceAndName(double price, String name);
		
		//JPQL Queries
		@Query("SELECT p FROM Product p WHERE p.price > :price")  // ?1
		List<Product> findExpensiveProducts(@Param("price") double price);

		//Native Queries
		@Query(value = "SELECT * FROM products WHERE name = :name", nativeQuery = true)
		List<Product> findByNameNative(@Param("name") String name);
	
}
