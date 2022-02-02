package org.generation.blogPessoal.repository;

import java.util.List;
import java.util.Optional;

import org.generation.blogPessoal.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	//este método irá buscar o usuário pelo email
	
	public Optional<Usuario> findByUsuario(String usuario);	
	
	//TESTE
	
	public List<Usuario>findAllByNomeContainingIgnoreCase(String nome);

}
