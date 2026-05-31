package model.services; 

import model.DB.DB; 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

public class LoginService {

    

    public boolean validateUser(String nome, String senha) throws SQLException {
        
        String sql = "SELECT senha FROM profissional WHERE nome = ?";
        
        String storedHash = null;

        try (Connection conn = DB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nome);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                
                if (rs.next()) {
                    storedHash = rs.getString("senha");
                } else {
                    return false;
                }
            }
        }
        return BCrypt.checkpw(senha, storedHash);
    }
    
    public boolean autorizarAdmin(String senhaAdminDigitada) throws SQLException {
        
        String sql = "SELECT senha FROM profissional WHERE nome = 'Admin'"; 
        
        try (Connection conn = DB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
             
            if (rs.next()) {
                String hashSalvo = rs.getString("senha");
                return BCrypt.checkpw(senhaAdminDigitada, hashSalvo);
            }
        }
        return false;
    }

    
    public boolean cadastrarProfissional(String nome, String senha, String cargo) throws SQLException {
        String sql = "INSERT INTO profissional (nome, senha, cargo) VALUES (?, ?, ?)";
        String hash = BCrypt.hashpw(senha, BCrypt.gensalt()); 
        
        try (Connection conn = DB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, hash);
            pstmt.setString(3, cargo);
            pstmt.executeUpdate();
            return true;
        }
    }
    
    public boolean alterarSenha(String usuarioAlvo, String novaSenha) throws SQLException {
        String sql = "UPDATE profissional SET senha = ? WHERE nome = ?";
        String novoHash = BCrypt.hashpw(novaSenha, BCrypt.gensalt());
        
        try (Connection conn = DB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, novoHash);
            pstmt.setString(2, usuarioAlvo);
            int linhas = pstmt.executeUpdate();
            return linhas > 0;
        }
    }
    
    public boolean isSistemaVazio() throws SQLException {
        String sql = "SELECT COUNT(*) FROM profissional";
        
        try (Connection conn = DB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
             
            if (rs.next()) {
                return rs.getInt(1) == 0; // Retorna TRUE se for igual a 0
            }
        }
        return false;
    }
}