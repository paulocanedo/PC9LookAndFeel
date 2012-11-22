/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.paulocanedo.pc9.laf;

import br.com.paulocanedo.pc9.util.ComponentUtil;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JComponent;
import javax.swing.border.AbstractBorder;

/**
 *
 * @author paulocanedo
 */
public class PRoundBorder extends AbstractBorder {

    private Insets insets;
    private Color borderColor;

    public PRoundBorder(Insets insets, Color borderColor) {
        this.insets = insets;
        this.borderColor = borderColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return insets;
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        JComponent jc = (JComponent) c;
        int w = width - 1, h = height - 1;
        int roundCorner = ComponentUtil.getRoundedExteriorCorner(jc) / 2;

        String buttonSegmentPosition = (String) jc.getClientProperty("JButton.segmentPosition");

        Shape r = getShape(jc);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(borderColor);

        if (buttonSegmentPosition == null) {
            g2.draw(r);
        }

        if (buttonSegmentPosition == null) {
            //do nothing
        } else if (buttonSegmentPosition.equals("first")) {
            GeneralPath path = new GeneralPath();
            path.moveTo(x + roundCorner, y);
            path.lineTo(x + w, y);
            path.lineTo(x + w, y + h);
            path.lineTo(x + roundCorner, y + h);
            path.curveTo(x + roundCorner, y + h, x, y + h, x, y + h - roundCorner);
            path.lineTo(x, y + roundCorner);
            path.curveTo(x, y + roundCorner, x, y, x + roundCorner, y);

            path.closePath();
            g2.draw(path);
        } else if (buttonSegmentPosition.equals("middle")) {
            g2.drawLine(x, y, w, y);
            g2.drawLine(w, y, w, h);
            g2.drawLine(w, h, x, h);
        } else if (buttonSegmentPosition.equals("last")) {
            GeneralPath path = new GeneralPath();
            path.moveTo(x, y);
            path.lineTo(x + w - roundCorner, y);
            path.curveTo(x + w - roundCorner, y, x + w, y, x + w, y + roundCorner);
            path.lineTo(x + w, y + h - roundCorner);
            path.curveTo(x + w, y + h - roundCorner, x + w, y + h, x + w - roundCorner, y + h);
            path.lineTo(x, y + h);

            g2.draw(path);
        }
    }

    public Shape getShape(JComponent c) {
        int x = 0, y = 0, w = c.getWidth() - 1, h = c.getHeight() - 1;
        int roundCorner = c.getHeight();

        Integer maxRoundCorner = (Integer) c.getClientProperty("maxRoundCorner");
        if (maxRoundCorner != null && maxRoundCorner >= 0) {
            roundCorner = Math.min(roundCorner, maxRoundCorner);
        }

        return new RoundRectangle2D.Double(x, y, w, h, roundCorner, roundCorner);
    }
}
