/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.paulocanedo.pc9.laf;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

/**
 *
 * @author paulocanedo
 */
public class PToggleButtonUI extends PButtonUI {

    private static ComponentUI componentUI;

    public static ComponentUI createUI(JComponent c) {
        if (componentUI == null) {
            componentUI = new PToggleButtonUI();
        }
        return componentUI;
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        super.paint(g, c);

        AbstractButton b = (AbstractButton) c;
        ButtonModel model = b.getModel();
        if (model.isArmed() && model.isPressed() || model.isSelected()) {
            paintButtonPressed(g, b);
        }
    }
}
