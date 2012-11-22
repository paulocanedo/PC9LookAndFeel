/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.paulocanedo.pc9;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import javax.swing.Icon;
import javax.swing.SwingConstants;

/**
 *
 * @author paulocanedo
 */
public class ArrowIcon implements Icon {

    private int width;
    private int height;
    private int direction;

    public ArrowIcon(int width, int height) {
        this(width, height, SwingConstants.SOUTH);
    }

    public ArrowIcon(int width, int height, int direction) {
        this.width = width;
        this.height = height;
        this.direction = direction;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int rotateAngle;
        if (direction == SwingConstants.EAST) {
            rotateAngle = 270;
        } else if (direction == SwingConstants.NORTH) {
            rotateAngle = 180;
        } else if (direction == SwingConstants.WEST) {
            rotateAngle = 90;
        } else {
            rotateAngle = 0;
        }
        g2.rotate(Math.toRadians(rotateAngle), width / 2, height / 2);

        GeneralPath path = new GeneralPath();
        path.moveTo(x, y);
        path.lineTo(x + width, y);
        path.curveTo(x + width, y, x + width - (width / 3), y + height / 2, x + width / 2, y + height);
        path.curveTo(x + width / 2, y + height, x + width / 3, y + height / 2, x, y);
        path.closePath();

        g2.setColor(Color.DARK_GRAY);
        g2.fill(path);

        g2.dispose();
    }

    @Override
    public int getIconWidth() {
        return width;
    }

    @Override
    public int getIconHeight() {
        return height;
    }
}
