package br.com.product.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.product.entities.Product;
import br.com.product.services.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductService service;

	@GetMapping
	public ResponseEntity<List<Product>> findAllProducts() {
		List<Product> productList = this.service.findAll();
		return ResponseEntity.ok(productList);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> findProduct(@PathVariable Integer id) {
		Product product = this.service.findById(id);
		return ResponseEntity.ok(product);
	}

	@PutMapping("/update")
	public ResponseEntity<Product> updateProduct(@RequestBody @Valid Product requestProduct) {
		Product product = this.service.update(requestProduct);
		return ResponseEntity.status(HttpStatus.CREATED).body(product);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable Integer id) {
		Product product = this.service.deleteById(id);
		return ResponseEntity.status(HttpStatus.CREATED).body(product);
	}

	@PostMapping("/insert")
	public ResponseEntity<Product> findProduct(@RequestBody @Valid Product requestProduct) {
		Product product = this.service.insert(requestProduct);
		return ResponseEntity.status(HttpStatus.CREATED).body(product);
	}

}
