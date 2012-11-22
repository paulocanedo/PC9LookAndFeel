/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.paulocanedo.pc9.util;

import javax.swing.JComponent;

/**
 *
 * @author paulocanedo
 */
public class ClientPropertyUtil {

    private ClientPropertyUtil() {
    }

    public static boolean isSquare(JComponent c) {
        String clientProperty = getClientPropertyAsString(c, "JComponent.type");

        return isEquals(clientProperty, "square");
    }

    public static boolean isRoundRect(JComponent c) {
        String clientProperty = getClientPropertyAsString(c, "JComponent.type");

        return isEquals(clientProperty, "roundRect");
    }

    public static boolean isNormalType(JComponent c) {
        String clientProperty = getClientPropertyAsString(c, "JComponent.type");

        return isEquals(clientProperty, "normal");
    }

    public static boolean isCustomType(JComponent c) {
        return ((!isSquare(c)) && (!isRoundRect(c)) && (!isNormalType(c)));
    }

    public static boolean isFirstPartButton(JComponent c) {
        String clientProperty = getClientPropertyAsString(c, "JButton.segmentPosition");

        return isEquals(clientProperty, "first");
    }

    public static String getClientPropertyAsString(JComponent c, String property) {
        return (String) c.getClientProperty(property);
    }

    public static Integer getClientPropertyAsInteger(JComponent c, String property) {
        Object clientProperty = c.getClientProperty(property);
        return clientProperty == null ? null : (Integer) clientProperty;
    }

    private static boolean isEquals(Object o1, Object o2) {
        return isNotNull(o1) && isNotNull(o2) && o1.equals(o2);
    }

    private static boolean isNotNull(Object o) {
        return o != null;
    }
}
