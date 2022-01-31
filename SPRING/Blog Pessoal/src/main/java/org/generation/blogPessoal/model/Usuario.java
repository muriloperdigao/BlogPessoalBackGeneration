package org.generation.blogPessoal.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tb_usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O atributo nome é obrigatório")
	private String nome;
	
	@NotBlank(message = "O atributo usuario é obrigatório")
	@Email(message = "O Atributo usuario deve ser um EMAIL")
	private String usuario;
	
	//anotação @Size apenas com valor mínimo pois, quando a senha é
	//criptografada, ganha muitos caracteres
	
	@NotBlank(message = "O atribudo senha é obrigatório")
	@Size(min = 8, message = "A senha deve ter no minimo 8 caracteres")
	private String senha;
	
	private String foto;
	
	//cascade type remove = remove todas as postagens do usuario]
	//seja excluido
	
	@OneToMany(mappedBy= "usuario", cascade = CascadeType.REMOVE)
	private List<Postagem> postagem;

	//getters and setters
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public List<Postagem> getPostagem() {
		return postagem;
	}

	public void setPostagem(List<Postagem> postagem) {
		this.postagem = postagem;
	}
	
	

}
