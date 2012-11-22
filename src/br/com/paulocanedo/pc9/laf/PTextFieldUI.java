/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.paulocanedo.pc9.laf;

import br.com.paulocanedo.pc9.util.ComponentUtil;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.RoundRectangle2D;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.text.JTextComponent;

/**
 *
 * @author paulocanedo
 */
public class PTextFieldUI extends BasicTextFieldUI {

    private JTextComponent textComponent;
    private RepainterFocus focusListener;
    private boolean oldOpaque;

    public static ComponentUI createUI(JComponent c) {
        return new PTextFieldUI();
    }

    public PTextFieldUI() {
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);

        textComponent = (JTextComponent) c;
        textComponent.putClientProperty("JComponent.type", "normal");

        if (focusListener == null) {
            focusListener = new RepainterFocus();
        }
        c.addFocusListener(focusListener);

        oldOpaque = c.isOpaque();
        c.setOpaque(false);
    }

    @Override
    public void uninstallUI(JComponent c) {
        super.uninstallUI(c);

        c.removeFocusListener(focusListener);
        c.setOpaque(oldOpaque);
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        Dimension preferredSize = super.getPreferredSize(c);
        if (c.getInsets() != null) {
            preferredSize.height += c.getInsets().bottom + c.getInsets().top;
            preferredSize.width += c.getInsets().left + c.getInsets().right + 2;
        }
        return preferredSize;
    }

    @Override
    protected void paintBackground(Graphics g) {
        Boolean opaque = (Boolean) textComponent.getClientProperty("opaque");
        if (opaque != null && opaque == false) {
            return;
        }
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setColor(textComponent.getBackground());
        Shape shape = getInteriorShape(textComponent);

        ComponentUtil.fastFill(g2, (RoundRectangle2D) shape);
        g2.dispose();
    }

    @Override
    protected void paintSafely(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        Object desktopProperty = Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints");
        if (desktopProperty != null) {
            g2.addRenderingHints((Map) desktopProperty);
        }

        g2.setColor(UIManager.getColor("control"));
        paintBackground(g2);

        //work around to fix text position
        if (textComponent.getBorder() instanceof PBorder) {
            PBorder border = (PBorder) textComponent.getBorder();
            Insets borderInsets = border.getBorderInsets(textComponent);

            borderInsets.left += ComponentUtil.getRoundedInteriorCorner(textComponent);
            borderInsets.right += ComponentUtil.getRoundedInteriorCorner(textComponent);
        }//-------------------------------
        super.paintSafely(g2);

        if ((textComponent.getText() == null || textComponent.getText().isEmpty())) {
            String description = (String) textComponent.getClientProperty("placeholder");
            if (description != null) {
                Paint oldPaint = g2.getPaint();
                g2.setColor(Color.GRAY);

                g2.setFont(textComponent.getFont());

                int x = textComponent.getInsets().left;
                int y = (textComponent.getHeight() + g2.getFontMetrics().getHeight()) / 2 - 3;
                g2.drawString(description, x, y);
                g2.setPaint(oldPaint);
            }
        }

        //work around to fix text position
        if (textComponent.getBorder() instanceof PBorder) {
            PBorder border = (PBorder) textComponent.getBorder();
            Insets borderInsets = border.getBorderInsets(textComponent);

            borderInsets.left -= ComponentUtil.getRoundedInteriorCorner(textComponent);
            borderInsets.right -= ComponentUtil.getRoundedInteriorCorner(textComponent);
        }//-------------------------------
    }

    protected Shape getShape(JComponent c) {
        return ComponentUtil.getRoundedExternalShape(c);
    }

    protected Shape getInteriorShape(JComponent c) {
        return ComponentUtil.getRoundedInteriorShape(c);
    }

    private class RepainterFocus implements FocusListener {

        @Override
        public void focusGained(FocusEvent e) {
            textComponent.repaint();
        }

        @Override
        public void focusLost(FocusEvent e) {
            textComponent.repaint();
        }
    }
}
