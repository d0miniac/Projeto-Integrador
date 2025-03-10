package modelo;

public enum Tamanho {
	PP("PP"),P("P"),M("M"),G("G"),GG("GG"),XG("XG"),XGG("XGG"),EG("EG"),EGG("EGG") ;
	private String descricao;
	private Tamanho(String desc) {
		this.descricao=desc;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getDescricao() {
		// TODO Auto-generated method stub
		return descricao;
	}
	public static Tamanho getTamanhoPorDescricao(String tamanho) {
		switch (tamanho) {
		case "PP":
			return PP;
		case "P":
			return P;
		case "M":
			return M;
		case "G":
			return G;
		case "GG":
			return GG;
		case "XG":
			return XG;
		case "XGG":
			return XGG;
		case "EG":
			return EG;
		case "EGG":
			return EGG;
		default:
			throw new IllegalArgumentException("Unexpected value: " + tamanho);
		}
	}
}
