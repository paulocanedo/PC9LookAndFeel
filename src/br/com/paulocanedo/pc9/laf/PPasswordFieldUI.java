/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.paulocanedo.pc9.laf;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.text.Element;
import javax.swing.text.PasswordView;
import javax.swing.text.View;

/**
 *
 * @author paulocanedo
 */
public class PPasswordFieldUI extends PTextFieldUI {

    public static ComponentUI createUI(JComponent c) {
        return new PPasswordFieldUI();
    }

    /**
     * Creates a view (PasswordView) for an element.
     *
     * @param elem the element
     * @return the view
     */
    @Override
    public View create(Element elem) {
        return new PasswordView(elem);
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
    }
}
