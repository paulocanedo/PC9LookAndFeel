/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.paulocanedo.pc9.laf;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.plaf.basic.BasicLabelUI;

/**
 *
 * @author paulocanedo
 */
public class PLabelUI extends BasicLabelUI {

    private static ComponentUI componentUI;

    public static ComponentUI createUI(JComponent c) {
        if (componentUI == null) {
            componentUI = new PLabelUI();
        }
        return componentUI;
    }

    @Override
    protected void paintEnabledText(JLabel l, Graphics g, String s, int textX, int textY) {
        Graphics2D g2 = (Graphics2D) g.create();
        Object desktopProperty = Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints");
        if (desktopProperty != null) {
            g2.addRenderingHints((Map) desktopProperty);
        }

        g2.setColor(l.getForeground());
        int mnemonicIndex = l.getDisplayedMnemonicIndex();
        BasicGraphicsUtils.drawStringUnderlineCharAt(g2, l.getText(), mnemonicIndex,
                textX,
                textY);

        g2.dispose();
    }

    @Override
    protected void paintDisabledText(JLabel l, Graphics g, String s, int textX, int textY) {
        Graphics2D g2 = (Graphics2D) g.create();
        Object desktopProperty = Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints");
        if (desktopProperty != null) {
            g2.addRenderingHints((Map) desktopProperty);
        }

        g2.setColor(l.getBackground().brighter());
        int mnemonicIndex = l.getDisplayedMnemonicIndex();
        BasicGraphicsUtils.drawStringUnderlineCharAt(g2, l.getText(), mnemonicIndex,
                textX,
                textY);

        g2.setColor(l.getBackground().darker());
        BasicGraphicsUtils.drawStringUnderlineCharAt(g2, l.getText(), mnemonicIndex,
                textX - 1,
                textY - 1);
    }
}
