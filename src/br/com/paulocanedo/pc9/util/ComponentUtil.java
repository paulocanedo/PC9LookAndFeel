/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.paulocanedo.pc9.util;

import br.com.paulocanedo.pc9.laf.PBorder;
import br.com.paulocanedo.pc9.laf.PRoundBorder;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JComponent;

/**
 *
 * @author paulocanedo
 */
public final class ComponentUtil {

    public static int DEFAULT_ROUND_CORNER = 6;

    public static int getBorderSize(JComponent c) {
        Insets insets = c.getInsets();

        return insets.top;
    }

    public static Rectangle2D getInteriorRect(JComponent c) {
        int x = getBorderSize(c);
        int y = getBorderSize(c);
        int w = c.getWidth() - (getBorderSize(c) * 2);
        int h = c.getHeight() - (getBorderSize(c) * 2);

        return new Rectangle2D.Double(x, y, w, h);
    }

    public static RoundRectangle2D getRoundedInteriorShape(JComponent c) {
        int x = getBorderSize(c);
        int y = getBorderSize(c);
        int w = c.getWidth() - (getBorderSize(c) * 2);
        int h = c.getHeight() - (getBorderSize(c) * 2);
        int roundCorner = getRoundedInteriorCorner(c);

        return new RoundRectangle2D.Double(x, y, w, h, roundCorner, roundCorner);
    }

    public static RoundRectangle2D getRoundedExternalShape(JComponent c) {
        int x = 0;
        int y = 0;
        int w = c.getWidth();
        int h = c.getHeight();
        int roundCorner = getRoundedExteriorCorner(c);

        return new RoundRectangle2D.Double(x, y, w, h, roundCorner, roundCorner);
    }

    public static RoundRectangle2D getExternalPaintableShape(JComponent c) {
        int x = 0, y = 0;
        int w = c.getWidth() - 1, h = c.getHeight() - 1;
        int roundCorner = h;
        Integer maxRoundCorner = (Integer) c.getClientProperty("maxRoundCorner");
        if (maxRoundCorner != null && maxRoundCorner >= 0) {
            roundCorner = Math.min(roundCorner, maxRoundCorner);
        }

        return new RoundRectangle2D.Double(x, y,
                w, h, roundCorner, roundCorner);
    }

    public static int getRoundedInteriorCorner(JComponent c) {
        if (!(c.getBorder() instanceof PBorder) && !(c.getBorder() instanceof PRoundBorder)) {
            return 0;
        }

        int h = c.getHeight() - (getBorderSize(c) * 2);
        int roundCorner = h;

        String ctype = (String) c.getClientProperty("JComponent.type");
        if (ctype != null) {
            if (ctype.equals("roundRect")) {
                return roundCorner;
            } else if (ctype.equals("square")) {
                return 0;
            } else if (ctype.equals("normal")) {
                return DEFAULT_ROUND_CORNER;
            }
        }

        Integer maxRoundCorner = (Integer) c.getClientProperty("maxRoundCorner");
        if (maxRoundCorner != null && maxRoundCorner >= 0) {
            roundCorner = Math.min(roundCorner, maxRoundCorner);
        }
        return roundCorner;
    }

    public static int getRoundedExteriorCorner(JComponent c) {
        int roundCorner = c.getHeight() - 1;
        String ctype = (String) c.getClientProperty("JComponent.type");
        if (ctype != null) {
            if (ctype.equals("roundRect")) {
                return roundCorner;
            } else if (ctype.equals("square")) {
                return 0;
            } else if (ctype.equals("normal")) {
                return DEFAULT_ROUND_CORNER;
            }
        }

        Integer maxRoundCorner = (Integer) c.getClientProperty("maxRoundCorner");
        if (maxRoundCorner != null && maxRoundCorner >= 0) {
            roundCorner = Math.min(roundCorner, maxRoundCorner);
        }
        return roundCorner;
    }

//    public static Shape getInteriorShapeAsFirstPartTextField(Rectangle bounds) {
//        int x = bounds.x;
//        int y = bounds.y;
//        int w = bounds.width;
//        int h = bounds.height;
//        int roundCorner = h;
//
//        int x1 = x + w, x2 = x + roundCorner, x3 = x, x4 = x, x5 = x, x6 = x, x7 = x + roundCorner, x8 = x1;
//        int y1 = y + h, y2 = y1, y3 = y1, y4 = y + h - roundCorner, y5 = y + roundCorner, y6 = y, y7 = y6, y8 = y6;
//        GeneralPath path = new GeneralPath();
//        path.moveTo(x1, y1);
//        path.lineTo(x2, y2);
//        path.curveTo(x2, y2, x3, y3, x4, y4);
//        path.lineTo(x5, y5);
//        path.curveTo(x5, y5, x6, y6, x7, y7);
//        path.lineTo(x8, y8);
//        return path;
//    }
    public static Shape getInteriorShapeAsLastPart(Rectangle bounds) {
        int x = bounds.x;
        int y = bounds.y;
        int w = bounds.width;
        int h = bounds.height;
        int roundCorner = h / 2;

        int x1 = x, x2 = x + w - roundCorner, x3 = x + w, x4 = x3, x5 = x3, x6 = x3, x7 = x2, x8 = x1;
        int y1 = y, y2 = y1, y3 = 1, y4 = y + roundCorner, y5 = y + h - roundCorner, y6 = y + h, y7 = y6, y8 = y6;
        GeneralPath path = new GeneralPath();
        path.moveTo(x1, y1);
        path.lineTo(x2, y2);
        path.curveTo(x2, y2, x3, y3, x4, y4);
        path.lineTo(x5, y5);
        path.curveTo(x5, y5, x6, y6, x7, y7);
        path.lineTo(x8, y8);
        return path;
    }

    public static void fastFill(Graphics2D g2, RoundRectangle2D r) {
        Rectangle b = r.getBounds();
        g2.fillRoundRect(b.x, b.y, b.width, b.height, (int) r.getArcWidth(), (int) r.getArcHeight());
    }

    public static void fastFill(Graphics2D g2, Rectangle2D r) {
        Rectangle b = r.getBounds();
        g2.fillRect(b.x, b.y, b.width, b.height);
    }
}
