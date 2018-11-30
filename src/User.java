/**
 * 用户实体，用来表示某个用户
 */
class User {

    private String name;
    private String nickname;
    private String password;
    private double balance;
    private int id;

    int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getNickname() {
        return nickname;
    }

    void setNickname(String nickname) {
        this.nickname = nickname;
    }

    String getPassword() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }

    double getBalance() {
        return balance;
    }

    void setBalance(double balance) {
        this.balance = balance;
    }

    User(){}
}
