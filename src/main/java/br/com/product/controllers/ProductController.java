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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Produtos")
@RestController
@RequestMapping("/api/products")
@SecurityRequirement(name = "basicScheme")
public class ProductController {

	@Autowired
	private ProductService service;

	@GetMapping
	@Operation(summary = "Consultar todos os produtos")
	@ApiResponse(responseCode = "200", description = "Busca realizada com sucesso",
	content = @Content(schema = @Schema(implementation = Product.class)))
	public ResponseEntity<List<Product>> findAllProducts() {
		List<Product> productList = this.service.findAll();
		return ResponseEntity.ok(productList);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Consultar produto pelo ID")
	@ApiResponse(responseCode = "200", description = "Busca realizada com sucesso",
	content = @Content(schema = @Schema(implementation = Product.class)))
	public ResponseEntity<Product> findProduct(@PathVariable Integer id) {
		Product product = this.service.findById(id);
		return ResponseEntity.ok(product);
	}

	@PutMapping("/update")
	@Operation(summary = "Atualizar dados do produto")
	@ApiResponse(responseCode = "201", description = "Dados do produto atualizado com sucesso",
	content = @Content(schema = @Schema(implementation = Product.class)))
	public ResponseEntity<Product> updateProduct(@RequestBody @Valid Product requestProduct) {
		Product product = this.service.update(requestProduct);
		return ResponseEntity.status(HttpStatus.CREATED).body(product);
	}

	@DeleteMapping("/delete/{id}")
	@Operation(summary = "Exclusão do produto pelo ID")
	@ApiResponse(responseCode = "201", description = "Produto excluído com sucesso",
	content = @Content(schema = @Schema(implementation = Product.class)))
	public ResponseEntity<Product> deleteProduct(@PathVariable Integer id) {
		Product product = this.service.deleteById(id);
		return ResponseEntity.status(HttpStatus.CREATED).body(product);
	}

	@PostMapping("/insert")
	@Operation(summary = "Cadastro de produto")
	@ApiResponse(responseCode = "201", description = "Produto cadastrado com sucesso",
	content = @Content(schema = @Schema(implementation = Product.class)))
	public ResponseEntity<Product> insertProduct(@RequestBody @Valid Product requestProduct) {
		Product product = this.service.insert(requestProduct);
		return ResponseEntity.status(HttpStatus.CREATED).body(product);
	}

}
