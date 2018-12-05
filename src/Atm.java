import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SwingConsole{
    public static void run(final JFrame f, final int width, final int height){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                f.setTitle(f.getClass().getSimpleName());
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setSize(width,height);
                f.setLocationRelativeTo(null);
                f.setVisible(true);
            }
        });
    }
}

public class Atm extends JFrame{
    private Service service = new Service("User.txt");//输入你的User文件路径
    private Container container = this.getContentPane();
    private JPanel panel = new JPanel();
    private JTextField jTextField;//用户账号框
    private JPasswordField passwordField;//用户密码框
    private JLabel label1;//用户名
    private JLabel label2;//密码
    private boolean flag = false;//判断是否登录
    private boolean isOperator = false;
    public Atm() {
        panel.setLayout(null);
        JLabel label = new JLabel("ATM !");
        label.setFont(new Font("宋体", Font.BOLD, 20));
        label.setBounds(200, 0, 150, 60);
        panel.add(label);
        label1 = new JLabel("Username : ");
        label1.setBounds(120,60,100,20);
        panel.add(label1);
        jTextField = new JTextField();
        jTextField.setBounds(200,60,100,20);
        panel.add(jTextField);

        label2 = new JLabel("Password : ");
        label2.setBounds(120,90,100,20);
        panel.add(label2);
        passwordField = new JPasswordField();
        passwordField.setBounds(200,90,100,20);
        panel.add(passwordField);

        JButton button = new JButton("Log in");
        button.setBounds(200,120,100,20);
        button.addActionListener(new MyActionListener());
        panel.add(button);

        button = new JButton("Save !");
        button.setForeground(Color.RED);
        button.setBounds(130,200,100,20);
        button.addActionListener(new MyActionListener());
        panel.add(button);

        button = new JButton("Draw !");
        button.setForeground(Color.CYAN);
        button.setBounds(250,200,100,20);
        button.addActionListener(new MyActionListener());
        panel.add(button);

        button = new JButton("History");
        button.setForeground(Color.GREEN);
        button.setBounds(130,250,100,20);
        button.addActionListener(new MyActionListener());
        panel.add(button);

        button = new JButton("Current");
        button.setForeground(Color.BLUE);
        button.setBounds(250,250,100,20);
        button.addActionListener(new MyActionListener());
        panel.add(button);

        button = new JButton("exit");
        button.setBounds(200,300,100,20);
        button.addActionListener(new MyActionListener());
        panel.add(button);
        container.add(panel);
    }

    class MyActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String actionCommand = e.getActionCommand();
            String name = jTextField.getText();
            String password = new String(passwordField.getPassword());
            String money = jTextField.getText();
            if(actionCommand.equals("Log in")){
                if (! flag && service.loginin(name,password)){
                    flag = true;
                    container.removeAll();
                    panel.remove(jTextField);
                    panel.remove(passwordField);
                    panel.remove(label1);
                    panel.remove(label2);

                    label1 = new JLabel("Hello , " + name);
                    label1.setFont(new Font("宋体", Font.BOLD, 15));
                    label1.setBounds(200,60,200,40);
                    panel.add(label1);

                    label2 = new JLabel("Money:");
                    label2.setBounds(150,150,100,20);
                    panel.add(label2);

                    jTextField = new JTextField();
                    jTextField.setBounds(200,150,100,20);
                    panel.add(jTextField);
                    container.add(panel);

                    repaint();
                    validate();
                    invalidate();
                    validate();
                }else
                    JOptionPane.showMessageDialog(null,"[Error] Info error!");
            }
            if(actionCommand.equals("Save !")){
                if(flag){
                    int result = service.saveMoney(Double.parseDouble(money));
                    if(result == 0){
                        isOperator = true;
                        JOptionPane.showMessageDialog(null,"Save Money Success!");
                    }
                    else
                        JOptionPane.showMessageDialog(null,"Money should greater than 0");
                }else
                    JOptionPane.showMessageDialog(null,"Please Login first!");
            }
            if(actionCommand.equals("Draw !")){
                if (flag){
                    int result = service.drawMoney(Double.parseDouble(money));
                    if(result == 0){
                        isOperator = true;
                        JOptionPane.showMessageDialog(null,"Draw Money Success");
                    }
                    if(result == -1)
                        JOptionPane.showMessageDialog(null,"Money should not greater than the you have!");
                    if(result == -2)
                        JOptionPane.showMessageDialog(null,"Money must be greater than 0");
                }
                else
                    JOptionPane.showMessageDialog(null,"Please Login first!");
            }
            if(actionCommand.equals("exit")){
                if(flag){
                    if(!service.saveInfo())
                        JOptionPane.showMessageDialog(null,"Error!");
                    flag = false;
                    if(isOperator && !service.saveLog()){
                        isOperator = false;
                        JOptionPane.showMessageDialog(null,"Log Save Failed!");
                    }
                    container.removeAll();
                    SwingConsole.run(new Atm(),500,500);
                }
            }
            if(actionCommand.equals("History")){
                if(flag){
                    JOptionPane.showMessageDialog(null,service.getHistoryLog());
                }
                else
                    JOptionPane.showMessageDialog(null,"Please Login First!");
            }
            if(actionCommand.equals("Current")){
                if(flag){
                    JOptionPane.showMessageDialog(null,service.getCurrentLog());
                }
                else
                    JOptionPane.showMessageDialog(null,"Please Login First!");
            }

        }
    }

    public static void main(String[] args){
        SwingConsole.run(new Atm(),500,500);
    }
}
