package model.classes;

public class Familiar {
    
    private int idVinculo;
    private int idAcolhido;
    private int idParente;
    
    
    private String nomeParente;
    private String cpfParente;
    
    
    private String parentesco; 
    private String ocupacao;

    public Familiar() {
    }

    public Familiar(int idVinculo, String nomeParente, String parentesco, String ocupacao) {
        this.idVinculo = idVinculo;
        this.nomeParente = nomeParente;
        this.parentesco = parentesco;
        this.ocupacao = ocupacao;
    }

    
    public int getIdVinculo() { return idVinculo; }
    public void setIdVinculo(int idVinculo) { this.idVinculo = idVinculo; }

    public int getIdAcolhido() { return idAcolhido; }
    public void setIdAcolhido(int idAcolhido) { this.idAcolhido = idAcolhido; }

    public int getIdParente() { return idParente; }
    public void setIdParente(int idParente) { this.idParente = idParente; }

    public String getNomeParente() { return nomeParente; }
    public void setNomeParente(String nomeParente) { this.nomeParente = nomeParente; }

    public String getCpfParente() { return cpfParente; }
    public void setCpfParente(String cpfParente) { this.cpfParente = cpfParente; }

    public String getParentesco() { return parentesco; }
    public void setParentesco(String parentesco) { this.parentesco = parentesco; }

    public String getOcupacao() { return ocupacao; }
    public void setOcupacao(String ocupacao) { this.ocupacao = ocupacao; }
}