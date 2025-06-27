package modelo;

public class Venda {

    private Long id;
    private String data;
    private String horario;
    private Long idFuncionario;
    private Float total;

    // 🔧 Construtor vazio
    public Venda() {
    }

    // 🔧 Construtor completo (opcional)
    public Venda(Long id, String data, String horario, Long idFuncionario, Float total) {
        this.id = id;
        this.data = data;
        this.horario = horario;
        this.idFuncionario = idFuncionario;
        this.total = total;
    }

    // ✅ Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Long getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Long idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    // 👁️ Útil para logs ou tabelas
    @Override
    public String toString() {
        return "Venda #" + id + " | " + data + " " + horario + " | Total: R$ " + String.format("%.2f", total);
    }
}