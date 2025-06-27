package modelo;

public class ItemVenda {
    private Produto produto;
    private int quantidade;
    private String nome;

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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}