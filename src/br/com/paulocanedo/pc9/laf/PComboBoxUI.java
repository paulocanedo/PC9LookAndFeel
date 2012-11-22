/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.paulocanedo.pc9.laf;

import br.com.paulocanedo.pc9.util.ComponentUtil;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.ComboPopup;

/**
 *
 * @author paulocanedo
 */
public class PComboBoxUI extends BasicComboBoxUI {

    private int padding = ComponentUtil.DEFAULT_ROUND_CORNER;

    public static ComponentUI createUI(JComponent c) {
        return new PComboBoxUI();
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);

        c.putClientProperty("JComponent.type", "square");
    }

    @Override
    protected ComboPopup createPopup() {
        JPopupMenu cpopup = (JPopupMenu) super.createPopup();

        Border border1 = BorderFactory.createEmptyBorder(0, padding, 0, padding);
        Border border2 = BorderFactory.createLineBorder(Color.GRAY);
        cpopup.setBorder(BorderFactory.createCompoundBorder(border1, border2));
        return (ComboPopup) cpopup;
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        FontMetrics fm = c.getFontMetrics(c.getFont());
        int w, h;

        h = 6 + fm.getHeight();

        JComboBox cb = (JComboBox) c;
        ComboBoxModel model = cb.getModel();
        String maxString = null;
        for (int i = 0; i < model.getSize(); i++) {
            Object elem = model.getElementAt(i);
            if (!(elem instanceof Icon) && !(elem instanceof Component) && elem != null) {
                maxString = elem.toString();
            }
        }
        int stringWidth = maxString == null ? 50 : fm.stringWidth(maxString);
        w = stringWidth + (arrowButton == null ? 0 : arrowButton.getWidth());
        w = 200;

        return new Dimension(w, h);
    }

    @Override
    protected JButton createArrowButton() {
        JButton button = super.createArrowButton();
        button.setBorder(BorderFactory.createEmptyBorder());

        return button;
    }

    @Override
    public void update(Graphics g, JComponent c) {
        if (c.isOpaque()) {
            g.setColor(UIManager.getColor("control"));
            g.fillRect(0, 0, c.getWidth(), c.getHeight());
        }
        paint(g, c);
    }
}
