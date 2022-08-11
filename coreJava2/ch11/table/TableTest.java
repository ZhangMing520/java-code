package table;

import java.awt.*;
import java.awt.print.*;

import javax.swing.*;

/**
 * @author zhangming
 * @version 2022-07-19 22:47:09
 * <p>
 * 展示一个简单的表格
 */
public class TableTest {

    public static void main(String[] args) {
        System.out.println(System.getProperty("java.endorsed.dirs"));
        EventQueue.invokeLater(() -> {
            PlanetTableFrame frame = new PlanetTableFrame();
            frame.setTitle("TableTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

class PlanetTableFrame extends JFrame {

    private String[] columnNames = {"Planet", "Radius", "Moons", "Gaseous", "Color"};
    private Object[][] cells = {
            {"Mercury", 2440.0, 0, false, Color.YELLOW},
            {"Venus", 6052.0, 0, false, Color.YELLOW},
            {"Earth", 6378.0, 1, false, Color.BLUE},
            {"Mars", 3397.0, 2, false, Color.RED},
            {"Jupiter", 71492.0, 16, true, Color.ORANGE},
            {"Saturn", 60268.0, 18, true, Color.ORANGE},
            {"Uranus", 25559.0, 17, true, Color.BLUE},
            {"Neptune", 24766.0, 8, true, Color.BLUE},
            {"Pluto", 1137.0, 1, false, Color.BLACK},
    };

    public PlanetTableFrame() {
//        单元格 + 列名数组
        JTable table = new JTable(cells, columnNames);
//        点击列的头，行就会自动排序。再次点击，排序顺序就会反过来
        table.setAutoCreateRowSorter(true);
//        将表格包装到一个 JScrollPane 中来添加滚动条
        add(new JScrollPane(table), BorderLayout.CENTER);
        JButton printButton = new JButton("print");
        printButton.addActionListener(event -> {
            try {
//                对表格进行打印（输出到打印机）
                table.print();
            } catch (SecurityException | PrinterException e) {
                e.printStackTrace();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(printButton);
        add(buttonPanel, BorderLayout.SOUTH);
        pack();
    }
}