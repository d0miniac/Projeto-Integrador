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

    // 🧠 Nome do produto gerado dinamicamente a partir do Produto
    public String getNome() {
        if (produto != null) {
            return produto.getNomeVisual(); // usa o método da classe Produto
        }
        return "Produto sem nome";
    }
}