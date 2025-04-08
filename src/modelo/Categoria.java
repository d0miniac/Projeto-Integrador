package modelo;

public enum Categoria {
    CAMISA("Camisa"),
    CALÇA("Calça"),
    BLUSA("Blusa"),
    JAQUETA("Jaqueta"),
    VESTIDO("Vestido"),
    SHORTS("Shorts"),
    INTIMA("Íntima");

    private final String descricao;

    Categoria(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Categoria getCategoriaPorDescricao(String categoria) {
        for (Categoria c : Categoria.values()) {
            if (c.getDescricao().equalsIgnoreCase(categoria)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Categoria inválida: " + categoria);
    }
}