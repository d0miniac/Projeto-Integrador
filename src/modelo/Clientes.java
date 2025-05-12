package modelo;

import java.io.Serializable;

public class Clientes implements Serializable{
	private static final long serialVersionUID = 1L;

	private int idCliente;
	private String Nome_Clientes;
	private String email;
	private String telefone;
	
	public Clientes(int id, String Nome_Clientes, String email, String telefone) {
		this.idCliente = idCliente;
		this.Nome_Clientes = Nome_Clientes;
		this.email = email;
		this.telefone = telefone;
	}
	public Clientes(String Nome_Clientes, String email, String telefone) {
		this.Nome_Clientes = Nome_Clientes;
		this.email = email;
		this.telefone = telefone;


}
	public int getidCliente() {
		return idCliente;
	}
	public void setidCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public String getNome_Clientes() {
		return Nome_Clientes;
	}
	public void setNome(String Nome_Clientes) {
		this.Nome_Clientes = Nome_Clientes;
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

	
}
