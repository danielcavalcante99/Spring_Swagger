package br.com.product.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.product.entities.Product;
import br.com.product.repositories.ProductRepository;
import br.com.product.services.exceptions.ProductBadRequestException;
import br.com.product.services.exceptions.ProductNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	
	
	/**Método para buscar o produto cadastrado pelo ID.
	 * @author Daniel Cavalcante
	 * @version 0.0.1
	 * @param id Integer - ID do Produto 
	 * @throws ProductNotFoundException Excessão será lançada caso o produto não seja encontrado.
	 * @return Product */
	public Product findById(Integer id) {
		return this.repository.findById(id).orElseThrow(() -> new ProductNotFoundException("Produto não encontrado."));
	}
	
	
	/**Método para buscar todos os produtos cadastrados.
	 * @author Daniel Cavalcante
	 * @version 0.0.1
	 * @return List<Product> */
	public List<Product> findAll() {
		return this.repository.findAll();
	}
	
	
	/**Método para cadastro de novos produtos.
	 * @author Daniel Cavalcante
	 * @version 0.0.1
	 * @param product Product - Objeto Produto 
	 * @throws ProductBadRequestException Excessão será lançada caso tente cadastrar dois produtos com a mesma descrição.
	 * @return Product */
	public Product insert(Product product) {
		try {
			return this.repository.save(product);
		} catch (DataIntegrityViolationException e) {
			throw new ProductBadRequestException(e.getMostSpecificCause().getMessage());
		}
	}

	
	/**Método para atualizar os dados de algum produto cadastrado.
	 * @author Daniel Cavalcante
	 * @version 0.0.1
	 * @param obj Product - Objeto Produto 
	 * @throws ProductNotFoundException Excessão será lançada caso não encontrar o produto para ser atualizado.
	 * @return Product */
	public Product update(Product obj) {
		try {
			Product product = this.findById(obj.getId());
			this.repository.save(obj);
			return product;
		} catch (ProductNotFoundException e) {
			throw new ProductNotFoundException("Não foi possível atualizar os dados do produto, pois não existe.");
		}
	}

	
	/**Método para exclusão do produto pelo ID.
	 * @author Daniel Cavalcante
	 * @version 0.0.1
	 * @param id Integer - ID do Produto 
	 * @throws ProductNotFoundException Excessão será lançada caso não encontrar o produto para ser excluído.
	 * @return Product */
	public Product deleteById(Integer id) {
		try {
			Product product = this.findById(id);
			this.repository.deleteById(id);
			return product;
		} catch (ProductNotFoundException e) {
			throw new ProductNotFoundException("Não foi possível excluir o produto, pois o mesmo não existe.");
		}
	}

}
