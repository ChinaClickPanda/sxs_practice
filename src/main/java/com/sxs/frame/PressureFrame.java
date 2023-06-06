package com.sxs.frame;

import com.sxs.frame.ticc.TiccPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class PressureFrame extends JFrame {

    public static void main(String []args) {
        PressureFrame f = new PressureFrame();
        f.setVisible(true);
    }

    @Override
    public void setVisible(boolean b) {
        // 窗体名称
        JFrame mainframe = new JFrame("这是一个标题");
        // 可以扩大窗体
        mainframe.setResizable(true);
        // 设置初始窗口大小
        mainframe.setMinimumSize(new Dimension(575, 550));
        mainframe.setMaximumSize(new Dimension(915, 875));
        // 窗口设置监听
        this.resizeComponent(mainframe);
        // 窗口默认关闭操作
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 窗口位置居中，并且设置logo
        this.setFrameCenter(mainframe);
        // 初始化面板
        TiccPanel ticcPanel = new TiccPanel();
        ticcPanel.initPanel(mainframe);
        // 设置窗口可见
        mainframe.setVisible(true);
    }

    /**
     * 设置工具窗口到屏幕中间，并设置logo
     * @param mainframe 主窗口
     */
    private void setFrameCenter(JFrame mainframe) {
        Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
        Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
        int screenWidth = screenSize.width / 2; // 获取屏幕的宽
        int screenHeight = screenSize.height / 2; // 获取屏幕的高
        int height = mainframe.getHeight(); //获取窗口高度
        int width = mainframe.getWidth(); //获取窗口宽度
        mainframe.setLocation(screenWidth - width / 2, screenHeight - height / 2);//将窗口设置到屏幕的中部
        Image kitImage = kit.getImage("logo.png"); //由tool获取图像
        mainframe.setIconImage(kitImage);
    }

    /**
     * 重置窗口大小
     * @param mainframe 主窗口
     */
    private void resizeComponent(JFrame mainframe) {
        mainframe.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                System.out.println("New size: " + mainframe.getSize());
                // 根据窗口变化，调整组件大小


            }
        });
    }

}
