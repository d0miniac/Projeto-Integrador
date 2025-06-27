package modelo;

import java.math.BigDecimal;

public class Produto {

    private Long id;
    private Tamanho tamanho;
    private Categoria categoria;
    private BigDecimal preco;
    private int quantidade;
    private Cor cor;
    private Marca marca;
    private Long fornecedor;
    private String foto;

    // 🔧 Construtor completo (opcional)
    public Produto(Long id, Tamanho tamanho, Categoria categoria, BigDecimal preco, int quantidade, Cor cor, Marca marca, Long fornecedor, String foto) {
        this.id = id;
        this.tamanho = tamanho;
        this.categoria = categoria;
        this.preco = preco;
        this.quantidade = quantidade;
        this.cor = cor;
        this.marca = marca;
        this.fornecedor = fornecedor;
        this.foto = foto;
    }

    // 🔧 Construtor vazio (necessário para set manual)
    public Produto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tamanho getTamanho() {
        return tamanho;
    }

    public void setTamanho(Tamanho tamanho) {
        this.tamanho = tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = Tamanho.getTamanhoPorDescricao(tamanho);
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = Categoria.getCategoriaPorDescricao(categoria);
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Cor getCor() {
        return cor;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
    }

    public void setCor(String cor) {
        this.cor = Cor.getCorPorDescricao(cor);
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public void setMarca(String marca) {
        this.marca = Marca.getMarcaPorDescricao(marca);
    }

    public Long getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Long fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    // 🌟 Nome amigável construído dinamicamente com dados do produto
    public String getNomeVisual() {
        StringBuilder sb = new StringBuilder();
        if (marca != null) sb.append(marca.getDescricao()).append(" ");
        if (categoria != null) sb.append(categoria.getDescricao()).append(" ");
        if (cor != null) sb.append(cor.getDescricao()).append(" ");
        if (tamanho != null) sb.append("(").append(tamanho.getDescricao()).append(")");
        return sb.toString().trim();
    }

    // 👁️ Usado para exibição em listas, logs, e combo boxes
    @Override
    public String toString() {
        return getNomeVisual() + " - R$ " + preco;
    }
}