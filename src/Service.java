import com.sun.istack.internal.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 完成用户的各种操作
 */
public class Service {
    private User user;
    private List<User> userList;
    private FileDao fileDao;
    private String filename = "User.txt";
    private List<Log> record;
    private List<Log> history;
    private boolean isLooked;//是否已经查看
    public Service(){
        isLooked = false;
        fileDao = new FileDao(filename);
        userList = fileDao.getList();
        record = new ArrayList<>();
        history = new ArrayList<>();
    }

     public Service(String filename){
        isLooked = false;
        this.filename = filename;
        fileDao = new FileDao(this.filename);
        userList = fileDao.getList();
        record = new ArrayList<>();
        history = new ArrayList<>();
    }

    /**
     * 验证身份
     * @param name 姓名或者昵称
     * @param password 密码
     * @return boolean 登录结果
     */
    public boolean loginin(@NotNull String name, @NotNull String password){
        for (User u:userList){
            if((u.getName().equals(name) || u.getNickname().equals(name)) && u.getPassword().equals(password)){
                user = u;
                userList.remove(u);
                System.out.println("the size is : " + (userList.size()+1));
                return true;
            }
        }
        return false;
    }

    /**
     * 保存用户信息
     * @return boolean 如果写入数据失败，则返回 false.
     */
    public boolean saveInfo(){
        userList.add(user);
        return  fileDao.saveInfo(userList);
    }


    /**
     * 检查密码
     * @param password 需要检查的密码
     * @return boolean
     */
    private boolean checkPassword(String password){
        return password.equals(user.getPassword());
    }

    /**
     * 存钱
     * @param money 用户需要存的钱数.
     * @return -1代表用户存的数额小于0，0代表存钱成功.
     */
    public int saveMoney(double money){
        if (money < 0)
            return -1;
        else{
            user.setBalance(user.getBalance() + money);
            Log log = createLog("save",money);
            record.add(log);
            history.add(log);
            return 0;
        }

    }

    /**
     * 取钱
     * @param money 用户需要取的钱数
     * @return - 1代表用户取的钱大于余额，0代表取钱成功 -2代表钱数小于0
     */
    public int drawMoney(double money){
        double mon = user.getBalance();
        if(mon < money)
            return -1;
        if (money < 0)
            return -2;
        user.setBalance(user.getBalance()-money);
        Log log = createLog("draw",money);
        record.add(log);
        history.add(log);
        return  0;
    }

    /**
     * 修改密码
     * @param passsword 需要修改的密码
     * @return boolean 如果修改后的密码与之前的相同，返回false,否则返回true
     */
    public boolean changePasswd(String passsword){
        if(checkPassword(passsword))
            return false;
        else{
            user.setPassword(passsword);
            return true;
        }
    }


    /**
     * 生成日志格式
     * @param logs 需要被生成的日志
     * @return String 字符串形式的日志
     */
    private String toString(List<Log> logs){
        StringBuilder historyRecord = new StringBuilder();
        String type = "";
        for (Log log : logs) {
            type = log.getType() == 1 ? "draw" : "save";
            StringBuilder onelog = new StringBuilder("Date : " + log.getDate() + "\n"
                                                    + "Type : " + type + "\n"
                                                    + "Amount : " + log.getAmount() + "\n"
                                                    + "Balance : " + log.getBalance() + "\n"
                                                    + "Description : " + log.getDescription() + "\n");
            historyRecord.append(onelog);
            historyRecord.append("\n");
        }
        return historyRecord.toString();
    }

    /**
     * 创建一个日志
     * @param type 操作类型，是取钱还是存钱
     * @param money 钱数
     * @return Log 一个日志实体
     */
    private Log createLog(String type,double money){
        Log log = new Log();
        Date date = new Date();
        log.setDate(date.toString());
        log.setType((type.equals("draw"))?Log.draw:Log.save);
        log.setAmount(money);
        log.setBalance(user.getBalance());
        log.setDescription(type);
        return log;
    }

    /**
     * 判断用户是否了查看历史记录，
     * 如果查看了，不再重新读取文件
     * 如果没有查看，读取文件内容，
     *  并将日志生成一个字符串格式的日志
     * @return String 历史记录的日志
     */
    public String getHistoryLog(){
        if(!isLooked) {
            isLooked = true;
            fileDao.readLog(user.getId() + "-" + user.getName() + ".txt");
            List<Log> oldLogs = fileDao.getLog();
            history.addAll(oldLogs);
        }
        return toString(history);
    }

    /**
     * 获得当前用户进行的操作
     * @return String 登录进行的操作
     */
    public String getCurrentLog(){
        return toString(record);
    }

    /**
     * 保存日志
     * 如果用户操作了，进行写
     * 如果用户没有操作，不写入文件
     * @return boolean 是否写入文件成功
     */
    public boolean saveLog(){
        if(record.size() != 0)
            return fileDao.writeLog(record,user.getId()+"-"+user.getName()+".txt");
        else
            return true;
    }
}
