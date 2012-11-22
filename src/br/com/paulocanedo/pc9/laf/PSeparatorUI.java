/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.paulocanedo.pc9.laf;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.Paint;
import java.awt.RenderingHints;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSeparatorUI;

/**
 *
 * @author paulocanedo
 */
public class PSeparatorUI extends BasicSeparatorUI {

    private Color[] colors;
    private static float[] fractions = new float[]{0f, 0.5f};

    public static ComponentUI createUI(JComponent c) {
        return new PSeparatorUI();
    }

    private Color getColor() {
        return UIManager.getColor("Separator.foreground");
    }

    private Color getControlColor() {
        return UIManager.getColor("control");
    }

    private Color[] getColors() {
        if (colors == null) {
            colors = new Color[]{getControlColor(), getColor()};
        }
        return colors;
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int w = c.getWidth(), h = c.getHeight();
        int x = 0, y = h / 2;

        try {
            Paint paint = new LinearGradientPaint(x, y, w / 2, y, fractions, getColors(), CycleMethod.REFLECT);

            String directionGradient = (String) c.getClientProperty("directionGradient");
            if (directionGradient != null) {
                if (directionGradient.equals("leftToRight")) {
                    paint = new LinearGradientPaint(x, y, w, y, fractions, getColors());
                } else if (directionGradient.equals("rightToLeft")) {
                    paint = new LinearGradientPaint(w, y, x, y, fractions, getColors());
                }
            }
            g2.setPaint(paint);
        } catch (Throwable t) {
            g2.setColor(getColor());
        }

        g2.drawLine(x, y, w, y);
    }
}
