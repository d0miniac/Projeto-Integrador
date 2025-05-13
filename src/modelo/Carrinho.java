package modelo;

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

    public double getTotal() {
        return itens.stream()
                .mapToDouble(i -> i.getFoto().getPreco() * i.getQuantidade())
                .sum();
    }
}
