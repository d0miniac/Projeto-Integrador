package modelo;

public class ItemVenda {
	public Long id;
	public Float preco;
	public String nome;
	public int quantidade;
	public Produto foto;
	
	public Float getPreco() {
		return preco;
	}
	public void setPreco(Float preco) {
		this.preco = preco;
	}
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
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public Produto getFoto() {
		return foto;
	}
	public void setFoto1(Produto produto) {
		this.foto = produto;
	}
	public Long getIdProduto() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setFoto(Produto produto) {
		// TODO Auto-generated method stub
		
	}
	public void setFoto(String string) {
		// TODO Auto-generated method stub
		
	}
}
