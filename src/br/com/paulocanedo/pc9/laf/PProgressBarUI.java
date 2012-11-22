/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.paulocanedo.pc9.laf;

import br.com.paulocanedo.pc9.util.ComponentUtil;
import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JComponent;
import javax.swing.LookAndFeel;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.plaf.basic.BasicProgressBarUI;

/**
 *
 * @author paulocanedo
 */
public class PProgressBarUI extends BasicProgressBarUI {

    private int position = 0;
    private int amount = 24;
    private ActionListener animatorListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            position++;
            if (position >= 24) {
                position = 0;
            }
            progressBar.repaint();
        }
    };
    private javax.swing.Timer animator = new javax.swing.Timer(70, animatorListener);

    public static ComponentUI createUI(JComponent c) {
        return new PProgressBarUI();
    }

    @Override
    protected void installDefaults() {
        super.installDefaults();

        LookAndFeel.installProperty(progressBar, "opaque", Boolean.FALSE);
    }

    @Override
    protected void installListeners() {
        super.installListeners();

        progressBar.addPropertyChangeListener(new PropertyChangeListener() {

            private Dimension originalSize;

            @Override
            public void propertyChange(PropertyChangeEvent evt) {

                if (evt.getPropertyName().equals("indeterminate")) {
                    boolean indeterminate = ((Boolean) evt.getNewValue());
                    progressBar.setBorderPainted(!indeterminate);

                    if (indeterminate) {
                        Dimension size = progressBar.getSize();
                        if (size != null && size.width > 0 && size.height > 0) {
                            originalSize = size;
                            progressBar.setSize(new Dimension(size.height, size.height));
                        } else {
                            Dimension preferredSize = progressBar.getPreferredSize();
                            originalSize = preferredSize;
                            progressBar.setPreferredSize(new Dimension(preferredSize.height, preferredSize.height));
                            progressBar.setSize(new Dimension(preferredSize.height, preferredSize.height));
                        }
                    } else {
                        if (originalSize != null) {
                            progressBar.setPreferredSize(originalSize);
                            progressBar.setSize(originalSize);
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void paintDeterminate(Graphics g, JComponent c) {
        Insets b = progressBar.getInsets(); // area for border
        int barRectWidth = progressBar.getWidth() - (b.right + b.left);
        int barRectHeight = progressBar.getHeight() - (b.top + b.bottom);

        if (barRectWidth <= 0 || barRectHeight <= 0) {
            return;
        }


        // amount of progress to draw
        int amountFull = getAmountFull(b, barRectWidth, barRectHeight);

        Shape amountShape = getAmountShape(c, amountFull, amountFull == barRectWidth);

        Graphics2D g2 = (Graphics2D) g;
        RoundRectangle2D externalShape = ComponentUtil.getRoundedExternalShape(c);
        g2.setColor(progressBar.getBackground());
        ComponentUtil.fastFill(g2, externalShape);

        g2.setColor(progressBar.getForeground());
        g2.fill(amountShape);

        if (progressBar.isStringPainted()) {
            paintString(g, b.left, b.top,
                    barRectWidth, barRectHeight,
                    amountFull, b);
        }
    }

    @Override
    protected void paintIndeterminate(Graphics g, JComponent c) {
        Insets b = progressBar.getInsets(); // area for border
        int barRectWidth = progressBar.getWidth() - (b.right + b.left);
        int barRectHeight = progressBar.getHeight() - (b.top + b.bottom);

        if (barRectWidth <= 0 || barRectHeight <= 0) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        String s = progressBar.getString();
        g2.setFont(progressBar.getFont());
        g2.setColor(progressBar.getForeground());

        FontMetrics fm = progressBar.getFontMetrics(progressBar.getFont());
        int tx = progressBar.getHeight() + 2, ty = (progressBar.getHeight() + fm.getHeight()) / 2;
        BasicGraphicsUtils.drawString(g2, progressBar.getString(), -1, tx, ty);

        int width = progressBar.getHeight(), height = Math.max(progressBar.getHeight(), 18);
        int angle = 360 / amount;
        int middleX = width / 2, middleY = height / 2;
        int w = height / 3, x = width / 2 + w / 2, y = middleY,
                h = w / 6, corner = h;

        y = y - h / 2;

        g2.rotate(Math.toRadians(angle * position), middleX, middleY);
        for (int i = 0; i < amount; i++) {
            float opacity = (amount - i) / (float) amount;

            Composite composite = AlphaComposite.SrcOver.derive(opacity);
            g2.setComposite(composite);
            g2.fillRoundRect(x, y, w, h, corner, corner);
            g2.rotate(Math.toRadians(angle), middleX, middleY);
        }
    }

    @Override
    protected void startAnimationTimer() {
        animator.start();
    }

    @Override
    protected void stopAnimationTimer() {
        animator.stop();
    }

    protected Shape getAmountShape(JComponent c, int amountFull, boolean flagFull) {
        Border border = c.getBorder();

        if (border instanceof PRoundBorder) {
            Shape full = ((PRoundBorder) border).getShape(c);
            if (flagFull) {
                return full;
            }

            Rectangle2D shape = new Rectangle2D.Double(0, 0, amountFull, c.getHeight());
            Area a = new Area(full);
            a.intersect(new Area(shape));
            return a;
        }
        return new Rectangle(0, 0, amountFull, c.getHeight());
    }
}
