package application;

import service.PasswordLeakService;

public class TestLeakChecker {
    public static void main(String[] args) {
        PasswordLeakService leakService = new PasswordLeakService();

        String senhaVazada = "123456"; // VAZADA
        String senhaSegura = "7u$!Tg2@xA&z"; // NÃO VAZADA (ATÉ O MOMENTO)

        System.out.println("Senha 1 vazada? " + leakService.checkLeakPassword(senhaVazada));
        System.out.println("Senha 2 vazada? " + leakService.checkLeakPassword(senhaSegura));
    }
}
