package application;

import java.sql.SQLException;
import java.util.Scanner;

import model.dao.impl.PasswordDaoH2;
import model.dao.impl.UserDaoImpl;
import model.entity.PasswordEntry;
import service.AuthenticatorService;
import service.EmailService;
import service.PasswordGeneratorService;
import service.PasswordLeakService;

public class AppService {
	private final AuthenticatorService authService = new AuthenticatorService();
	private final PasswordGeneratorService passwordService = new PasswordGeneratorService();
	private final PasswordLeakService leakService = new PasswordLeakService();
	private final EmailService emailService = new EmailService();
	private final UserDaoImpl userDao = new UserDaoImpl();
	private final PasswordDaoH2 passwordDaoH2 = new PasswordDaoH2();

	private final Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		try {
			AppService app = new AppService();
			app.run(); 
		} catch (SQLException e) {
			System.err.println("Erro no banco de dados: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("Erro inesperado: " + e.getStackTrace());
			System.err.println("Erro inesperado: " + e.getMessage());

		}
	}

	public void run() throws SQLException {
		System.out.println("🔐 Bem-vindo ao Gerenciador de Senhas Seguras");

		
		// 1. Login
		System.out.print("Digite seu e-mail: ");
		String email = sc.nextLine();
		System.out.print("Digite sua senha: ");
		String senha = sc.nextLine();

		if (userDao.validateUser(email, senha)) {
			System.out.println("❌ Login falhou.");
			return;
		}

		// 2. Enviar token 2FA
		String token = authService.generateToken(email);
		emailService.sendTokenEmail(email, token);
		System.out.println("✔️ Token enviado para seu e-mail.");

		System.out.print("Digite o token: ");
		String tokenInput = sc.nextLine();
		if (!authService.validateToken(email, tokenInput)) {
			System.out.println("❌ Token inválido.");
			return;
		}

		// 3. Menu principal
		int opcao;
		do {
			System.out.println("\n📋 MENU PRINCIPAL");
			System.out.println("1 - Cadastrar nova senha");
			System.out.println("2 - Listar senhas");
			System.out.println("3 - Excluir senha");
			System.out.println("4 - Gerar senha segura");
			System.out.println("5 - Verificar vazamento");
			System.out.println("0 - Sair");
			System.out.print("Escolha uma opção: ");
			opcao = InputUtil(sc);

			switch (opcao) {
			case 1:
				System.out.print("Nome do serviço: ");
				String serviceName = sc.nextLine();
				System.out.print("Username: ");
				String username = sc.nextLine();
				System.out.print("Senha: ");
				String novaSenha = sc.nextLine();
				PasswordEntry pe = new PasswordEntry(serviceName, username, novaSenha);
				passwordDaoH2.save(pe);
				break;
			case 2:
				System.out.println(passwordDaoH2.findAll());
				break;
			case 3:
				System.out.print("ID da senha a excluir: ");
				passwordDaoH2.deleteById(sc.nextInt());
				break;
			case 4:
				System.out.print("Senha de quantos digitos?");
				int length = sc.nextInt();
				String senhaSegura = passwordService.generate(length);
				System.out.println("🔐 Senha segura: " + senhaSegura);
				break;
			case 5:
				System.out.print("Digite a senha para checar: ");
				String senhaVazar = sc.nextLine();
				boolean vazada = leakService.checkLeakPassword(senhaVazar);
				System.out.println(vazada ? "⚠️ Senha vazada!" : "✅ Senha segura.");
				break;
			case 0:
				System.out.println("👋 Saindo...");
				break;
			default:
				System.out.println("❌ Opção inválida.");
			}
		} while (opcao != 0);
	}

	//Método auxiliar
	public static int InputUtil(Scanner sc) {
		while (true) {
			try {
				int value = sc.nextInt();
				sc.nextLine(); 
				return value;
			} catch (Exception e) {
				System.out.print("Entrada inválida. Digite um número inteiro: ");
				sc.nextLine();
			}
		}
	}
}
