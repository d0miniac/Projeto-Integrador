package modelo;

public enum Cor {
	VERMELHO("Vermelho"),LARANJA("Laranja"),AZUL("Azul"),ROXO("Roxo"),VERDE("Verde"),AMARELO("Amarelo"),ROSA("Rosa"),BRANCO("Branco"),PRETO("Preto"),CINZA("Cinza"),MARROM("Marrom");
	private String descricao;
	private Cor(String desc) {
		this.descricao = desc;
		
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public static Cor getCorPorDescicao(String cor) {
		switch (cor) {
		case "Vermelho": 
			
			return VERMELHO;
		case "Laranja":
			return LARANJA;
		case "Azul":
			return AZUL;
		case "Roxo":
			return ROXO;
		case "Verde":
			return VERDE;
		case "Amarelo":
			return AMARELO;
		case "Rosa":
			return ROSA;
		case "Branco":
			return BRANCO;
		case "Preto":
			return PRETO;
		case "Cinza":
			return CINZA;
		case "Marrom":
			return MARROM;
		default:
			throw new IllegalArgumentException("Unexpected value: " + cor);
		}
	}

}
