package tableModel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

/**
 * @author zhangming
 * @version 7/19/22 11:11 PM
 * <p>
 * 表格模型。如果你发现自己在将数据装入一个数组中，然后作为一个表格显示出来，那么就应该考虑实现自己的表格模型了。
 */
public class InvestmentTable {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            InvestmentTableFrame frame = new InvestmentTableFrame();
            frame.setTitle("InvestmentTable");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

class InvestmentTableFrame extends JFrame {
    public InvestmentTableFrame() {
        InvestmentTableModel model = new InvestmentTableModel(30, 5, 10);
        JTable table = new JTable(model);
        add(new JScrollPane(table));
        pack();
    }
}

/**
 * This table model computes the cell entries each time they are requested. The table contents
 * shows the growth of an investment for a number of years under different interest rates.
 */
class InvestmentTableModel extends AbstractTableModel {

    private static double INITIAL_BALANCE = 100000.0;

    private int years;
    private int minRate;
    private int maxRate;

    /**
     * @param years   the number of years
     * @param minRate the lowest interest rate to tabulate
     * @param maxRate the highest interest rate to tabulate
     */
    public InvestmentTableModel(int years, int minRate, int maxRate) {
        this.years = years;
        this.minRate = minRate;
        this.maxRate = maxRate;
    }

    @Override
    public int getRowCount() {
        return years;
    }

    @Override
    public int getColumnCount() {
        return maxRate - minRate + 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        double rate = (columnIndex + minRate) / 100.0;
        int nperiods = rowIndex;
        double futureBalance = INITIAL_BALANCE * Math.pow(1 + rate, nperiods);
        return String.format("%.2f", futureBalance);
    }

    @Override
    public String getColumnName(int column) {
        return (column + minRate) + "%";
    }

}
