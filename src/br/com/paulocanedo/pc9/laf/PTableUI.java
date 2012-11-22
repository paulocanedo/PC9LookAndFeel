/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.paulocanedo.pc9.laf;

import br.com.paulocanedo.pc9.PSimpleBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import javax.swing.BorderFactory;
import javax.swing.CellRendererPane;
import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.RowSorter;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.LabelUI;
import javax.swing.plaf.basic.BasicTableUI;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

/**
 *
 * @author paulocanedo
 */
public class PTableUI extends BasicTableUI {

    public static ComponentUI createUI(JComponent c) {
        return new PTableUI();
    }

    public PTableUI() {
    }

    @Override
    protected void installDefaults() {
        super.installDefaults();
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);

        table.remove(rendererPane);

        table.add(rendererPane = new PCellRendererPane(table));

        JTableHeader tableHeader = table.getTableHeader();
        if (tableHeader != null) {
            tableHeader.setDefaultRenderer(new PTableHeaderRenderer(table));
        }
        table.setShowHorizontalLines(false);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Container viewport = table.getParent();
        if (viewport instanceof JViewport) {
            Container parent = viewport.getParent();
            if (parent instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane) parent;
                Component corner = scrollPane.getCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER);
                if (corner == null) {
                    JLabel cornerLabel = new JLabel();
                    cornerLabel.setUI(PTableUI.createHeaderLabelUI());

                    scrollPane.setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER, cornerLabel);
                }
            }
        }

        super.paint(g, c);
    }

    @Override
    protected void uninstallDefaults() {
        super.uninstallDefaults();
    }

    @Override
    public void uninstallUI(JComponent c) {
        super.uninstallUI(c);
    }

    public static LabelUI createHeaderLabelUI() {
        return new PLabelUI() {

            @Override
            public void installUI(JComponent c) {
                super.installUI(c);

                c.setOpaque(true);
            }

            @Override
            public void paint(Graphics g, JComponent c) {
                if (c.isOpaque()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    GradientPaint paint = new GradientPaint(0, 0, new Color(0xdbdbdb), 0, c.getHeight(), new Color(0xbbbbbb));
                    g2.setPaint(paint);

                    g2.fillRect(0, 0, c.getWidth(), c.getHeight());
                    g2.dispose();
                }
                super.paint(g, c);
            }
        };
    }

    private class PCellRendererPane extends CellRendererPane {

        private JTable table;
        private Color stripedColor = new Color(0xF1F5FA);

        public PCellRendererPane(JTable table) {
            this.table = table;
        }

        @Override
        public void paintComponent(Graphics g, Component c, Container p, int x, int y, int w, int h, boolean shouldValidate) {
            Color background = c.getBackground();

            int row = table.rowAtPoint(new Point(x, y));
            boolean isSelected = table.isRowSelected(row);

            if (isSelected) {
                c.setBackground(UIManager.getColor("List.selectionBackground"));
                c.setForeground(UIManager.getColor("List.selectionForeground"));
            } else {
                Color color = (row % 2 == 0 ? stripedColor : background);
                c.setBackground(color);
            }
            super.paintComponent(g, c, p, x, y, w, h, shouldValidate);

            c.setForeground(table.getForeground());
            c.setBackground(table.getBackground());
        }
    }

    private class PTableHeaderRenderer extends JLabel implements TableCellRenderer {

        private Border bottomBorder;
        private Border rightBorder;
        private Border border;
        private LabelUI labelUI = createHeaderLabelUI();

        public PTableHeaderRenderer(JTable table) {
            bottomBorder = new PSimpleBorder(new Insets(0, 0, 0, 0), table.getGridColor(), PSimpleBorder.PaintInPosition.BOTTOM);
            rightBorder = new PSimpleBorder(new Insets(0, 0, 0, 0), table.getGridColor(), PSimpleBorder.PaintInPosition.RIGHT);
            border = BorderFactory.createCompoundBorder(bottomBorder, rightBorder);

            setUI(labelUI);
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension preferredSize = super.getPreferredSize();
            preferredSize.height += 6;

            return preferredSize;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Border columnBorder;
            boolean lastColumn = (table.getColumnCount() - 1 == column);
            columnBorder = border;
            if (lastColumn && table.getParent() == null) {
                columnBorder = bottomBorder;
            }
            setBorder(columnBorder);

            if (value == null) {
                setText("");
            } else {
                if (value instanceof Icon) {
                    setText("");
                    setIcon((Icon) value);
                } else {
                    setText(value.toString());

                    Icon sortIcon = null;
                    RowSorter<? extends TableModel> rowSorter = table.getRowSorter();
                    if (rowSorter != null) {
                        java.util.List<? extends RowSorter.SortKey> sortKeys = rowSorter.getSortKeys();
                        if (sortKeys.size() > 0
                                && sortKeys.get(0).getColumn() == table.convertColumnIndexToModel(column)) {
                            switch (sortKeys.get(0).getSortOrder()) {
                                case ASCENDING:
                                    sortIcon = UIManager.getIcon("Table.ascendingSortIcon");
                                    break;
                                case DESCENDING:
                                    sortIcon = UIManager.getIcon("Table.descendingSortIcon");
                                    break;
                                case UNSORTED:
                                    sortIcon = UIManager.getIcon("Table.naturalSortIcon");
                                    break;
                            }
                        }
                    }

                    setHorizontalTextPosition(SwingConstants.LEFT);
                    setIcon(sortIcon);
                }
            }

            return this;
        }
    }
}
