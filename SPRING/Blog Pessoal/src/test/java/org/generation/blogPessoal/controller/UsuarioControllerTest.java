package org.generation.blogPessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.generation.blogPessoal.model.Usuario;
import org.generation.blogPessoal.service.UsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {
	
	
	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private UsuarioService usuarioService;

	@Test
	@Order(1)
	@DisplayName("Cadastrar Um Usuário")
	public void deveCriarUmUsuario() {


		//Cria um objeto da *Classe Usuario* e insere dentro de um Objeto da *Classe HttpEntity* (Entidade HTTP)

		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(0L, 
			"Paulo Antunes", "paulo_antunes@email.com.br", "13465278"));


		//Na requisição HTTP será enviada a URL do recurso (/usuario/cadastrar), o verbo (POST), a entidade
		//HTTP criada acima (corpoRequisicao) e a Classe de retornos da Resposta (Usuario).
		
		ResponseEntity<Usuario> corpoResposta = testRestTemplate
			.exchange("/usuario/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);


		//Verifica se o http status foi "created". se sim, o teste passa
		
		assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());


		/**
		 * Verifica se o *Nome* do Objeto da Classe Usuario retornado no Corpo da Requisição 
		 * é igual ao Atributo Nome do Objeto da Classe Usuario Retornado no Corpo da Resposta
		 */
		
		//********** POR QUE SERIA DIFERENTE? ***********
		
		assertEquals(corpoRequisicao.getBody().getNome(), corpoResposta.getBody().getNome());


		// idem a cima, porem com usuario
		assertEquals(corpoRequisicao.getBody().getUsuario(), corpoResposta.getBody().getUsuario());
	}
	
	
	@Test
	@Order(2)
	@DisplayName("Não deve permitir duplicação do Usuário")
	public void naoDeveDuplicarUsuario() {


		//criação de um usuario
		
		usuarioService.cadastrarUsuario(new Usuario(0L, 
			"Maria da Silva", "maria_silva@email.com.br", "13465278"));


		//simula um cadastro duplicado
		
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(0L, 
			"Maria da Silva", "maria_silva@email.com.br", "13465278"));


		//Na requisição HTTP será enviada a URL do recurso (/usuario/cadastrar), o verbo (POST), a entidade
		//HTTP criada acima (corpoRequisicao) e a Classe de retornos da Resposta (Usuario).
		
		ResponseEntity<Usuario> corpoResposta = testRestTemplate
			.exchange("/usuario/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);


		//verifica se houve o BAD REQUEST para usuario já cadastrado
		assertEquals(HttpStatus.BAD_REQUEST, corpoResposta.getStatusCode());
	}
	
	@Test
	@Order(3)
	@DisplayName("Atualizar um Usuário")
	public void deveAtualizarUmUsuario() {


		//iserção no banco de dados 
		
		Optional<Usuario> usuarioCadastrado = usuarioService.cadastrarUsuario(new Usuario(0L, 
			"Juliana Andrews", "juliana_andrews@email.com.br", "juliana123"));
		
		//atualização do cadastro 
		
		Usuario usuarioUpdate = new Usuario(usuarioCadastrado.get().getId(), 
			"Juliana Andrews Ramos", "juliana_ramos@email.com.br", "juliana123");
		
		//inserção na classe usuario
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(usuarioUpdate);


		//Envio da entidade pelo método put
		//utilização do basicAuth por não estar logado no usuario
		
		ResponseEntity<Usuario> corpoResposta = testRestTemplate
			.withBasicAuth("root", "root")
			.exchange("/usuario/atualizar", HttpMethod.PUT, corpoRequisicao, Usuario.class);


		//verificação do http status OK
		
		assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());


		//verificação de igualdade de nome
		
		assertEquals(corpoRequisicao.getBody().getNome(), corpoResposta.getBody().getNome());


		//verificação de igualdade de usuario
		
		assertEquals(corpoRequisicao.getBody().getUsuario(), corpoResposta.getBody().getUsuario());
	}

}
