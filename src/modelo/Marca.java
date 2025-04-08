package modelo;

public enum Marca {

    NIKE("Nike"),
    ADIDAS("Adidas"),
    PUMA("Puma");

    private final String descricao;

    Marca(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Marca getMarcaPorDescricao(String marca) {
        for (Marca m : Marca.values()) {
            if (m.getDescricao().equalsIgnoreCase(marca)) {
                return m;
            }
        }
        throw new IllegalArgumentException("Marca inválida: " + marca);
    }
}