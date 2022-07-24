package br.com.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.product.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

}
