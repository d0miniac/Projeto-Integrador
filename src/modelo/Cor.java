package modelo;

public enum Cor {
    VERMELHO("Vermelho"),
    LARANJA("Laranja"),
    AZUL("Azul"),
    ROXO("Roxo"),
    VERDE("Verde"),
    AMARELO("Amarelo"),
    ROSA("Rosa"),
    BRANCO("Branco"),
    PRETO("Preto"),
    CINZA("Cinza"),
    MARROM("Marrom");

    private final String descricao;

    Cor(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Cor getCorPorDescricao(String cor) {
        for (Cor c : Cor.values()) {
            if (c.getDescricao().equalsIgnoreCase(cor)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Cor inválida: " + cor);
    }
}