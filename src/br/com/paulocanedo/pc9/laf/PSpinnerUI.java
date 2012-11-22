/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.paulocanedo.pc9.laf;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSpinnerUI;

/**
 *
 * @author paulocanedo
 */
public class PSpinnerUI extends BasicSpinnerUI {

    public static ComponentUI createUI(JComponent c) {
        return new PSpinnerUI();
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);

        c.putClientProperty("JComponent.roundType", "square");
    }
}
