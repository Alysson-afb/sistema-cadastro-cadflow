package model.utils;

import org.mindrot.jbcrypt.BCrypt;


public class GeradorDeHash {

    public static void main(String[] args) {
        
        
        String senhaTextoPuro = "admin123"; 
        
        
        String hashParaSalvar = BCrypt.hashpw(senhaTextoPuro, BCrypt.gensalt());

        
        System.out.println("--- GERADOR DE HASH ---");
        System.out.println("Senha: " + senhaTextoPuro);
        System.out.println("HASH (copie isso para o banco):");
        System.out.println(hashParaSalvar);
        System.out.println("-------------------------");
    }
}