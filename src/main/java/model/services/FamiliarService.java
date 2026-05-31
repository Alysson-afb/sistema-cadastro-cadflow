package model.services;

import java.util.List;
import model.DB.DB;
import model.classes.Familiar;
import model.dao.FamiliarDao;

public class FamiliarService {
    private FamiliarDao dao = new FamiliarDao(DB.getConnection());

    public List<Familiar> listarPorAcolhido(int idAcolhido) {
        return dao.listarPorAcolhido(idAcolhido);
    }

    public boolean adicionarVinculo(int idAcolhido, int idParente, String parentesco, String ocupacao) {
        
        if (idAcolhido == idParente) return false;
        return dao.adicionarVinculo(idAcolhido, idParente, parentesco, ocupacao);
    }

    public boolean removerVinculo(int idVinculo) {
        return dao.removerVinculo(idVinculo);
    }
    
    public List<Familiar> listarAcolhidosDoParente(int idParente) {
        return dao.listarAcolhidosDoParente(idParente);
    }
}