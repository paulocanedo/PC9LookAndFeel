/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.paulocanedo.pc9.laf;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicListUI;

/**
 *
 * @author paulocanedo
 */
public class PListUI extends BasicListUI {
    
    public static ComponentUI createUI(JComponent list) {
        return new PListUI();
    }

    public PListUI() {
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
    }
    
}
