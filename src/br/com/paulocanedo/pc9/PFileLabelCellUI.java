/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.paulocanedo.pc9;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicLabelUI;

/**
 *
 * @author paulocanedo
 */
public class PFileLabelCellUI extends BasicLabelUI {

    private static ComponentUI componentUI;
    private boolean selected = false;

    public static ComponentUI createUI(JComponent c) {
        if (componentUI == null) {
            componentUI = new PFileLabelCellUI();
        }
        return componentUI;
    }

    @Override
    protected void paintEnabledText(JLabel l, Graphics g, String s, int textX, int textY) {
        textX += 5;
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2.setFont(l.getFont().deriveFont(Font.PLAIN));

        g2.setColor(l.getForeground());
        g2.drawString(l.getText(), textX, textY);
    }

    @Override
    protected void paintDisabledText(JLabel l, Graphics g, String s, int textX, int textY) {
        textX += 5;
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2.setFont(l.getFont().deriveFont(Font.PLAIN));

        g2.setColor(l.getBackground().brighter());
        g2.drawString(l.getText(), textX, textY);

        g2.setColor(l.getBackground().darker());
        g2.drawString(l.getText(), textX - 1, textY - 1);
    }

    @Override
    public void update(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (c.isOpaque()) {
            g2.setColor(selected ? UIManager.getColor("List.selectionBackground") : c.getBackground());
            g2.fillRect(0, 0, c.getWidth(), c.getHeight());
        }
        paint(g2, c);
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
