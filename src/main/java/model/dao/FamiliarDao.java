package model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.DB.DB;
import model.classes.Familiar;

public class FamiliarDao {
    private Connection con;

    public FamiliarDao(Connection con) {
        this.con = con;
    }

    
    public List<Familiar> listarPorAcolhido(int idAcolhido) {
        List<Familiar> lista = new ArrayList<>();
        String sql = "SELECT f.pk_id_vinculo, f.fk_id_parente, f.parentesco, f.ocupacao, " +
                     "p.nome_completo, p.cpf " +
                     "FROM familiar f " +
                     "INNER JOIN pessoa p ON f.fk_id_parente = p.pk_cod_pessoa " +
                     "WHERE f.fk_id_acolhido = ?";
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idAcolhido);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                Familiar f = new Familiar();
                f.setIdVinculo(rs.getInt("pk_id_vinculo"));
                f.setIdParente(rs.getInt("fk_id_parente"));
                f.setParentesco(rs.getString("parentesco"));
                f.setOcupacao(rs.getString("ocupacao"));
                
                
                f.setNomeParente(rs.getString("nome_completo"));
                f.setCpfParente(rs.getString("cpf"));
                
                lista.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    
    public boolean adicionarVinculo(int idAcolhido, int idParente, String parentesco, String ocupacao) {
        String sql = "INSERT INTO familiar (fk_id_acolhido, fk_id_parente, parentesco, ocupacao) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idAcolhido);
            stmt.setInt(2, idParente);
            stmt.setString(3, parentesco);
            stmt.setString(4, ocupacao);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public boolean removerVinculo(int idVinculo) {
        String sql = "DELETE FROM familiar WHERE pk_id_vinculo = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idVinculo);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Familiar> listarAcolhidosDoParente(int idParente) {
        List<Familiar> lista = new ArrayList<>();
        
        String sql = "SELECT f.pk_id_vinculo, f.fk_id_acolhido, f.parentesco, f.ocupacao, " +
                     "p.nome_completo, p.cpf " +
                     "FROM familiar f " +
                     "INNER JOIN pessoa p ON f.fk_id_acolhido = p.pk_cod_pessoa " +
                     "WHERE f.fk_id_parente = ?";
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idParente);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                Familiar f = new Familiar();
                f.setIdVinculo(rs.getInt("pk_id_vinculo"));
                f.setIdAcolhido(rs.getInt("fk_id_acolhido"));
                f.setParentesco(rs.getString("parentesco"));
                f.setOcupacao(rs.getString("ocupacao"));
                
                
                
                f.setNomeParente(rs.getString("nome_completo")); 
                f.setCpfParente(rs.getString("cpf"));
                
                lista.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}