/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.paulocanedo.pc9.laf;

import br.com.paulocanedo.pc9.util.ComponentUtil;
import br.com.paulocanedo.pc9.util.LafUtils;
import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JToolTip;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.plaf.basic.BasicToolTipUI;
import javax.swing.text.View;

/**
 *
 * @author paulocanedo
 */
public class PToolTipUI extends BasicToolTipUI {

    private static ComponentUI componentUI;
    private static Composite alphaComposite = AlphaComposite.SrcOver.derive(0.9f);

    public static ComponentUI createUI(JComponent c) {
        if (componentUI == null) {
            componentUI = new PToolTipUI();
        }
        return componentUI;
    }

    public PToolTipUI() {
    }

    @Override
    protected void installDefaults(JComponent c) {
        super.installDefaults(c);

        LafUtils.applyMaxRoundCorner(c, 12);
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);

        c.setOpaque(false);
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        Dimension preferredSize = super.getPreferredSize(c);
        preferredSize.height += 8;
        preferredSize.width += 8;
        return preferredSize;
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        Object desktopProperty = Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints");
        if (desktopProperty != null) {
            g2.addRenderingHints((Map) desktopProperty);
        }

        g2.setComposite(alphaComposite);
        g2.setColor(c.getBackground());
        g2.fillRect(0, 0, c.getWidth(), c.getHeight());

        g2.setComposite(AlphaComposite.SrcOver);
        paintText(g2, c);
    }

    private void paintText(Graphics2D g2, JComponent c) {
        int w = c.getWidth(), h = c.getHeight();
        FontMetrics fm = c.getFontMetrics(c.getFont());

        String tipText = ((JToolTip) c).getTipText();
        if (tipText == null) {
            tipText = "";
        }

        int x = (w - fm.stringWidth(tipText)) / 2, y = ((h - fm.getHeight()) / 2);
        View v = (View) c.getClientProperty(BasicHTML.propertyKey);
        if (v != null) {
            Insets insets = c.getInsets();
            Rectangle textRect = new Rectangle(insets.left + 4, insets.top + 4, w - insets.right - insets.left, h - insets.top - insets.bottom);
            v.paint(g2, textRect);
        } else {
            g2.setColor(c.getForeground());
            BasicGraphicsUtils.drawString(g2, tipText, -1, x, y + fm.getAscent());
        }
    }
}
