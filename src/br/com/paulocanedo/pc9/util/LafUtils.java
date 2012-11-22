/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.paulocanedo.pc9.util;

import java.awt.Component;
import java.awt.Container;
import javax.swing.JComponent;

/**
 *
 * @author paulocanedo
 */
public class LafUtils {

    /**
     * this will affects children components
     * @param component
     */
    public static void applyRecursiveMaxRoundCorner(JComponent component, int maxRoundCorner) {
        applyMaxRoundCorner(component, maxRoundCorner);
        for (Component c : component.getComponents()) {
            if (c instanceof JComponent) {
                applyRecursiveMaxRoundCorner((JComponent) c, maxRoundCorner);
            }
        }
    }

    public static void applyMaxRoundCorner(JComponent component, int maxRoundCorner) {
        component.putClientProperty("maxRoundCorner", maxRoundCorner);
    }

}
