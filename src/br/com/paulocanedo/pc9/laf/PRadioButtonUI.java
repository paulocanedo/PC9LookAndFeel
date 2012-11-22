/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.paulocanedo.pc9.laf;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicRadioButtonUI;

/**
 *
 * @author paulocanedo
 */
public class PRadioButtonUI extends BasicRadioButtonUI {

    protected static Color color1 = new Color(0x73dcff);
    protected static Color color2 = new Color(0x597aff);
    private static ComponentUI componentUI;

    public static ComponentUI createUI(JComponent c) {
        if (componentUI == null) {
            componentUI = new PRadioButtonUI();
        }
        return componentUI;
    }

    @Override
    public synchronized void paint(Graphics g, JComponent c) {
        super.paint(g, c);

        AbstractButton ab = (AbstractButton) c;
        if (ab.getModel().isRollover()) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            paintOnFocus(g2, ab);
            g2.dispose();
        }
    }

    protected static Shape getRadioShape(Rectangle iconRect) {
        int offset = 0;
        int x = offset, y = offset, w = iconRect.width - offset * 2, h = iconRect.height - offset * 2;
        Ellipse2D oval = new Ellipse2D.Double(x, y, w, h);

        return oval;
    }

    public static class RadioIcon implements Icon, UIResource, Serializable {

        private int size;

        public RadioIcon(int size) {
            this.size = size;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            AbstractButton b = (AbstractButton) c;

            Graphics2D g2 = (Graphics2D) g.create();
            g2.translate(x, y);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Color[] colors;
            if (b.getModel().isSelected()) {
                colors = new Color[]{color1, color2};
            } else {
                Color c1 = UIManager.getColor("window");
                colors = new Color[]{c1, c1.darker()};
            }
            Paint paint = new LinearGradientPaint(0, 0, 0, getIconHeight(), new float[]{0f, 1f}, colors);
            g2.setPaint(paint);
            Shape shape = getRadioShape(new Rectangle(getIconWidth(), getIconHeight()));
            g2.fill(shape);

            if (b.getModel().isSelected()) {
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                int bx = getIconWidth() / 2, by = getIconHeight() / 2;
                g2.drawLine(bx, by, bx, by);
            }

            g2.setStroke(new BasicStroke(1f));
            g2.setColor(Color.GRAY);
            g2.draw(shape);
        }

        @Override
        public int getIconWidth() {
            return size;
        }

        @Override
        public int getIconHeight() {
            return size;
        }
    }

    protected void paintOnFocus(Graphics2D g2, AbstractButton b) {
        int w = (int) b.getWidth(), h = (int) b.getHeight();

        g2.setColor(Color.BLACK);
        g2.setComposite(AlphaComposite.SrcOver.derive(0.1f));
        g2.fillRoundRect(0, 0, w, h, 6, 6);

        g2.setComposite(AlphaComposite.Src);
    }
}
