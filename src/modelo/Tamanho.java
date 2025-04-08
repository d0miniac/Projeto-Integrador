package modelo;

public enum Tamanho {
    PP("PP"),
    P("P"),
    M("M"),
    G("G"),
    GG("GG"),
    XG("XG"),
    XGG("XGG"),
    EG("EG"),
    EGG("EGG");

    private final String descricao;

    Tamanho(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Tamanho getTamanhoPorDescricao(String tamanho) {
        for (Tamanho t : Tamanho.values()) {
            if (t.getDescricao().equalsIgnoreCase(tamanho)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Tamanho inválido: " + tamanho);
    }
}