/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.paulocanedo.pc9.laf;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 *
 * @author paulocanedo
 */
public class PScrollBarUI extends BasicScrollBarUI {

    public static ComponentUI createUI(JComponent c) {
        return new PScrollBarUI();
    }

    @Override
    protected void configureScrollBarColors() {
        super.configureScrollBarColors();
    }

    @Override
    protected ArrowButtonListener createArrowButtonListener() {
        return super.createArrowButtonListener();
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        JButton button = super.createDecreaseButton(orientation);
        button.setBorder(BorderFactory.createEmptyBorder());
        return button;
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        JButton button = super.createIncreaseButton(orientation);
        button.setBorder(BorderFactory.createEmptyBorder());
        return button;
    }

    @Override
    protected void installComponents() {
        super.installComponents();
    }

    @Override
    protected void installDefaults() {
        super.installDefaults();
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        super.paint(g, c);
    }

    @Override
    protected void paintDecreaseHighlight(Graphics g) {
        super.paintDecreaseHighlight(g);
    }

    @Override
    protected void paintIncreaseHighlight(Graphics g) {
        super.paintIncreaseHighlight(g);
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
            return;
        }

        boolean isVertical = scrollbar.getOrientation() == JScrollBar.VERTICAL;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int w = thumbBounds.width;
        int h = thumbBounds.height;

        g2.translate(thumbBounds.x, thumbBounds.y);

        float[] fractions = new float[]{0f, 1f};
        Color[] colors = new Color[]{new Color(0x92A2B7), new Color(0x5E7290)};

        int x = 0, y = 0;
        int roundRect = isVertical ? w : h;

        Paint paint = new LinearGradientPaint(0, 0, isVertical ? w : 0, isVertical ? 0 : h, fractions, colors);
        g2.setPaint(paint);
        g2.fillRect(0, 0, w, h);
//        g2.fillRoundRect(0, 0, w, h, roundRect, roundRect);

        if (isThumbRollover()) {
            g2.setColor(new Color(255, 255, 255, 40));
            g2.fillRoundRect(0, 0, w, h, roundRect, roundRect);
        }

        g2.setColor(new Color(0x3C4F6E));
        g2.drawRect(0, 0, w - 1, h - 1);
//        g2.drawRoundRect(0, 0, w - 1, h - 1, roundRect, roundRect);

        g2.translate(-thumbBounds.x, -thumbBounds.y);
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        super.paintTrack(g, c, trackBounds);
    }

//    @Override
//    protected void layoutVScrollbar(JScrollBar sb) {
//        super.layoutVScrollbar(sb);
//
//        Rectangle bounds = incrButton.getBounds();
//        int x = bounds.x, y = bounds.y, w = bounds.width, h = bounds.height;
//
//        decrButton.setBounds(x, y - h, w, h);
//        incrButton.setBounds(0, y, w, h);
//
//        Rectangle trackRectBounds = trackRect.getBounds();
//        trackRectBounds.y -= decrButton.getHeight();
//
//        Rectangle thumbBounds = getThumbBounds();
//        setThumbBounds(thumbBounds.x, thumbBounds.y - decrButton.getHeight(), thumbBounds.width, thumbBounds.height);
//    }
}
