/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.paulocanedo.pc9.laf;

import br.com.paulocanedo.third.effects.ShadowRenderer;
import br.com.paulocanedo.pc9.util.ComponentUtil;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.border.AbstractBorder;

/**
 *
 * @author paulocanedo
 */
public class PBorder extends AbstractBorder {

    private int borderSize;
    private float borderOpacity;
    private Insets borderInsets;
    private PRoundBorder partialBorder;
    private Color color;

    public PBorder() {
        this.borderSize = 5;
        this.borderOpacity = 0.8f;
        this.borderInsets = new Insets(borderSize, borderSize, borderSize, borderSize);

        partialBorder = new PRoundBorder(borderInsets, Color.DARK_GRAY);
    }

    private Color getBorderColor(JComponent c) {
        if (color != null) {
            return color;
        }
        return (isActive(c)) ? UIManager.getColor("borderFocusColor") : Color.GRAY;
    }

    private boolean isActive(JComponent c) {
        Boolean forceBlur = (Boolean) ((JComponent) c).getClientProperty("JComponent.forceBorderBlurred");
        forceBlur = forceBlur == null ? false : forceBlur;

        return c.isFocusOwner() || forceBlur;
    }

    @Override
    public Insets getBorderInsets(Component c) {
//        JComponent jc = (JComponent) c;
//        if (PButtonUI.isToolbarButton(jc) || PButtonUI.isIconButton(jc)) {
//            return borderInsets;
//        }
//        int hInsetValue;
//
//        if (ClientPropertyUtil.isRoundRect(jc)) {
//            hInsetValue = c.getHeight() / 2;
//        } else if (ClientPropertyUtil.isSquare(jc)) {
//            hInsetValue = 0;
//        } else if (ClientPropertyUtil.isNormalType(jc)) {
//            hInsetValue = ComponentUtil.DEFAULT_ROUND_CORNER;
//        } else {
//            Integer roundCorner = ClientPropertyUtil.getClientPropertyAsInteger(jc, "maxRoundCorner");
//            hInsetValue = roundCorner == null ? c.getHeight() / 2 : roundCorner;
//        }
//
//        borderInsets.left = 2 + hInsetValue;
//        borderInsets.right = 2 + hInsetValue;
//
//        if (c instanceof JPanel) {
//            borderInsets.left = 10;
//            borderInsets.right = 10;
//        }

        return borderInsets;
    }

    public Insets getBorderInsets() {
        return borderInsets;
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        String segmentPosition = (String) ((JComponent) c).getClientProperty("JButton.segmentPosition");
        JComponent jc = (JComponent) c;
        int w = width - 1, h = height - 1;
        if (segmentPosition != null) {
            partialBorder.paintBorder(c, g, x, y, width, height);
            return;
        } else if (PButtonUI.isSpecialButton(jc)) {
            g.setColor(getBorderColor(jc));
            g.drawRect(x, y, w, h);
            return;
        }


        int roundCorner = ComponentUtil.getRoundedInteriorCorner(jc);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBorderColor(jc));
        g2.drawRoundRect(borderSize, borderSize, w - borderSize * 2, h - borderSize * 2, roundCorner, roundCorner);

        if (isActive(jc)) {
            Image image = createBorderImage(g2, getBorderColor(jc), w - borderSize, h - borderSize, roundCorner);

            g2.drawImage(image, -borderSize, -borderSize, null);
        }

        g2.dispose();
    }

    private Image createBorderImage(Graphics2D g2, Color color, int w, int h, int roundCorner) {
        BufferedImage image = g2.getDeviceConfiguration().createCompatibleImage(w, h, Transparency.TRANSLUCENT);
        Graphics2D graphics = image.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(color);
        graphics.translate(borderSize, borderSize);
        graphics.fillRoundRect(0, 0, w - borderSize, h - borderSize, roundCorner, roundCorner);

        ShadowRenderer renderer = new ShadowRenderer(borderSize, borderOpacity, color);
        BufferedImage shadow = renderer.createShadow(image);

        Graphics2D sgraphics = shadow.createGraphics();
        sgraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        sgraphics.setComposite(AlphaComposite.Clear);
        sgraphics.translate(borderSize, borderSize);
        sgraphics.fillRoundRect(borderSize, borderSize, w - borderSize, h - borderSize, roundCorner, roundCorner);
        return shadow;
    }
}
