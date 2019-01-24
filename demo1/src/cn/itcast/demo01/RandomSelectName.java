package cn.itcast.demo01;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

public class RandomSelectName {
    /*定义集合存储名字*/
    String n1 = null;
    String n2 = null;
    String n3 = null;
    ArrayList <String> list1 = new ArrayList <>();
    //初始化的该类时执行构造代码块,io流读取文件中的名字存到集合中;

    BufferedReader bf1;

    {
        try {
            bf1 = new BufferedReader(new FileReader("D:/IdeaProjects/nameOrder/demo1/src/名字.txt"));
            String s = null;
            while ((s = bf1.readLine()) != null) {
                list1.add(s);

                System.out.println("======");
                System.out.println();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bf1.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    ArrayList <String> list2 = new ArrayList <>();
    BufferedReader bf2;

    {
        try {
            bf2 = new BufferedReader(new FileReader("D:/IdeaProjects/nameOrder/demo1/src/题目.txt"));
            String s = null;
            while ((s = bf2.readLine()) != null) {
                list2.add(s);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bf2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //主面板

    JFrame rFrame = new JFrame("随机点名器");
    //用于存储名字的标签

    JLabel name1 = new JLabel();
    JLabel name2 = new JLabel();

    ImageIcon icon = new ImageIcon("images/psu.jpg");

    //将图片放入label中
    JLabel label = new JLabel(icon);

    //设置label的大小


    //按钮

    JButton btn = new JButton("开始点名了");

    JButton btn1 = new JButton("题目解析");
    //采用的是伪随机数，大家也可以不用这个，这个在网上可以找到java随机数的设置

    Random rd = new Random();

    public static void main(String[] args) {
        RandomSelectName rsl = new RandomSelectName();
        rsl.select();
    }

    public void select() {
        //提示标签页面
        JLabel jt = new JLabel("随机点名器");
        //设置标签居中
        jt.setHorizontalAlignment(SwingConstants.CENTER);
        //设置字体大小
        jt.setFont(new Font("随机点名器", 1, 35));
        btn.setFont(new Font("随机点名器", 1, 40));
        btn1.setFont(new Font("题目解析", 1, 40));
        label.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
//        JPanel panel=new JPanel();
//        JTextField jta=new JTextField(10);
        btn.setBackground(Color.white);
        btn1.setBackground(Color.white);

        //设置背景图片
        //设置名字显示的标签居中

        name1.setHorizontalAlignment(SwingConstants.CENTER);
        name2.setHorizontalAlignment(SwingConstants.CENTER);

        //通过匿名类实现Action按钮的监听事件
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                //获取随机的姓名
                n1 = getRandomSelectName();
                n2 = getRandomSelectTitle();
                //设置name标签的文字
                name1.setText(n1);
                //设置字体
                name1.setFont(new Font(n1, 1, 20));
                //设置字体颜色
                name1.setForeground(Color.red);
                name2.setText(n2);
                //设置字体
                name2.setFont(new Font(n2, 2, 20));
                //设置字体颜色
                name2.setForeground(Color.red);
            }
        });
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                name1.setText(n2);
                name1.setFont(new Font(n2, 1, 20));

                char c = n2.charAt(0);
                BufferedReader bf3 = null;
                try {
                    bf3 = new BufferedReader(new FileReader("D:/IdeaProjects/nameOrder/demo1/src/jiexi.properties"));
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }

                Properties pro = new Properties();
                try {
                    pro.load(bf3);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                n3 = pro.getProperty(n2.charAt(0) + "");
                try {
                    jlabelSetText(name2,n3);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                name2.setFont(new Font(n3, 2, 20));
                name2.setForeground(Color.black);
            }
        });
        //获取JFrame的面板
        Container p = this.rFrame.getContentPane();
        //设置布局方式，采用的GridLayout布局
        p.setLayout(new GridLayout(0, 1));
        p.add(jt);
        p.add(name1);
        p.add(name2);
        p.add(btn);
        p.add(btn1);
        //调整大小，这个是java中无法设置标签的大小
        rFrame.pack();
        btn.setPreferredSize(new Dimension(10, 10));
        //设置窗体大小
        rFrame.setSize(800, 400);
        //设置可以显示
        rFrame.setVisible(true);
    }

    //获取随机的姓名
    public String getRandomSelectName() {
        int a;
        //随机数的范围
        a = rd.nextInt(list1.size());
        return list1.get(a);
    }

    public String getRandomSelectTitle() {
        int b;
        //随机数的范围
        b = rd.nextInt(list2.size());
        return list2.get(b);
    }

    void jlabelSetText(JLabel jLabel, String longString) throws InterruptedException {
        StringBuilder builder = new StringBuilder("<html>");
        char[] chars = longString.toCharArray();
        FontMetrics fontMetrics = jLabel.getFontMetrics(jLabel.getFont());
        int start = 0;
        int len = 0;
        while (start + len < longString.length()) {
            while (true) {
                len++;
                if (start + len > longString.length()) {break;}
                if (fontMetrics.charsWidth(chars, start, len) > jLabel.getWidth()) {
                    break;
                }
            }
            builder.append(chars, start, len - 2).append("<br/>");
            start = start + len - 1;
            len = 0;
        }
        builder.append(chars, start, longString.length() - start);
        builder.append("</html>");
        jLabel.setText(builder.toString());
    }

}








