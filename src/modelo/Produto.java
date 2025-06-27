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
    private String nome;
    
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

	
   }


	