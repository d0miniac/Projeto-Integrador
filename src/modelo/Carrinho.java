package modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Carrinho {
    private static Carrinho instancia;
    private List<ItemVenda> itens;

    private Carrinho() {
        itens = new ArrayList<>();
    }

    public static Carrinho getInstancia() {
        if (instancia == null) {
            instancia = new Carrinho();
        }
        return instancia;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }

    public void adicionarItem(ItemVenda item) {
        itens.add(item);
    }

    public void removerItem(ItemVenda item) {
        itens.remove(item);
    }

    public void limpar() {
        itens.clear();
    }

    // Método corrigido para usar BigDecimal
    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemVenda item : itens) {
            Produto produto = item.getFoto(); // ou item.getProduto()
            if (produto != null && produto.getPreco() != null) {
                BigDecimal preco = produto.getPreco()
                        .multiply(BigDecimal.valueOf(item.getQuantidade()));
                total = total.add(preco);
            }
        }
        return total;
    }

    public List<Produto> getProdutos() {
        List<Produto> produtos = new ArrayList<>();
        for (ItemVenda item : itens) {
            produtos.add(item.getFoto()); // ou item.getProduto()
        }
        return produtos;
    }
}