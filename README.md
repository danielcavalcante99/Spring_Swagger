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


Logo abaixo irei demostrar pequenos trechos para explicar como construir o OpenAPI em código Java, lembrando que o contéudo abaixo já está implementado nesse projeto.


- Dependência do <b>springdoc-openapi</b> no arquivo pom.xml:
~~~
   <dependency>
      <groupId>org.springdoc</groupId>
      <artifactId>springdoc-openapi-ui</artifactId>
      <version>1.6.9</version>
   </dependency>
~~~


<b>Package:</b> br.com.product.config;</br>
<b>Classe:</b> SwaggerConfig;

A classe SwaggerConfig: configuração do bean do objeto OpenAPI.
~~~
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


<b>Package:</b> package br.com.product.controllers;</br>
<b>Classe:</b> ProductController;

Classe ProductController: nesse trecho do código é onde está configurado o nome da tag, as operações que fazem parte dela, definição do conteúdo e do status http de resposta. 
~~~

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
~~~


<b>Package:</b> br.com.product.controllers.exceptions;</br>
<b>Classe:</b> ControllerAdviceHandlersExceptions;

Classe ControllerAdviceHandlersExceptions: nesse trecho do código é onde está configurado o conteúdo e o status http de resposta das requisições que não foram bem sucedidas. 
~~~
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

}
~~~


- @Tag: para especificar o nome da tag.
- @Operation: para especificar o tipo de operação.
- @ApiResponse: para definir o código do status http, a descrição e conteúdo de retorno da requisição.  

##

### Como executar o projeto ?

Primeiramente você precisa ter o Java instalado na sua máquina. </br>Após clonar o projeto na sua máquina local, abra em alguma IDE de sua preferência, irei mostrar um exemplo de como executar esse projeto utilizando o Spring Tool Suite (STS):

![image](https://user-images.githubusercontent.com/74054701/180882314-c520b55c-6514-4a50-97b4-caba1300e814.png)

Com a aplicação rodando localmente você já pode visualizar o OpenAPI, basta digitar o seguinte endereço no navegador:
~~~
http://localhost:8080/swagger-ui/index.html
~~~


