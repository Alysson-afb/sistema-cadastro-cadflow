package model.classes;

import java.time.LocalDate;


public class Acolhido extends Pessoa {

    private String registroCartorio;
    private String medidaProtetiva;
    private String historicoRua;
    private String infoSaude;
    private String servicosAcessados;
    private LocalDate dataEntrada;
    private LocalDate dataDesligamento;
    private String avaliacaoInterdisciplinar;

    private String responsavelAcolhimento;
    private String contatoResponsavel;
    private String residiaCom;
    private String detalhesAcolhimento;
    private String motivoAcolhimento;

    
    private String planoObjetivo;
    private String planoAcao; 
    private String planoResponsaveis;
    private LocalDate planoPrazoInicio;
    private LocalDate planoPrazoFim;
    
    private String observacoes;
    
    public Acolhido() {
        super();
        this.setStatusAcolhido(1); 
    }

    

    public String getRegistroCartorio() { return registroCartorio; }
    public void setRegistroCartorio(String registroCartorio) { this.registroCartorio = registroCartorio; }

    public String getMedidaProtetiva() { return medidaProtetiva; }
    public void setMedidaProtetiva(String medidaProtetiva) { this.medidaProtetiva = medidaProtetiva; }

    public String getHistoricoRua() { return historicoRua; }
    public void setHistoricoRua(String historicoRua) { this.historicoRua = historicoRua; }

    public String getInfoSaude() { return infoSaude; }
    public void setInfoSaude(String infoSaude) { this.infoSaude = infoSaude; }

    public String getServicosAcessados() { return servicosAcessados; }
    public void setServicosAcessados(String servicosAcessados) { this.servicosAcessados = servicosAcessados; }

    public LocalDate getDataEntrada() { return dataEntrada; }
    public void setDataEntrada(LocalDate dataEntrada) { this.dataEntrada = dataEntrada; }

    public LocalDate getDataDesligamento() { return dataDesligamento; }
    public void setDataDesligamento(LocalDate dataDesligamento) { this.dataDesligamento = dataDesligamento; }

    public String getAvaliacaoInterdisciplinar() { return avaliacaoInterdisciplinar; }
    public void setAvaliacaoInterdisciplinar(String avaliacaoInterdisciplinar) { this.avaliacaoInterdisciplinar = avaliacaoInterdisciplinar; }
    
    public String getResponsavelAcolhimento() { return responsavelAcolhimento; }
    public void setResponsavelAcolhimento(String responsavelAcolhimento) { this.responsavelAcolhimento = responsavelAcolhimento; }

    public String getContatoResponsavel() { return contatoResponsavel; }
    public void setContatoResponsavel(String contatoResponsavel) { this.contatoResponsavel = contatoResponsavel; }

    public String getResidiaCom() { return residiaCom; }
    public void setResidiaCom(String residiaCom) { this.residiaCom = residiaCom; }

    public String getDetalhesAcolhimento() { return detalhesAcolhimento; }
    public void setDetalhesAcolhimento(String detalhesAcolhimento) { this.detalhesAcolhimento = detalhesAcolhimento; }

    public String getMotivoAcolhimento() { return motivoAcolhimento; }
    public void setMotivoAcolhimento(String motivoAcolhimento) { this.motivoAcolhimento = motivoAcolhimento; }

    public String getPlanoObjetivo() { return planoObjetivo; }
    public void setPlanoObjetivo(String planoObjetivo) { this.planoObjetivo = planoObjetivo; }

    public String getPlanoAcao() { return planoAcao; }
    public void setPlanoAcao(String planoAcao) { this.planoAcao = planoAcao; }

    public String getPlanoResponsaveis() { return planoResponsaveis; }
    public void setPlanoResponsaveis(String planoResponsaveis) { this.planoResponsaveis = planoResponsaveis; }

    public LocalDate getPlanoPrazoInicio() { return planoPrazoInicio; }
    public void setPlanoPrazoInicio(LocalDate planoPrazoInicio) { this.planoPrazoInicio = planoPrazoInicio; }

    public LocalDate getPlanoPrazoFim() { return planoPrazoFim; }
    public void setPlanoPrazoFim(LocalDate planoPrazoFim) { this.planoPrazoFim = planoPrazoFim; }
    
    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}