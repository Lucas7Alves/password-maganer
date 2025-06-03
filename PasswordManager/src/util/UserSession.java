package util;

/**
 * Classe Singleton responsável por armazenar informações da sessão do usuário logado.
 */
public class UserSession {
    private static volatile UserSession instance;
    private String userId;
    private String userEmail;

    /**
     * Construtor privado para garantir padrão Singleton.
     */
    private UserSession() {}

    /**
     * Retorna a instância única da sessão do usuário.
     * @return instância de UserSession
     */
    public static UserSession getInstance() {
        if (instance == null) {
            synchronized (UserSession.class) {
                if (instance == null) {
                    instance = new UserSession();
                }
            }
        }
        return instance;
    }

    /**
     * Inicializa a sessão do usuário com ID e e-mail.
     * @param userId ID do usuário
     * @param userEmail E-mail do usuário
     */
    public void initSession(String userId, String userEmail) {
        SecurityUtils.validateRequiredFields(userId, userEmail);
        SecurityUtils.validateEmail(userEmail);
        
        this.userId = userId;
        this.userEmail = userEmail;
    }

    /**
     * Retorna o ID do usuário atual.
     * @return ID do usuário
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Retorna o e-mail do usuário atual.
     * @return E-mail do usuário
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * Limpa os dados da sessão atual.
     */
    public void clearSession() {
        this.userId = null;
        this.userEmail = null;
    }

    /**
     * Verifica se os dados da sessão atual são válidos.
     * @return true se válidos, false caso contrário
     */
    public boolean isValid() {
        try {
            SecurityUtils.validateRequiredFields(userId, userEmail);
            SecurityUtils.validateEmail(userEmail);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
