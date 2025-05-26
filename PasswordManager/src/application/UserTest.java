package application;

import java.sql.SQLException;

import model.dao.impl.PasswordDaoH2;
import model.dao.impl.UserDaoImpl;
import model.entity.User;
import util.DB;

public class UserTest {

	public static void main(String[] args) {
		
		UserDaoImpl ud = new UserDaoImpl();
		PasswordDaoH2 pd = new PasswordDaoH2();
		
		try {
			DB.getConnection();
			DB.createSchema();
			
			
			System.out.println(ud.findAll());
            
			/*
			// --- TESTE DE CADASTRO ---
            User newUser = new User("lucas", "senha123", "lucassalvess0909@gmail.com");
            ud.registerUser(newUser);
            System.out.println("Usuário cadastrado com sucesso!");

            // --- TESTE DE LOGIN ---
            boolean loginSuccess = ud.validateUser("alice", "senha123");
            System.out.println(loginSuccess ? "✅ Login válido!" : "❌ Login inválido!");

            // --- TESTE DE SENHA INCORRETA ---
            boolean loginFail = ud.validateUser("alice", "senha_errada");
            System.out.println(loginFail ? "✅ Login válido (ERRO!)" : "❌ Login inválido (CORRETO!)");
			*/
        } catch (SQLException e) {
            System.err.println("Erro no banco de dados: " + e.getMessage());
        } finally {
			DB.closeConnection();
		}
    }

}
