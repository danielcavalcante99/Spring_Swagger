### Construa documentação da sua API Rest com OpenAPI em código Java

Projeto: nesse projeto iremos aprender a utilizar a bliblioteca <b>springdoc-openapi</b> na linguagem java que irá ajudar a automatizar a geração da documentação da API.</br>

link springdoc-openapi: https://springdoc.org/

![image](https://user-images.githubusercontent.com/74054701/180668464-af1a7ad9-c583-4dc4-a7b7-cb7635c4d1f1.png)

![image](https://user-images.githubusercontent.com/74054701/180669260-c398d6cf-fa0c-45c8-82fc-c2cfe04238a2.png)

#
### O que é OAS (OpenAPI Specification)?

A OpenAPI Specification (OAS) define uma interface padrão independente de linguagem para APIs RESTful que permite que humanos e computadores descubram 
e compreendam os recursos do serviço. O "OpenAPI Specification" terá uma especificação de como você pode modelar APIs, como você 
pode documentar o design de uma API e etc.


### O que é o OpenAPI?

Documento que define ou descreve uma API, ou seja, é o documento que descreve para as pessoas e máquinas como funciona e como usar sua API.

link: https://swagger.io/specification/
##

### O que é o Swagger?

O Swagger é um conjunto de ferramentas e ele trabalha em cima de uma especificação, que é a OAS (OpenAPI Specification).</br>
O Swagger nos fornece três ferramentas "Open Source", ou seja, ferramentas gratuitas e de código aberto: 
- <b>Swagger Editor:</b> editor que renderiza visualmente a documentação do OpenAPI e fornece feedback de erros em tempo real;
- <b>Swagger UI:</b> basicamente fornece um ambiente visual da documentação do OpenAPI que criamos com o Swagger Editor;
- <b>Swagger Codegen:</b> simplifica seu processo de compilação gerando stubs de servidor e SDKs de cliente para qualquer API, definida com a especificação OpenAPI.

link: https://swagger.io/tools/open-source/

##

### Construção do OpenAPI em código Java

- Clone o projeto em sua máquina local para facilitar seu entendimento:</br>
git clone https://github.com/danielcavalcante99/Spring_Swagger.git

- Dependência do <b>springdoc-openapi</b> no arquivo pom.xml:
~~~
   <dependency>
      <groupId>org.springdoc</groupId>
      <artifactId>springdoc-openapi-ui</artifactId>
      <version>1.6.9</version>
   </dependency>
~~~
##

<b>Package:</b> br.com.product.config;</br>
<b>Classe:</b> SwaggerConfig;

A classe SwaggerConfig é aquela que configura o bean do objeto OpenAPI.
~~~
package br.com.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
	
  @Bean
  public OpenAPI OpenAPI() {
    	
		return new OpenAPI()
                .components(new Components()
                .addSecuritySchemes("basicScheme",  new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
                .info(new Info()
                .version("0.0.1")		
				.title(String.format("Documentation API"))
				.description( "Essa API foi desenvolvida para realizar cadastro, atualização, exclusão e consulta referente aos produtos.")
				.contact(new Contact()
						.name("Developer Daniel Cavalcante")
						.email("daniel16henrrique@gmail.com")));
	}
                
}
~~~
##

<b>Package:</b> package br.com.product.controllers;</br>
<b>Classe:</b> ProductController;

A classe ProductController é onde está definido o nome da tag, operações que fazem parte dela e o corpo da resposta.
~~~
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
~~~
##

<b>Package:</b> br.com.product.controllers.exceptions;</br>
<b>Classe:</b> ControllerAdviceHandlersExceptions;

A classe ControllerAdviceHandlersExceptions é onde foi definido o status http, descrição e corpo da resposta quando a requisição não é bem sucedida.
~~~
package br.com.product.controllers.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.product.services.exceptions.ProductBadRequestException;
import br.com.product.services.exceptions.ProductNotFoundException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestControllerAdvice
public class ControllerAdviceHandlersExceptions {
	
	@ExceptionHandler(ProductNotFoundException.class)
	@ApiResponse(responseCode = "404", description = "Produto não encontrado",
	content = @Content(schema = @Schema(implementation =  StandardError.class)))
	public ResponseEntity<StandardError> resourceNotFound(ProductNotFoundException e, HttpServletRequest request) {
		
		StandardError standardError = new StandardError();
		standardError.setTitleError("Not Found");
		standardError.setStatusHttp(HttpStatus.NOT_FOUND.value());
		standardError.setMessage(e.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
	}
	
	
	@ExceptionHandler(ProductBadRequestException.class)
	@ApiResponse(responseCode = "400", description = "Requisição inválida",
	content = @Content(schema = @Schema(implementation =  StandardError.class)))
	public ResponseEntity<StandardError> resourceBadRequest(ProductBadRequestException e, HttpServletRequest request) {
		
		StandardError standardError = new StandardError();
		standardError.setTitleError("Bad Request");
		standardError.setStatusHttp(HttpStatus.BAD_REQUEST.value());
		standardError.setMessage(e.getMessage());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ApiResponse(responseCode = "400", description = "Requisição inválida",
	content = @Content(schema = @Schema(implementation =  StandardError.class)))
	public ResponseEntity<StandardError> resourceBadRequest(MethodArgumentNotValidException e, HttpServletRequest request) {

		StandardError standardError = new StandardError();
		standardError.setTitleError("Bad Request");
		standardError.setStatusHttp(HttpStatus.BAD_REQUEST.value());
		standardError.setMessage(e.getFieldError().getField().concat(" ").concat(e.getFieldError().getDefaultMessage()));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
	}
}
~~~
  
- @Tag: para especificar o nome da tag.
- @Operation: para especificar o tipo de operação.
- @ApiResponse: para especificar o status http, response body e a descrição.  
