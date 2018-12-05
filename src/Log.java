class Log {

    final static int save = 0;//零代表存钱
    final static int draw = 1;//一代表取钱
    private String date;
    private int type;
    private double Amount;
    private double balance;
    private String Description;

    String getDate() {
        return date;
    }

    void setDate(String date) {
        this.date = date;
    }

    int getType() {
        return type;
    }

    void setType(int type) {
        this.type = type;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    double getBalance() {
        return balance;
    }

    void setBalance(double balance) {
        this.balance = balance;
    }

    String getDescription() {
        return Description;
    }

    void setDescription(String description) {
        Description = description;
    }

    Log(){}
}
