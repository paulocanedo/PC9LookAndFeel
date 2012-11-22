/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.paulocanedo.pc9;

import br.com.paulocanedo.pc9.util.ComponentUtil;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.util.Map;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;

/**
 *
 * @author paulocanedo
 */
public class PButtonBarUI extends BasicButtonUI {

    private Color color1 = new Color(0x7e7e7f);
    private Color color2 = new Color(0xcfcfcf);
    private static ComponentUI buttonUI;
    private boolean oldOpaque;
    private Dimension preferredSize = new Dimension();

    public static ComponentUI createUI(JComponent c) {
        if (buttonUI == null) {
            buttonUI = new PButtonBarUI();
        }
        return buttonUI;
    }

    public PButtonBarUI() {
    }

    @Override
    protected void installDefaults(AbstractButton b) {
        super.installDefaults(b);

        oldOpaque = b.isOpaque();
        b.setOpaque(false);
        b.setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6));
    }

    @Override
    public void uninstallUI(JComponent c) {
        super.uninstallUI(c);

        c.setOpaque(oldOpaque);
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        int size = UIManager.getInt("PButtomBar.size");
        size = 20;
        preferredSize.setSize(size, size);
        return preferredSize;
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        AbstractButton ab = ((AbstractButton) c);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (ab.isOpaque()) {
            Color background = ab.getBackground();
            g2.setColor(background);
            g2.fillRect(0, 0, ab.getWidth(), ab.getHeight());
            
            if(background instanceof UIResource) {
                Color color = g2.getColor();
                g2.setColor(Color.RED);
                g2.drawLine(0, 0, ab.getWidth(), ab.getHeight());
                g2.setColor(color);
            }
        }

        if (ab.getModel().isRollover()) {
            customPaintBackground(g2, ab, false);
        }

        super.paint(g2, c);
    }

    private void customPaintBackground(Graphics2D g2, AbstractButton ab, boolean isPressed) {
        Composite composite = g2.getComposite();
        g2.setComposite(AlphaComposite.SrcOver.derive(0.4f));

        if (isPressed) {
            g2.setColor(color1);
        } else {
            g2.setColor(color2);
        }
        g2.fillRect(0, 0, ab.getWidth(), ab.getHeight());
        g2.setComposite(composite);
    }

    @Override
    protected void paintButtonPressed(Graphics g, AbstractButton b) {
        customPaintBackground((Graphics2D) g, b, true);
    }

    @Override
    protected void paintText(Graphics g, AbstractButton b, Rectangle textRect, String text) {
        Graphics2D g2 = (Graphics2D) g.create();
        Object desktopProperty = Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints");
        if (desktopProperty != null) {
            g2.addRenderingHints((Map) desktopProperty);
        }

        ButtonModel model = b.getModel();
        FontMetrics fm = b.getFontMetrics(b.getFont());
        int mnemonicIndex = b.getDisplayedMnemonicIndex();

        if (model.isEnabled()) {
            g2.setColor(b.getForeground());

            if (model.isPressed()) {
                BasicGraphicsUtils.drawStringUnderlineCharAt(g2, b.getText(), mnemonicIndex,
                        textRect.x + getTextShiftOffset() + 1,
                        textRect.y + fm.getAscent() + getTextShiftOffset() + 1);
            } else {
                BasicGraphicsUtils.drawStringUnderlineCharAt(g2, text, mnemonicIndex,
                        textRect.x + getTextShiftOffset(),
                        textRect.y + fm.getAscent() + getTextShiftOffset());
            }
        } else {
            g2.setColor(b.getBackground().brighter());
            BasicGraphicsUtils.drawStringUnderlineCharAt(g2, text, mnemonicIndex,
                    textRect.x + getTextShiftOffset(),
                    textRect.y + fm.getAscent() + getTextShiftOffset());

            g2.setColor(b.getBackground().darker());
            BasicGraphicsUtils.drawStringUnderlineCharAt(g2, b.getText(), mnemonicIndex,
                    textRect.x + getTextShiftOffset() - 1,
                    textRect.y + fm.getAscent() + getTextShiftOffset() - 1);
        }
    }

    private Shape getShape(JComponent c) {
        String segmentPosition = (String) ((JComponent) c).getClientProperty("JButton.segmentPosition");
        if (segmentPosition == null) {
            return ComponentUtil.getRoundedInteriorShape(c);
        } else {
            return ComponentUtil.getExternalPaintableShape(c);
        }
    }
}
