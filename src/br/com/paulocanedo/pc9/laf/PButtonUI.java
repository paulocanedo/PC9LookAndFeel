/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.paulocanedo.pc9.laf;

import br.com.paulocanedo.pc9.util.ComponentUtil;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Map;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.JRootPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;

/**
 *
 * @author paulocanedo
 */
public class PButtonUI extends BasicButtonUI {

    private Color color1 = new Color(0xf5f5fa);
    private Color color2 = new Color(0xcececf);
    private Color color3 = new Color(0xb6babf);
    private Color color4 = new Color(0xcfcfcf);
    private Color[] colors = new Color[]{color1, color2, color3, color4};
    private float[] fractions = new float[]{0.0f, 0.5f, 0.501f, 1.0f};
    private static ComponentUI buttonUI;
    private boolean oldOpaque;

    public static ComponentUI createUI(JComponent c) {
        if (buttonUI == null) {
            buttonUI = new PButtonUI();
        }
        return buttonUI;
    }

    public PButtonUI() {
    }

    @Override
    protected void installDefaults(AbstractButton b) {
        super.installDefaults(b);

        if (!isToolbarButton(b)) {
            oldOpaque = b.isOpaque();
            b.setOpaque(false);

            b.putClientProperty("JComponent.roundType", "roundRect");
        }
    }

    @Override
    public void uninstallUI(JComponent c) {
        super.uninstallUI(c);

        c.setOpaque(oldOpaque);
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        if (isSpecialButton(c)) {
            return super.getPreferredSize(c);
        }
        String segmentPosition = (String) ((JComponent) c).getClientProperty("JButton.segmentPosition");
        Boolean buttonClearText = (Boolean) c.getClientProperty("JButton.clearText");
        buttonClearText = buttonClearText == null ? false : buttonClearText;

        Dimension preferredSize = super.getPreferredSize(c);
        Dimension newSize = new Dimension(preferredSize);
        if (buttonClearText == true || (c.getInsets() != null && segmentPosition == null)) {
            newSize.height += c.getInsets().top;
            newSize.width += preferredSize.getHeight();
        }
        return newSize;
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        AbstractButton ab = ((AbstractButton) c);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        colors = new Color[]{color1, color2, color3, color4};

        JRootPane rootPane = ab.getRootPane();
        if (rootPane != null && rootPane.getDefaultButton() == ab) {
            colors = new Color[]{new Color(0xadcff7), new Color(0x82bdf5), new Color(0x73a7eb), new Color(0x7ac5fa)};
        }
        paintBackground(g2, c, AlphaComposite.Src);

        ButtonModel model = ab.getModel();
        if (model.isRollover() && isToolbarButton(c)) {
            g2.setColor(UIManager.getColor("Menu.selectionBackground"));
            Composite composite = g2.getComposite();
            g2.setComposite(AlphaComposite.SrcOver.derive(0.4f));
            g2.fillRect(0, 0, c.getWidth(), c.getHeight());
            g2.setComposite(composite);
        }

        super.paint(g2, c);
    }

    private void paintBackground(Graphics2D g2, JComponent c, Composite composite) {
        AbstractButton ab = ((AbstractButton) c);
        if (!ab.isContentAreaFilled()) {
            return;
        }

        Composite oldComposite = g2.getComposite();
        g2.setComposite(composite);

        Shape rect = getShape(c);

        Color background = c.getBackground();
        if (background instanceof UIResource) {
            Paint p = new LinearGradientPaint(0.0f, 0.0f, 0.0f, 3 * c.getHeight() / 4,
                    fractions,
                    colors);
            g2.setPaint(p);
        } else {
            g2.setColor(c.getBackground());
        }

        String segmentPosition = (String) ((JComponent) c).getClientProperty("JButton.segmentPosition");
        if (segmentPosition == null) {
            if (isToolbarButton(c)) {
                g2.fillRect(0, 0, c.getWidth(), c.getHeight());
            } else {
                ComponentUtil.fastFill(g2, (RoundRectangle2D) rect);
            }
        } else {
            RoundRectangle2D externalShape = ComponentUtil.getExternalPaintableShape(c);
            Rectangle bounds = externalShape.getBounds();
            int x = bounds.x, y = bounds.y, w = bounds.width, h = bounds.height;
            int roundCorner = ComponentUtil.getRoundedExteriorCorner(c);

            if (segmentPosition.equals("first")) {
                Rectangle2D rightRect = new Rectangle2D.Double(x + roundCorner / 2, y, w - roundCorner / 2, h);
                Area area = new Area(rect);
                area.add(new Area(rightRect));
                g2.fill(area);
            } else if (segmentPosition.equals("middle")) {
                g2.fillRect(x, y, w, h);
            } else if (segmentPosition.equals("last")) {
                Rectangle2D r3 = new Rectangle2D.Double(x, y, w - roundCorner / 2, h);
                Area area = new Area(rect);
                area.add(new Area(r3));
                g2.fill(area);
            }
        }

        g2.setComposite(oldComposite);
    }

    @Override
    protected void paintButtonPressed(Graphics g, AbstractButton b) {
        super.paintButtonPressed(g, b);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.BLACK);
        g2.setComposite(AlphaComposite.SrcOver.derive(0.2f));

        RoundRectangle2D shape = null;
        String segmentPosition = (String) b.getClientProperty("JButton.segmentPosition");
        if (/*b.getBorder() instanceof PBorder && */segmentPosition == null) {
            if (isToolbarButton(b)) {
                g2.fillRect(0, 0, b.getWidth(), b.getHeight());
            } else {
                shape = ComponentUtil.getRoundedInteriorShape(b);
                ComponentUtil.fastFill(g2, shape);
            }
        } else if (segmentPosition != null) {
            Shape rect = getShape(b);
            RoundRectangle2D externalShape = ComponentUtil.getExternalPaintableShape(b);
            Rectangle bounds = externalShape.getBounds();
            int x = bounds.x, y = bounds.y, w = bounds.width, h = bounds.height;
            int roundCorner = ComponentUtil.getRoundedExteriorCorner(b);

            if (segmentPosition.equals("first")) {
                Rectangle2D rightRect = new Rectangle2D.Double(x + roundCorner / 2, y, w - roundCorner / 2, h);
                Area area = new Area(rect);
                area.add(new Area(rightRect));
                g2.fill(area);
            } else if (segmentPosition.equals("middle")) {
                g2.fillRect(x, y, w, h);
            } else if (segmentPosition.equals("last")) {
                Rectangle2D r3 = new Rectangle2D.Double(x, y, w - roundCorner / 2, h);
                Area area = new Area(rect);
                area.add(new Area(r3));
                g2.fill(area);
            }
        }


        g2.dispose();
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

    public static boolean isToolbarButton(JComponent c) {
        Boolean bool = (Boolean) c.getClientProperty("isToolbar");
        if(bool != null && bool == true) return true;
        return (c.getParent() instanceof JToolBar);
    }

    public static boolean isIconButton(JComponent c) {
        if (c instanceof AbstractButton) {
            String text = ((AbstractButton) c).getText();
            return text == null || text.isEmpty();
        }
        return false;
    }

    public static boolean isSpecialButton(JComponent c) {
        boolean contentAreaFilled = true;
        if (c instanceof AbstractButton) {
            contentAreaFilled = ((AbstractButton) c).isContentAreaFilled();
        }
        return isToolbarButton(c) || isIconButton(c) || !contentAreaFilled;
    }
}
