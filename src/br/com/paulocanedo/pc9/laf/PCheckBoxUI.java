/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.paulocanedo.pc9.laf;

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
import java.awt.geom.RoundRectangle2D;
import java.io.Serializable;
import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;

/**
 *
 * @author paulocanedo
 */
public class PCheckBoxUI extends PRadioButtonUI {

    private final static String propertyPrefix = "CheckBox" + ".";
    private static ComponentUI componentUI;

    public static ComponentUI createUI(JComponent c) {
        if (componentUI == null) {
            componentUI = new PCheckBoxUI();
        }
        return componentUI;
    }

    @Override
    public String getPropertyPrefix() {
        return propertyPrefix;
    }

    protected static Shape getRadioShape(Rectangle iconRect) {
        int offset = 0;
        int x = offset, y = offset, w = iconRect.width - offset * 2, h = iconRect.height - offset * 2;
        RoundRectangle2D shape = new RoundRectangle2D.Double(x, y, w, h, 4, 4);

        return shape;
    }

    public static class CheckBoxIcon implements Icon, UIResource, Serializable {

        private int size;

        public CheckBoxIcon(int size) {
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
                g2.setStroke(new BasicStroke(2f));
                int w = getIconWidth(), h = getIconHeight();
                int space = 4;
                g2.drawLine(space, space, w - space, h - space);
                g2.drawLine(w - space, space, space, h - space);
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
}
