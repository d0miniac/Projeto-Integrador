package modelo;

import java.util.ArrayList;

public class Carrinho {
	public ArrayList<ItemVenda> itens = new ArrayList<>();

	private Carrinho() {
	}

	private static Carrinho unicaInstancia = null;

	public static Carrinho getInstancia() {
		if (unicaInstancia == null) {
			unicaInstancia = new Carrinho();
		}
		return unicaInstancia;
	}

	public void adicionar(ItemVenda iv) {

		itens.add(iv);

	}

	public void remover(Long id) {
		itens.removeIf(item -> item.getId() == id);

	}

	public boolean verificar(Long id) {
		int x = 0;
		for (ItemVenda itemVenda : itens) {
			if (itemVenda.getId() == id) {
				x = 1;
			}
		}
		if (x == 0) {
			return false;
		} else {
			return true;
		}
	}

	public ArrayList<ItemVenda> getItens() {
		return itens;
	}

	public void setItens(ArrayList<ItemVenda> itens) {
		this.itens = itens;
	}

}
