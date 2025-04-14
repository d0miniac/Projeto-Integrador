package modelo;

public enum Categoria {
    CAMISA("Camisa"),
    CALÇA("Calça"),
    BLUSA("Blusa"),
    JAQUETA("Jaqueta"),
    VESTIDO("Vestido"),
    SHORTS("Shorts"),
    INTIMA("Íntima");

    private String descricao;

    Categoria(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
                return descricao;
            }

	public static Categoria getCategoriaPorDescricao(String categoria) {
		// TODO Auto-generated method stub
		return null;
	}
        
}