package application;

import java.util.Scanner;

import service.AuthenticatorService;
import service.EmailService;

public class Auth2FA {
    public static void main(String[] args) throws Exception {
        AuthenticatorService authService = new AuthenticatorService();
        EmailService emailService = new EmailService();

        String email = "leon.sport55@gmail.com";

        String token = authService.generateToken(email);
        emailService.sendTokenEmail(email, token);

        Scanner sc = new Scanner(System.in);
        System.out.print("Digite o código 2FA recebido: ");
        String userInput = sc.nextLine();

        if (authService.validateToken(email, userInput)) {
            System.out.println("✅ Acesso liberado!");
        } else {
            System.out.println("❌ Código inválido ou expirado.");
        }
    }
}