package br.com.product.controllers;

import java.net.URI;
import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.product.entities.Product;
import br.com.product.services.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductControllerTest {
	
	@Autowired
	private Environment env;
	
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ProductService service;
    
    /* Teste: busca de todos os produtos cadastrado.
     * URI: /api/products
     * Status Http esperado: 200*/
	@Test
	void testFindAllProducts200() throws Exception {
	
        String adminUser = this.env.getProperty("admin.user");
        String adminPassword = this.env.getProperty("admin.password");
        String credentialsUserEncode = Base64.getEncoder().encodeToString(adminUser.concat(":").concat(adminPassword).getBytes());
        
        URI uri = new URI("/api/products");

        mockMvc.perform(MockMvcRequestBuilders
                .get(uri)
                .header("Authorization", "Basic " + credentialsUserEncode))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(200));
	}
	
	
    /* Teste: busca pelo ID de um produto que não está cadastrado.
     * URI: /api/products/{id}
     * Status Http esperado: 404*/
	@Test
	void testFindProductById404() throws Exception {
	
      String adminUser = this.env.getProperty("admin.user");
      String adminPassword = this.env.getProperty("admin.password");
      String credentialsUserEncode = Base64.getEncoder().encodeToString(adminUser.concat(":").concat(adminPassword).getBytes());
      
      Integer productID = 55;
      
      URI uri = new URI("/api/products/"+productID);

      mockMvc.perform(MockMvcRequestBuilders
              .get(uri)
              .header("Authorization", "Basic " + credentialsUserEncode))
              .andExpect(MockMvcResultMatchers
                      .status()
                      .is(404));
	}
	
	
    /* Teste: busca pelo ID de um produto que está cadastrado.
     * URI: /api/products/{id}
     * Status Http esperado: 200*/
	@Test
	void testFindProductById200() throws Exception {
	
      String adminUser = this.env.getProperty("admin.user");
      String adminPassword = this.env.getProperty("admin.password");
      String credentialsUserEncode = Base64.getEncoder().encodeToString(adminUser.concat(":").concat(adminPassword).getBytes());
    
      Product product = new Product();
      product.setDescription("Guitarra");
      product.setPrice(50.55);
      
      Product productRegister = this.service.insert(product);
      
      URI uri = new URI("/api/products/"+productRegister.getId());
      
      mockMvc.perform(MockMvcRequestBuilders
              .get(uri)
              .header("Authorization", "Basic " + credentialsUserEncode))
              .andExpect(MockMvcResultMatchers
                      .status()
                      .is(200));
	}
	
	
    /* Teste: cadastrando novo produto.
     * URI: /api/products/insert
     * Status Http esperado: 201*/
	@Test
	void testInsertProduct201() throws Exception {
	
      String adminUser = this.env.getProperty("admin.user");
      String adminPassword = this.env.getProperty("admin.password");
      String credentialsUserEncode = Base64.getEncoder().encodeToString(adminUser.concat(":").concat(adminPassword).getBytes());
      
      URI uri = new URI("/api/products/insert");
      
      Product product = new Product();
      product.setDescription("Violão");
      product.setPrice(31.55);
      
      ObjectMapper mapper = new ObjectMapper();
      // Serialize o objeto – Convertendo o objeto `Product` em uma string JSON
      String jsonString = mapper.writeValueAsString(product);

      mockMvc.perform(MockMvcRequestBuilders
              .post(uri)
              .header("Authorization", "Basic " + credentialsUserEncode)
              .content(jsonString)
              .contentType(MediaType.APPLICATION_JSON_VALUE))
              .andExpect(MockMvcResultMatchers
                      .status()
                      .is(201));
	}
	
	
    /* Teste: cadastrando produto que já está cadastrado.
     * URI: /api/products/insert
     * Status Http esperado: 400*/
	@Test
	void testInsertProduct400() throws Exception {
	
      String adminUser = this.env.getProperty("admin.user");
      String adminPassword = this.env.getProperty("admin.password");
      String credentialsUserEncode = Base64.getEncoder().encodeToString(adminUser.concat(":").concat(adminPassword).getBytes());
      
      URI uri = new URI("/api/products/insert");
      
      Product product = new Product();
      product.setDescription("Violão");
      product.setPrice(31.55);
      
      ObjectMapper mapper = new ObjectMapper();
      // Serialize o objeto – Convertendo o objeto `Product` em uma string JSON
      String jsonString = mapper.writeValueAsString(product);

      mockMvc.perform(MockMvcRequestBuilders
              .post(uri)
              .header("Authorization", "Basic " + credentialsUserEncode)
              .content(jsonString)
              .contentType(MediaType.APPLICATION_JSON_VALUE))
              .andExpect(MockMvcResultMatchers
                      .status()
                      .is(400));
	}
	
	
	
    /* Teste: atualizando dados do produto.
     * URI: /api/products/update
     * Status Http esperado: 201*/
	@Test
	void testUpdateProduct201() throws Exception {
	
      String adminUser = this.env.getProperty("admin.user");
      String adminPassword = this.env.getProperty("admin.password");
      String credentialsUserEncode = Base64.getEncoder().encodeToString(adminUser.concat(":").concat(adminPassword).getBytes());
      
      URI uri = new URI("/api/products/update");

      Product productRegister = this.service.insert(new Product(null,"Baixo",36.55));
    
      productRegister.setDescription("Tambor");
      productRegister.setPrice(55.20);
   
      ObjectMapper mapper = new ObjectMapper();
      // Serialize o objeto – Convertendo o objeto `Product` em uma string JSON
      String jsonString = mapper.writeValueAsString(productRegister);

      mockMvc.perform(MockMvcRequestBuilders
              .put(uri)
              .header("Authorization", "Basic " + credentialsUserEncode)
              .content(jsonString)
              .contentType(MediaType.APPLICATION_JSON_VALUE))
              .andExpect(MockMvcResultMatchers
                      .status()
                      .is(201));
	}
	
	
    /* Teste: atualizando dados do produto que não está cadastrado.
     * URI: /api/products/update
     * Status Http esperado: 404*/
	@Test
	void testUpdateProduct404() throws Exception {
	
      String adminUser = this.env.getProperty("admin.user");
      String adminPassword = this.env.getProperty("admin.password");
      String credentialsUserEncode = Base64.getEncoder().encodeToString(adminUser.concat(":").concat(adminPassword).getBytes());
      
      URI uri = new URI("/api/products/update");

      Product product = new Product();
      product.setId(15);
      product.setDescription("Tambor");
      product.setPrice(55.20);
   
      ObjectMapper mapper = new ObjectMapper();
      // Serialize o objeto – Convertendo o objeto `Product` em uma string JSON
      String jsonString = mapper.writeValueAsString(product);

      mockMvc.perform(MockMvcRequestBuilders
              .put(uri)
              .header("Authorization", "Basic " + credentialsUserEncode)
              .content(jsonString)
              .contentType(MediaType.APPLICATION_JSON_VALUE))
              .andExpect(MockMvcResultMatchers
                      .status()
                      .is(404));
	}
	
	
    /* Teste: excluíndo produto.
     * URI: /api/products/delete/{id}
     * Status Http esperado: 201*/
	@Test
	void testDeleteProduct201() throws Exception {
	
      String adminUser = this.env.getProperty("admin.user");
      String adminPassword = this.env.getProperty("admin.password");
      String credentialsUserEncode = Base64.getEncoder().encodeToString(adminUser.concat(":").concat(adminPassword).getBytes());
      
      Product product = new Product();
      product.setDescription("Caixa de Som");
      product.setPrice(505.55);
   
      Product productRegister = this.service.insert(product);
     
      Integer productID = productRegister.getId();
      URI uri = new URI("/api/products/delete/"+productID);
  
      mockMvc.perform(MockMvcRequestBuilders
              .delete(uri)
              .header("Authorization", "Basic " + credentialsUserEncode))
              .andExpect(MockMvcResultMatchers
                      .status()
                      .is(201));
	}
	
	
    /* Teste: excluíndo produto que não está cadastrado.
     * URI: /api/products/delete/{id}
     * Status Http esperado: 404*/
	@Test
	void testDeleteProduct404() throws Exception {
	
      String adminUser = this.env.getProperty("admin.user");
      String adminPassword = this.env.getProperty("admin.password");
      String credentialsUserEncode = Base64.getEncoder().encodeToString(adminUser.concat(":").concat(adminPassword).getBytes());
     
      Integer productID = 30;
      URI uri = new URI("/api/products/delete/"+productID);
  
      mockMvc.perform(MockMvcRequestBuilders
              .delete(uri)
              .header("Authorization", "Basic " + credentialsUserEncode))
              .andExpect(MockMvcResultMatchers
                      .status()
                      .is(404));
	}
	
	


	


}
