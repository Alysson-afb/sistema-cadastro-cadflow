package model.services;

import java.util.List;
import model.DB.DB;
import model.classes.Pessoa;
import model.dao.PessoaDao; 
import model.dao.PrincipalDao;

public class PrincipalService {
    
    private PrincipalDao dao = new PrincipalDao(DB.getConnection());
    public List<Pessoa> getAll() {
        return dao.getAll();
    }
}