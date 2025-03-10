package modelo;

public enum Categoria {
	CAMISA("Camisa"),CALÇA("Calça"),BLUSA("Blusa"),JAQUETA("Jaqueta"),VESTIDO("Vestido"),SHORTS("Shorts"),INTIMA("Intíma");
	
	private String descricao;

	
	private Categoria(String desc) {
		this.descricao = desc;
		// TODO Auto-generated constructor stub
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public static Categoria getCategoriaPorDescricao(String categoria) {
		switch (categoria) {
		case "Camisa": 
			return CAMISA;
			
		case "Calça":
			return CALÇA;
			
		case "Blusa":
			return BLUSA;
			
		case "Jaqueta":
			return JAQUETA;
			
		case "Vestido":
			return VESTIDO;
			
		case "Shorts":
			return SHORTS;
			
		case "Intíma":
			return INTIMA;
		
		default:
			throw new IllegalArgumentException("Unexpected value: " + categoria);
		}
		
	}
	
}
