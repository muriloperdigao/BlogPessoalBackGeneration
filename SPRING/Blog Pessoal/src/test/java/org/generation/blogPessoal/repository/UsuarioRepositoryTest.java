package org.generation.blogPessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.generation.blogPessoal.model.Usuario;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll
	void start(){

		// Metodo para inserção de dados no banco de dados

		usuarioRepository.save(new Usuario(0L, "João da Silva", "joao@email.com.br", "13465278"));
		
		usuarioRepository.save(new Usuario(0L, "Manuela da Silva", "manuela@email.com.br", "13465278"));
		
		usuarioRepository.save(new Usuario(0L, "Adriana da Silva", "adriana@email.com.br", "13465278"));

        usuarioRepository.save(new Usuario(0L, "Paulo Antunes", "paulo@email.com.br", "13465278"));

	}

	@Test
	@DisplayName("Retorna 1 usuario")
	public void deveRetornarUmUsuario() {


		//Metodo para buscar um usuário específico
		
		Optional<Usuario> usuario = usuarioRepository.findByUsuario("joao@email.com.br");


		//Método para verificar se ele foi encontrado ou não
		
		assertTrue(usuario.get().getUsuario().equals("joao@email.com.br"));
	}

	@Test
	@DisplayName("Retorna 3 usuarios")
	public void deveRetornarTresUsuarios() {


		//Método para buscar usuario que contem o nome
		List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Silva");


		//Verifica se foram encontrados 3 usuários com o "Silva" no nome
		assertEquals(3, listaDeUsuarios.size());


		//Método para verificar se o primeiro usuário (0) é o João da Silva
		assertTrue(listaDeUsuarios.get(0).getNome().equals("João da Silva"));
		
		//Método para verificar se o segundo usuário (1) é Manuela da Silva
		assertTrue(listaDeUsuarios.get(1).getNome().equals("Manuela da Silva"));
		
		//idem a cima
		assertTrue(listaDeUsuarios.get(2).getNome().equals("Adriana da Silva"));
		
	}

	@AfterAll
	public void end() {
		usuarioRepository.deleteAll();
	}
	
}