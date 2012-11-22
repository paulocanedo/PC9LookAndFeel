/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.paulocanedo.pc9.laf;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.util.Locale;
import javax.accessibility.AccessibleContext;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalFileChooserUI;

/**
 *
 * @author paulocanedo
 */
public class PFileChooserUI extends MetalFileChooserUI {

    public static ComponentUI createUI(JComponent c) {
        return new PFileChooserUI((JFileChooser) c);
    }

    public PFileChooserUI(JFileChooser filechooser) {
        super(filechooser);
    }

    @Override
    public void installComponents(JFileChooser fc) {
        super.installComponents(fc);

        normalize(getFileChooser());
    }

    private void normalize(JComponent c) {
        if (c instanceof Container) {
            Container co = (Container) c;
            for (Component c_ : co.getComponents()) {
                if (c_ instanceof JComponent) {
                    normalize((JComponent) c_);
                }
            }
        }

        if (c instanceof AbstractButton) {

            AbstractButton ab = (AbstractButton) c;
            Icon icon = ab.getIcon();
            if (icon != null) {
                c.putClientProperty("JComponent.type", "square");
                String accessibleName = (String) ab.getClientProperty(AccessibleContext.ACCESSIBLE_NAME_PROPERTY);

                Locale l = getFileChooser().getLocale();
//                if (UIManager.getString("FileChooser.listViewButtonAccessibleName", l).equals(accessibleName)) {
//                    ab.putClientProperty("JButton.segmentPosition", "first");
//                } else if (UIManager.getString("FileChooser.detailsViewButtonAccessibleName", l).equals(accessibleName)) {
//                    ab.putClientProperty("JButton.segmentPosition", "last");
//                }
                ab.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                ab.setPreferredSize(new Dimension(32, 24));
            }
        }
    }
}
