package model.classes;

import java.time.LocalDate;

public class Pessoa {

    private int idPessoa; 
    
    
    private String nome; 
    private String nomeSocial;
    private String cpf;
    private LocalDate dataNascimento;
    private String sexo;
    private String cor;
    private String nacionalidade;
    private String naturalidade;
    private String estadoCivil;
    private String profissao;
    private String escolaridade;
    
    
    private String telefone;
    private String enderecoAtual;
    private String estadoUF;

    
    private int statusAcolhido; 

    public Pessoa() {
    }

    
    public Pessoa(int idPessoa, String nome, int statusAcolhido) {
        this.idPessoa = idPessoa;
        this.nome = nome;
        this.statusAcolhido = statusAcolhido;
    }

    

    public int getIdPessoa() { return idPessoa; }
    public void setIdPessoa(int idPessoa) { this.idPessoa = idPessoa; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getNomeSocial() { return nomeSocial; }
    public void setNomeSocial(String nomeSocial) { this.nomeSocial = nomeSocial; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }

    public String getNacionalidade() { return nacionalidade; }
    public void setNacionalidade(String nacionalidade) { this.nacionalidade = nacionalidade; }

    public String getNaturalidade() { return naturalidade; }
    public void setNaturalidade(String naturalidade) { this.naturalidade = naturalidade; }

    public String getEstadoCivil() { return estadoCivil; }
    public void setEstadoCivil(String estadoCivil) { this.estadoCivil = estadoCivil; }

    public String getProfissao() { return profissao; }
    public void setProfissao(String profissao) { this.profissao = profissao; }

    public String getEscolaridade() { return escolaridade; }
    public void setEscolaridade(String escolaridade) { this.escolaridade = escolaridade; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEnderecoAtual() { return enderecoAtual; }
    public void setEnderecoAtual(String enderecoAtual) { this.enderecoAtual = enderecoAtual; }

    public String getEstadoUF() { return estadoUF; }
    public void setEstadoUF(String estadoUF) { this.estadoUF = estadoUF; }

    public int getStatusAcolhido() { return statusAcolhido; }
    public void setStatusAcolhido(int statusAcolhido) { this.statusAcolhido = statusAcolhido; }

    @Override
    public String toString() {
        return nome; 
    }
}