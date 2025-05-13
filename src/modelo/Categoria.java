package modelo;

public enum Categoria {
	CAMISA("Camisa"), CALÇA("Calça"), BLUSA("Blusa"), JAQUETA("Jaqueta"), VESTIDO("Vestido"), SHORTS("Shorts"),
	INTIMA("Íntima");

	private String descricao;

	Categoria(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Categoria getCategoriaPorDescricao(String categoria) {
		if (categoria.equalsIgnoreCase(CAMISA.descricao)) {
			return CAMISA;
		} else if (categoria.equalsIgnoreCase(CALÇA.descricao)) {
			return CALÇA;
		} else if (categoria.equalsIgnoreCase(BLUSA.descricao)) {
			return BLUSA;
		} else if (categoria.equalsIgnoreCase(JAQUETA.descricao)) {
			return JAQUETA;
		} else if (categoria.equalsIgnoreCase(VESTIDO.descricao)) {
			return VESTIDO;
		} else if (categoria.equalsIgnoreCase(SHORTS.descricao)) {
			return SHORTS;
		} else if (categoria.equalsIgnoreCase(INTIMA.descricao)) {
			return INTIMA;
		}
		return null;
	}

}