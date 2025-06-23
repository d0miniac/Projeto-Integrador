package modelo;

public class ItemVenda {
    private Produto produto;
    private int quantidade;

    public Produto getFoto() {
        return produto;
    }

    public void setFoto(Produto produto) {
        this.produto = produto;
    }

    public Long getIdProduto() {
        return produto.getId();
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getQuantidade() {
        return quantidade;
    }

	public String getNome() {

		return null;
	}
}
