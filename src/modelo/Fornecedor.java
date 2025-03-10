package modelo;

public class Fornecedor {
    private Long idFornecedor;
    private String email;
    private String nomeFornecedor;
    private String nomeCtt;
    private String telefone;

    public Long getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Long idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }

    public String getNomeCtt() {
        return nomeCtt;
    }

    public void setNomeCtt(String nomeCtt) {
        this.nomeCtt = nomeCtt;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

	@Override
	public String toString() {
		return nomeFornecedor;
	}
    
    
}
