import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 对底层文件进行操作
 */
public class FileDao {
    private List<User> list;
    private File file;
    private String filename;
    private List<Log> logs;
    private List<String> message = new ArrayList<>();
    /**
     *  初始化用户列表list,从文件中读出用户信息，保存到list中
     * @param filename 文件名
     */
    public FileDao(String filename){
        this.filename = filename;
        list = new ArrayList<User>();
        logs = new ArrayList<>();
        ReadFile();
    }

    /**
     *  读文件，将用户信息从文件中读出
     */
    private void ReadFile(){
        file = new File(this.filename);
        User u ;
        String[] info;
        String all = "";
        try {
            if(!file.exists()){
                try {
                    file.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((all = bufferedReader.readLine()) != null){
                if(all.startsWith("#")){
                    message.add(all);
                    continue;
                }
                if (all.equals(""))
                    continue;
                u = new User();
                info = all.split(" ");
                int i = 0;
                u.setId(Integer.parseInt(info[i++]));
                u.setName(info[i++]);
                u.setNickname(info[i++]);
                u.setPassword(info[i++]);
                u.setBalance(Double.parseDouble(info[i]));
                list.add(u);
            }
            bufferedReader.close();
            fileReader.close();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    /**
     *  保存用户列表，写入文件
     * @param user 用户列表
     * @return boolean 是否保存成功
     */
    public boolean saveInfo(List<User> user){
        file = new File(filename);
        try {
            if(!file.exists()){
                file.createNewFile();
            }
        }catch (IOException ioe){
            ioe.printStackTrace();
            return false;
        }
        try {
            FileWriter writer = new FileWriter(file);
            for (String mess:message){
                writer.write(mess);
                writer.write('\n');
            }
            for (User u:user){
                writer.write(u.getId()+ " "+u.getName()+" "+u.getNickname()+" "+u.getPassword()+" "+u.getBalance());
                writer.write("\n");
                writer.flush();
            }
            writer.close();
            return true;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public void readLog(String filename){
        file = new File(filename);
        if(!file.exists()){
            try {
                file.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String all= "";
            while ((all=bufferedReader.readLine()) != null){
                if (all.equals(""))
                    continue;
                String[] info = all.split(":");
                Log log = new Log();
                log.setDate(info[1]);
                all = bufferedReader.readLine();
                info = all.split(":");
                log.setType(info[1].equals("save")?0:1);
                all = bufferedReader.readLine();
                info = all.split(":");
                log.setAmount(Double.parseDouble(info[1]));
                all = bufferedReader.readLine();
                info = all.split(":");
                log.setBalance(Double.parseDouble(info[1]));
                all = bufferedReader.readLine();
                info = all.split(":");
                log.setDescription(info[1]);
                logs.add(log);
            }
        }catch (IOException e){
            e.printStackTrace();
        }


    }

    /**
     * 将日志写入文件
     * @param logs 日志列表
     * @param filename 文件名
     * @return boolean 是否操作成功
     */
    public boolean writeLog(List<Log> logs,String filename){
            file = new File(filename);
            if(!file.exists()){
                try {
                    file.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                    return false;
                }
            }
            try {
                FileWriter fileWriter = new FileWriter(file,true);
                for (Log log:logs){
                    String type = log.getType() == 1 ? "draw" : "save";
                    fileWriter.write("Date:" + log.getDate() + "\n"
                                         +"Type:" + type + "\n"
                                         +"Amount:" + log.getAmount() + "\n"
                                         +"Balance:" + log.getBalance() + "\n"
                                         + "Description:" + log.getDescription()+ "\n");
                    fileWriter.write("\n");
                    fileWriter.flush();
                }
                fileWriter.close();
                return true;
            }catch (IOException e){
                e.printStackTrace();
                return false;
            }
    }


    public List<Log> getLog(){
        return logs;
    }

    /**
     * 获取用户列表
     * @return list 用户列表
     */
    public List<User> getList(){
        return list;
    }

}
