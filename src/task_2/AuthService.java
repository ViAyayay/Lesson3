package task_2;

public interface AuthService {
    String changeNick(String nick, String newNick);
    void start();
    String getNickByLoginPass(String login, String pass);
    void stop();
}
