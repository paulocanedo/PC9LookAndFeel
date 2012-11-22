/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.paulocanedo.pc9.util;

import br.com.paulocanedo.pc9.laf.PButtonUI;
import br.com.paulocanedo.pc9.laf.PRoundBorder;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.plaf.ButtonUI;

/**
 *
 * @author paulocanedo
 */
public class AddRemoveButtonUI extends PButtonUI {

    private boolean addFlag;
    private String oldText;
    private static ButtonUI addInstanceUI;
    private static ButtonUI removeInstanceUI;

    private AddRemoveButtonUI(boolean plusFlag) {
        this.addFlag = plusFlag;
    }

    public static ButtonUI getAddInstanceUI() {
        if (addInstanceUI == null) {
            addInstanceUI = new AddRemoveButtonUI(true);
        }
        return addInstanceUI;
    }

    public static ButtonUI getRemoveInstanceUI() {
        if (removeInstanceUI == null) {
            removeInstanceUI = new AddRemoveButtonUI(false);
        }
        return removeInstanceUI;
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);

        c.putClientProperty("maxRoundCorner", 5);
    }

    @Override
    protected void installDefaults(AbstractButton b) {
        super.installDefaults(b);
        b.setBorder(new PRoundBorder(new Insets(1, 1, 1, 1), Color.DARK_GRAY));

        int size = 8;

        b.setIcon(createIcon(size, addFlag));
        b.setText("");
    }

    public static ImageIcon createIcon(int size, boolean addFlag) {
        int x = 0, y = 0, w = size, h = size;
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2 = (Graphics2D) image.getGraphics().create();

        g2.setStroke(new BasicStroke(2f));
        g2.setColor(Color.DARK_GRAY);
        g2.drawLine(x, y + h / 2, x + w, y + h / 2);
        if (addFlag) {
            g2.drawLine(x + w / 2, y, x + w / 2, y + h);
        }
        return new ImageIcon(image);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        super.paint(g, c);
    }

    @Override
    public void uninstallUI(JComponent c) {
        super.uninstallUI(c);

        c.putClientProperty("maxRoundCorner", null);

        ((AbstractButton) c).setText(oldText);
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        return new Dimension(24, 24);
    }
}
