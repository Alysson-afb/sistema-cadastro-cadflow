package model.services;

import java.util.List;
import model.DB.DB;
import model.classes.Acolhido;
import model.classes.Pessoa;
import model.dao.PessoaDao; 

public class PessoaService {
    
    private PessoaDao dao = new PessoaDao(DB.getConnection());
    
    public List<Pessoa> getAll() {
        return dao.getAll();
    }

    
    public boolean inserirAcolhido(Acolhido acolhido) {
        return dao.inserirAcolhido(acolhido);
    }

    
    public boolean inserirPessoa(Pessoa pessoa) {
        return dao.inserirPessoa(pessoa);
    }
    
     public Acolhido buscarPorId(int id) {
        return dao.buscarPorId(id);
    }

    public boolean atualizar(Acolhido acolhido, boolean ehAcolhido) {
        return dao.atualizar(acolhido, ehAcolhido);
    }
    
    public boolean desativar(int id) {
        return dao.desativar(id);
    }

    
    public List<Pessoa> filtrar(int tipo, boolean mostrarInativos) {
        return dao.filtrar(tipo, mostrarInativos);
    }
    
    public java.util.Map<String, Integer> getDadosDashboard() {
        return dao.getDadosDashboard();
    }
    
    public int getCapacidadeMaxima() {
        return dao.getCapacidadeMaxima();
    }
    
    public boolean atualizarCapacidadeMaxima(int novaCapacidade) {
        return dao.atualizarCapacidadeMaxima(novaCapacidade);
    }
}