package com.sxs;

import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;


public class MsgLoadFrame extends JFrame implements ActionListener {

    public static void main(String []args) {
        MsgLoadFrame f = new MsgLoadFrame();
        f.setVisible(true);
    }

    private static final long serialVersionUID = -1189035634361220261L;
    private static final Logger logger = Logger.getLogger(MsgLoadFrame.class);

    //创建相关的Label标签
    JLabel infilepath_label = new JLabel("导入文件(Excel):");
    JLabel outfilepath_label = new JLabel("导出文件路径:");
    JLabel outlogpath_label = new JLabel("过程日志路径:");    
    //创建相关的文本域
    JTextField infilepath_textfield = new JTextField(20);
    JTextField outfilepath_textfield = new JTextField(20);
    JTextField outlogpath_textfield = new JTextField(20);
    //创建滚动条以及输出文本域
    JScrollPane jscrollPane;
    JTextArea outtext_textarea = new JTextArea();
    //创建按钮
    JButton infilepath_button = new JButton("...");
    JButton outfilepath_button = new JButton("...");
    JButton outlogpath_button = new JButton("..."); 
    JButton start_button = new JButton("开始");

    @Override
    public void setVisible(boolean b) {
        JFrame mainframe = new JFrame("标题ver-1.0");
        // Setting the width and height of frame
        mainframe.setSize(575, 550);
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setResizable(false);//固定窗体大小

        Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
        Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
        int screenWidth = screenSize.width/2; // 获取屏幕的宽
        int screenHeight = screenSize.height/2; // 获取屏幕的高
        int height = mainframe.getHeight(); //获取窗口高度
        int width = mainframe.getWidth(); //获取窗口宽度
        mainframe.setLocation(screenWidth-width/2, screenHeight-height/2);//将窗口设置到屏幕的中部
        //窗体居中，c是Component类的父窗口
        //mainframe.setLocationRelativeTo(c);
        Image myimage=kit.getImage("resources/logo.png"); //由tool获取图像
        mainframe.setIconImage(myimage);
        // 初始化面板
        this.initPanel(mainframe);
        mainframe.setVisible(true);
    }

     /* 创建面板，这个类似于 HTML 的 div 标签
     * 我们可以创建多个面板并在 JFrame 中指定位置
     * 面板中我们可以添加文本字段，按钮及其他组件。
     */
    public void initPanel(JFrame mainframe){
        JPanel panel = new JPanel();
        panel.setLayout(null);
        //this.panel = new JPanel(new GridLayout(3,2)); //创建3行3列的容器     
        /* 这个方法定义了组件的位置。
         * setBounds(x, y, width, height)
         * x 和 y 指定左上角的新位置，由 width 和 height 指定新的大小。
         */
        infilepath_label.setBounds(10,20,120,25);
        infilepath_textfield.setBounds(120,20,400,25);
        infilepath_button.setBounds(520,20, 30, 25);
        panel.add(infilepath_label);
        panel.add(infilepath_textfield);
        panel.add(infilepath_button);

        outfilepath_label.setBounds(10,50,120,25);
        outfilepath_textfield.setBounds(120,50,400,25);
        outfilepath_button.setBounds(520,50, 30, 25);
        panel.add(outfilepath_label);
        panel.add(outfilepath_textfield);
        panel.add(outfilepath_button);

        outlogpath_label.setBounds(10,80,120,25);
        outlogpath_textfield.setBounds(120,80,400,25);
        outlogpath_button.setBounds(520,80, 30, 25);
        panel.add(outlogpath_label);
        panel.add(outlogpath_textfield);
        panel.add(outlogpath_button);

        start_button.setBounds(10,120,80,25);
        panel.add(start_button);

        outtext_textarea.setEditable(false);
        outtext_textarea.setFont(new Font("标楷体", Font.BOLD, 16));
        jscrollPane = new JScrollPane(outtext_textarea);
        jscrollPane.setBounds(10,170, 550, 330);
        panel.add(jscrollPane);
        //增加动作监听
        infilepath_button.addActionListener(this);
        outfilepath_button.addActionListener(this);
        outlogpath_button.addActionListener(this);
        start_button.addActionListener(this);

        mainframe.add(panel);
    }
    /**
     * 单击动作触发方法
     * @param event 事件
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        // TODO Auto-generated method stub
        System.out.println(event.getActionCommand());
        if(event.getSource() == start_button){
            //确认对话框弹出       
            int result = JOptionPane.showConfirmDialog(null, "是否开始转换?", "确认", JOptionPane.YES_NO_OPTION);//YES_NO_OPTION
            if (result == 1) {//是：0，否：1，取消：2
                return;
            }
            System.out.println(infilepath_textfield.getText());
            if (infilepath_textfield.getText().equals("") || outfilepath_textfield.getText().equals("")
                    || outlogpath_textfield.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "路径不能为空", "提示", JOptionPane.WARNING_MESSAGE);//弹出提示对话框，warning
            }else{
                outtext_textarea.setText("");
                String outlogpath = outlogpath_textfield.getText();
                //设置log4j日志输出格式以及路径
                Layout layout = new PatternLayout("%-d{yyyy-MM-dd HH:mm:ss}  [ %C{1}--%M:%L行 ] - [ %p ]  %m%n");
                Appender appender = null;
                try {
                    appender = new FileAppender(layout,outlogpath + File.separator + "log.log");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }         
                logger.addAppender(appender); 
                System.out.println(outlogpath + File.separator + "log.log");
                logger.debug("报文转换开始"); 
                /*
                 * 调用转换方法
                 */
                logger.debug("报文转换结束"); 
                outtext_textarea.setText("此处放输出信息(非日志)");
                result = JOptionPane.showConfirmDialog(null, "是否打开日志文件?", "确认", 0);//YES_NO_OPTION
                if (result == 0) {//是：0，否：1，取消：2
                    try {
                        @SuppressWarnings("unused")
                        Process process = Runtime.getRuntime().exec("echo  /c notepad "+outlogpath + File.separator + "log.log");//调用cmd方法使用记事本打开文件
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }       
        }else{
            //判断三个选择按钮并对应操作
            if(event.getSource() == infilepath_button) {
                File file = openChoseWindow(JFileChooser.FILES_ONLY);
                if(file == null)
                    return;
                infilepath_textfield.setText(file.getAbsolutePath());
                outfilepath_textfield.setText(file.getParent() + File.separator + "out");
                outlogpath_textfield.setText(file.getParent() + File.separator + "log");
            }else if(event.getSource() == outfilepath_button){
                File file = openChoseWindow(JFileChooser.DIRECTORIES_ONLY);
                if(file == null)
                    return;
                outfilepath_textfield.setText(file.getAbsolutePath() + File.separator + "out");
            }else if(event.getSource() == outlogpath_button){
                File file = openChoseWindow(JFileChooser.DIRECTORIES_ONLY);
                if(file == null)
                    return;
                outlogpath_textfield.setText(file.getAbsolutePath() + File.separator + "log");
            }           
        }
    }
    /**
     * 打开选择文件窗口并返回文件
     * @param type 类型
     * @return 选择文件
     */
    public File openChoseWindow(int type){
        JFileChooser jfc=new JFileChooser();  
        jfc.setFileSelectionMode(type);//选择的文件类型(文件夹or文件)  
        jfc.showDialog(new JLabel(), "选择");
        return jfc.getSelectedFile();
    }

    public void windowClosed(WindowEvent arg0) {         
        System.exit(0);
    } 
    public void windowClosing(WindowEvent arg0) { 
        System.exit(0);
    }
}