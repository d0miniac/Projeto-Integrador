package modelo;

import java.io.Serializable;

public class Clientes implements Serializable{
	private static final long serialVersionUID = 1L;

	private String nome;
	private String email;
	private String telefone;
	
	public Clientes(int i, String string, String string2, String string3) {
		
	}
	public Clientes(String nome, String email, String telefone) {
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;

	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	//public static Clientes get(int linhaSelecionada) {
		// TODO Auto-generated method stub
	//	return null;
	//}
	public void setId(int int1) {
		// TODO Auto-generated method stub
		
	}
	public int getId() {
		// TODO Auto-generated method stub
		return (Integer) null;
	}
	
}
