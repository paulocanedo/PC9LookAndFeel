/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.paulocanedo.pc9.laf;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalTreeUI;
import javax.swing.tree.TreeCellEditor;

/**
 *
 * @author paulocanedo
 */
public class PTreeUI extends MetalTreeUI {
    
    public static ComponentUI createUI(JComponent c) {
        System.out.println("t");
        return new PTreeUI();
    }

    public PTreeUI() {
    }

    @Override
    protected TreeCellEditor createDefaultCellEditor() {
        System.out.println("cdce");
        return super.createDefaultCellEditor();
    }

    
    
}
