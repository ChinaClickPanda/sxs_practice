package com.sxs.frame.ticc;

import com.sxs.MsgLoadFrame;
import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class TiccPanel implements ActionListener {

    private static final Logger logger = Logger.getLogger(MsgLoadFrame.class);

    //创建相关的Label标签
    JLabel requestUrl = new JLabel("请求地址:");
    JLabel inputOne = new JLabel("输入框-1:");
    JLabel inputTwo = new JLabel("输入框-2:");
    //创建相关的文本域
    JTextField infilepath_textfield = new JTextField(20);
    JTextField outfilepath_textfield = new JTextField(20);
    JTextField outlogpath_textfield = new JTextField(20);
    //创建滚动条以及输出文本域
    JScrollPane jscrollPane;
    JTextArea outtext_textarea = new JTextArea();
    //创建按钮
    JButton queryButton = new JButton("查询");
    JButton addButton = new JButton("添加");
    JButton deleteButton = new JButton("删除");

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
        requestUrl.setBounds(10,20,120,25);
        infilepath_textfield.setBounds(120,20,430,25);
        panel.add(requestUrl);
        panel.add(infilepath_textfield);

        inputOne.setBounds(10,50,120,25);
        outfilepath_textfield.setBounds(120,50,430,25);

        panel.add(inputOne);
        panel.add(outfilepath_textfield);

        inputTwo.setBounds(10,80,120,25);
        outlogpath_textfield.setBounds(120,80,430,25);
        panel.add(inputTwo);
        panel.add(outlogpath_textfield);

        this.bindButton(panel);

        outtext_textarea.setEditable(false);
        outtext_textarea.setFont(new Font("标楷体", Font.BOLD, 16));
        jscrollPane = new JScrollPane(outtext_textarea);
        jscrollPane.setBounds(10,170, 550, 330);
        panel.add(jscrollPane);
        // 增加动作监听

        mainframe.add(panel);
    }

    /**
     * 绑定按钮
     * @param panel 画板
     */
    private void bindButton(JPanel panel) {
        addButton.setBounds(10,120,80,25);
        panel.add(addButton);
        addButton.addActionListener(this);
    }

    /**
     * 单击动作触发方法
     * @param event 事件
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        // TODO Auto-generated method stub
        System.out.println(event.getActionCommand());
        if(event.getSource() == addButton){
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
        }
    }

}
