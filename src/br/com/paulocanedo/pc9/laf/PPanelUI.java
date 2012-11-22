/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.paulocanedo.pc9.laf;

import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicPanelUI;

/**
 *
 * @author paulocanedo
 */
public class PPanelUI extends BasicPanelUI {

    private static ComponentUI componentUI;

    public static ComponentUI createUI(JComponent c) {
        if (componentUI == null) {
            componentUI = new PPanelUI();
        }
        return componentUI;
    }

    public PPanelUI() {
    }

    @Override
    protected void installDefaults(JPanel p) {
        super.installDefaults(p);
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
    }

    @Override
    public void update(Graphics g, JComponent c) {
        if (c.isOpaque()) {
            g.setColor(c.getBackground());
            
            g.fillRect(0, 0, c.getWidth(), c.getHeight());
        }
        paint(g, c);
    }
}
