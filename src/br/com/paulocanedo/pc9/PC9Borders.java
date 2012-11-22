/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.paulocanedo.pc9;

import br.com.paulocanedo.pc9.laf.PBorder;
import br.com.paulocanedo.pc9.laf.PRoundBorder;
import java.awt.Color;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.plaf.UIResource;

/**
 *
 * @author paulocanedo
 */
public class PC9Borders {

    public static class ProgressBarBorder extends PRoundBorder implements UIResource {

        public ProgressBarBorder(Color borderColor) {
            super(new InsetsUIResource(1, 2, 1, 2), borderColor);
        }
    }

    public static class ButtonBorder extends PBorder implements UIResource {

        public ButtonBorder() {
            super();
        }
    }

    public static class TextFieldBorder extends PBorder implements UIResource {

        public TextFieldBorder() {
            super();
        }
    }

    public static class ToolTipBorder extends PRoundBorder implements UIResource {

        public ToolTipBorder(Color borderColor) {
            super(new InsetsUIResource(0, 0, 0, 0), borderColor);
        }
    }
}
