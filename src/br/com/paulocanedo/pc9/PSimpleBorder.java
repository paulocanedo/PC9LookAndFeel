/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.paulocanedo.pc9;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;

/**
 *
 * @author paulocanedo
 */
public class PSimpleBorder extends AbstractBorder {

    private static final Insets defaultInsets = new Insets(1, 1, 1, 1);
    private static final Color defaultColor = Color.BLACK;
    private static final PaintInPosition defaultPaintInPosition = PaintInPosition.ALL;
    private Insets insets;
    private Color color;
    private PaintInPosition paintInPosition;

    public PSimpleBorder() {
        this(defaultInsets, defaultColor, defaultPaintInPosition);
    }

    public PSimpleBorder(Color color) {
        this(defaultInsets, color, defaultPaintInPosition);
    }

    public PSimpleBorder(Insets insets) {
        this(insets, defaultColor, defaultPaintInPosition);
    }

    public PSimpleBorder(PaintInPosition paintInPosition) {
        this(defaultInsets, defaultColor, paintInPosition);
    }

    public PSimpleBorder(Insets insets, Color color, PaintInPosition paintInPosition) {
        this.insets = insets;
        this.color = color;
        this.paintInPosition = paintInPosition;
    }

    public Border joinBorders(PSimpleBorder... borders) {
        Border border = this;
        for (Border b : borders) {
            return BorderFactory.createCompoundBorder(border, b);
        }
        return border;
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
        Color oldColor = g.getColor();
        g.setColor(color);

        int w = width-1, h = height-1;

        switch (paintInPosition) {
            case NONE: {
                break;
            }
            case ALL: {
                g.drawRect(x, y, w, h);
                break;
            }
            case TOP: {
                g.drawLine(x, y, w, y);
                break;
            }
            case LEFT: {
                g.drawLine(x, y, x, h);
                break;
            }
            case BOTTOM: {
                g.drawLine(x, h, w, h);
                break;
            }
            case RIGHT: {
                g.drawLine(w, y, w, h);
                break;
            }
        }
        g.setColor(oldColor);
    }

    public enum PaintInPosition {

        NONE,
        ALL,
        TOP,
        LEFT,
        BOTTOM,
        RIGHT
    }
}
