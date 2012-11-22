/*
 *  Copyright 2011 - Paulo Canedo Costa Rodrigues
 * 
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package br.com.paulocanedo.pc9.laf;

import br.com.paulocanedo.pc9.PC9Borders;
import br.com.paulocanedo.pc9.util.ComponentUtil;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.UIDefaults;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource.LineBorderUIResource;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;

/**
 *
 * @author paulocanedo
 */
public class PC9LookAndFeel extends MetalLookAndFeel {

    public PC9LookAndFeel() {
    }

    @Override
    protected void initClassDefaults(UIDefaults table) {
        super.initClassDefaults(table);

        final String prefix = "br.com.paulocanedo.pc9.laf.";

        Object[] uiDefaults = {
            "ButtonUI", prefix + "PButtonUI",
            "CheckBoxUI", prefix + "PCheckBoxUI",
            "ComboBoxUI", prefix + "PComboBoxUI",
            "FileChooserUI", prefix + "PFileChooserUI",
            "LabelUI", prefix + "PLabelUI",
            "ProgressBarUI", prefix + "PProgressBarUI",
            "RadioButtonUI", prefix + "PRadioButtonUI",
            "ScrollBarUI", prefix + "PScrollBarUI",
            "SeparatorUI", prefix + "PSeparatorUI",
            "SpinnerUI", prefix + "PSpinnerUI",
            "TabbedPaneUI", prefix + "PTabbedPaneUI",
            "TextFieldUI", prefix + "PTextFieldUI",
            "TableUI", prefix + "PTableUI",
            "PanelUI", prefix + "PPanelUI",
            "PasswordFieldUI", prefix + "PPasswordFieldUI",
            "ToggleButtonUI", prefix + "PToggleButtonUI",
            "ToolTipUI", prefix + "PToolTipUI",
            "TreeUI", prefix + "PTreeUI",};

        table.putDefaults(uiDefaults);
    }

    @Override
    public String getName() {
        return "PC9 LookAndFeel";
    }

    @Override
    public String getID() {
        return "pc9LAF";
    }

    @Override
    public String getDescription() {
        return "A modern look and feel cross platform for Java";
    }

    @Override
    public boolean isNativeLookAndFeel() {
        return false;
    }

    @Override
    public boolean isSupportedLookAndFeel() {
        return true;
    }
    
    public static Font getUbuntuFont() {
        Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
        for (Font f : fonts) {
            if (f.getName().equalsIgnoreCase("ubuntu") || f.getName().equalsIgnoreCase("arial") || f.getName().equalsIgnoreCase("helvetica")) {
                return f;
            }
        }
        return null;
    }

    @Override
    protected void initComponentDefaults(UIDefaults table) {
        super.initComponentDefaults(table);
        registerUbuntuFonts();

        Font defaultFont = null;

        Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
        for (Font f : fonts) {
            if (f.getName().equalsIgnoreCase("ubuntu") || f.getName().equalsIgnoreCase("arial") || f.getName().equalsIgnoreCase("helvetica")) {
                defaultFont = f;
            }
        }

        Color control = new ColorUIResource(0xcdd1cf);
        Color controlShadow = getControlShadow();
        Color controlDarkShadow = getControlDarkShadow();
        Color controlTextColor = getControlTextColor();
        Color focusColor = new ColorUIResource(0xc0c4c2);

        Color textHighlight = new ColorUIResource(0x2F75D9);
        Color textHighlightText = ColorUIResource.WHITE;

        Color inactiveControlTextColor = getInactiveControlTextColor();
        Color primaryControl = getPrimaryControl();
        Color primaryControlDarkShadow = getPrimaryControlDarkShadow();
        Color primaryControlShadow = new ColorUIResource(0x1842f0);
        Color menuBackground = getMenuBackground();
        Color menuSelectedBackground = new ColorUIResource(175, 210, 244);
        Color menuDisabledForeground = getMenuDisabledForeground();
        Color menuSelectedForeground = ColorUIResource.DARK_GRAY;
        Color systemTextColor = getSystemTextColor();

        Insets zeroInsets = new InsetsUIResource(0, 0, 0, 0);

        Integer zero = new Integer(0);

        Border textFieldBorder = new PC9Borders.TextFieldBorder();

//        Object scrollPaneBorder = new SwingLazyValue("javax.swing.plaf.metal.MetalBorders$ScrollPaneBorder");
        Object buttonBorder = new PC9Borders.TextFieldBorder();

        int paddingCB = ComponentUtil.DEFAULT_ROUND_CORNER;
        Border border1 = BorderFactory.createEmptyBorder(0, paddingCB, 0, paddingCB);
        Border border2 = BorderFactory.createLineBorder(ColorUIResource.GRAY);
        Border comboBoxBorder = BorderFactory.createCompoundBorder(border1, border2);
//        Object comboBoxBorder = new PC9Borders.ComboBoxBorder(Color.GRAY);

//        Object toggleButtonBorder =  new SwingLazyValue("javax.swing.plaf.metal.MetalBorders", "getToggleButtonBorder");
        Object toggleButtonBorder = buttonBorder;

//        Object titledBorderBorder = new SwingLazyValue(
//			  "javax.swing.plaf.BorderUIResource$LineBorderUIResource",
//			  new Object[] {controlShadow});

//        Object desktopIconBorder = new SwingLazyValue("javax.swing.plaf.metal.MetalBorders", "getDesktopIconBorder");

//        Object menuBarBorder = new SwingLazyValue("javax.swing.plaf.metal.MetalBorders$MenuBarBorder");

//        Object popupMenuBorder = new SwingLazyValue("javax.swing.plaf.metal.MetalBorders$PopupMenuBorder");

//        Object menuItemBorder = new SwingLazyValue("javax.swing.plaf.metal.MetalBorders$MenuItemBorder");

//	Object menuItemAcceleratorDelimiter = "-";
//        Object toolBarBorder = new SwingLazyValue("javax.swing.plaf.metal.MetalBorders$ToolBarBorder");

        Object progressBarBorder = new PC9Borders.ProgressBarBorder(ColorUIResource.DARK_GRAY);

//        Object toolTipBorder = new SwingLazyValue("javax.swing.plaf.BorderUIResource$LineBorderUIResource", new Object[] {primaryControlDarkShadow});

//        Object toolTipBorderInactive = new SwingLazyValue("javax.swing.plaf.BorderUIResource$LineBorderUIResource", new Object[] {controlDarkShadow});

//        Object focusCellHighlightBorder = new SwingLazyValue("javax.swing.plaf.BorderUIResource$LineBorderUIResource", new Object[] {focusColor});

//        Object tabbedPaneTabAreaInsets = new InsetsUIResource(4, 2, 0, 6);

//        Object tabbedPaneTabInsets = new InsetsUIResource(0, 9, 1, 9);

//	final Object[] internalFrameIconArgs = new Object[1];
//	internalFrameIconArgs[0] = new Integer(16);

//	Object[] defaultCueList = new Object[] {
//		"OptionPane.errorSound",
//		"OptionPane.informationSound",
//		"OptionPane.questionSound",
//		"OptionPane.warningSound" };

//        MetalTheme theme = getCurrentTheme();
//        Object menuTextValue = new FontActiveValue(theme,
//                                                   MetalTheme.MENU_TEXT_FONT);
        Object controlTextValue = defaultFont.deriveFont(12f);
        Object userTextValue = defaultFont.deriveFont(12f);
//        Object windowTitleValue = new FontActiveValue(theme,
//                               MetalTheme.WINDOW_TITLE_FONT);
//        Object subTextValue = new FontActiveValue(theme,
//                                                  MetalTheme.SUB_TEXT_FONT);
//        Object systemTextValue = new FontActiveValue(theme,
//                                                 MetalTheme.SYSTEM_TEXT_FONT);

        List<Color> scrollbarGradient = new ArrayList<Color>();
        scrollbarGradient.add(ColorUIResource.BLACK);
        scrollbarGradient.add(ColorUIResource.DARK_GRAY);
        scrollbarGradient.add(ColorUIResource.LIGHT_GRAY);

        //
        // DEFAULTS TABLE
        //

        Object[] defaults = {
            "borderFocusColor", new ColorUIResource(0x0066cc),
            "TextField.border", textFieldBorder,
            "TextField.selectionBackground", textHighlight,
            "TextField.selectionForeground", textHighlightText,
            "TextField.font", userTextValue,
            "PasswordField.border", textFieldBorder,
            "PasswordField.font", userTextValue,
            "PasswordField.echoChar", (char) 0x2022,
            "TextArea.font", userTextValue,
            "TextPane.background", table.get("window"),
            "TextPane.font", userTextValue,
            "EditorPane.selectionBackground", textHighlight,
            "EditorPane.font", userTextValue,
            "FormattedTextField.border", textFieldBorder,
            "Button.background", control,
            "Button.defaultButtonFollowsFocus", Boolean.FALSE,
            "Button.disabledText", inactiveControlTextColor,
            "Button.select", controlShadow,
            "Button.border", new PC9Borders.ButtonBorder(),
            "Button.font", controlTextValue,
            "Button.focus", focusColor,
            "Button.margin", new InsetsUIResource(6, 16, 6, 16),
            "CheckBox.icon", new PCheckBoxUI.CheckBoxIcon(14),
            "CheckBox.disabledText", inactiveControlTextColor,
            "Checkbox.select", controlShadow,
            "CheckBox.font", controlTextValue,
            "CheckBox.focus", focusColor,
            "CheckBox.totalInsets", new Insets(4, 4, 4, 4),
            "RadioButton.icon", new PRadioButtonUI.RadioIcon(14),
            "RadioButton.disabledText", inactiveControlTextColor,
            "RadioButton.select", controlShadow,
            "RadioButton.font", controlTextValue,
            "RadioButton.focus", focusColor,
            "RadioButton.totalInsets", new Insets(4, 4, 4, 4),
            "ToggleButton.select", controlShadow,
            "ToggleButton.disabledText", inactiveControlTextColor,
            "ToggleButton.focus", focusColor,
            "ToggleButton.border", toggleButtonBorder,
            "ToggleButton.font", controlTextValue,
            "ToolTip.font", controlTextValue,
            "ToolTip.background", new ColorUIResource(0xfafabb),
            "ToolTip.foreground", ColorUIResource.BLACK,
            "ToolTip.border", new LineBorderUIResource(Color.GRAY),
            //            "ToolTip.border", new PC9Borders.ToolTipBorder(ColorUIResource.GRAY),
            "ToolTip.backgroundInactive", control,
            "ToolTip.foregroundInactive", controlDarkShadow,
            "ToolTip.hideAccelerator", Boolean.FALSE,
            "ToolTipManager.enableToolTipMode", "activeApplication",
            "ComboBox.border", comboBoxBorder,
            "ComboBox.background", table.get("window"),
            "ComboBox.foreground", controlTextColor,
            "ComboBox.selectionBackground", primaryControlShadow,
            "ComboBox.selectionForeground", ColorUIResource.WHITE,
            "ComboBox.font", controlTextValue,
            "DesktopIcon.font", controlTextValue,
            "DesktopIcon.foreground", controlTextColor,
            "DesktopIcon.background", control,
            "DesktopIcon.width", new Integer(160),
            "Label.font", controlTextValue,
            "Label.foreground", systemTextColor,
            "Label.disabledForeground", getInactiveSystemTextColor(),
            "List.selectionBackground", textHighlight,
            "List.selectionForeground", textHighlightText,
            "ProgressBar.font", userTextValue,
            "ProgressBar.border", progressBarBorder,
            "ProgressBar.background", ColorUIResource.WHITE,
            "ProgressBar.foreground", new ColorUIResource(0x599DDD),
            "ProgressBar.selectionBackground", new ColorUIResource(0x599DDD),
            "ProgressBar.selectionForeground", ColorUIResource.WHITE,
            "ProgressBar.repaintInterval", new Integer(100),
            "Table.font", userTextValue,
            "TableHeader.font", userTextValue,
            "ScrollBar.thumb", control,
            "ScrollBar.thumbShadow", control,
            "ScrollBar.thumbDarkShadow", ColorUIResource.BLACK,
            "ScrollBar.thumbHighlight", control,
            "ScrollBar.width", new Integer(8),
            "MenuBar.font", controlTextValue,
            "Menu.font", controlTextValue,
            "Menu.selectionForeground", menuSelectedForeground,
            "Menu.selectionBackground", menuSelectedBackground,
            "MenuItem.font", controlTextValue,
            "MenuItem.selectionForeground", menuSelectedForeground,
            "MenuItem.selectionBackground", menuSelectedBackground,
            "PopupMenu.font", userTextValue, // CB & RB Menu Item
            "CheckBoxMenuItem.checkIcon", new PCheckBoxUI.CheckBoxIcon(11),
            "CheckBoxMenuItem.font", userTextValue,
            "CheckBoxMenuItem.selectionForeground", menuSelectedForeground,
            "CheckBoxMenuItem.selectionBackground", menuSelectedBackground,
            "RadioButtonMenuItem.font", userTextValue,
            "RadioButtonMenuItem.selectionForeground", menuSelectedForeground,
            "RadioButtonMenuItem.selectionBackground", menuSelectedBackground,
            "RadioButtonMenuItem.checkIcon", new PRadioButtonUI.RadioIcon(11),
            "Tree.font", userTextValue,
            "Tree.selectionForeground", ColorUIResource.WHITE,
            "Tree.selectionBackground", primaryControlShadow,
            "Tree.selectionBorderColor", primaryControlShadow,
            "TabbedPane.font", userTextValue,};

        table.putDefaults(defaults);
    }

    private static void registerUbuntuFonts() {
        try {
            Class c = PC9LookAndFeel.class;
            String base = "/br/com/paulocanedo/third/fonts/";
            
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, c.getResourceAsStream(base + "Ubuntu-B.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, c.getResourceAsStream(base + "Ubuntu-BI.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, c.getResourceAsStream(base + "Ubuntu-C.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, c.getResourceAsStream(base + "Ubuntu-L.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, c.getResourceAsStream(base + "Ubuntu-LI.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, c.getResourceAsStream(base + "Ubuntu-M.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, c.getResourceAsStream(base + "Ubuntu-MI.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, c.getResourceAsStream(base + "Ubuntu-R.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, c.getResourceAsStream(base + "Ubuntu-RI.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, c.getResourceAsStream(base + "Ubuntu-B.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, c.getResourceAsStream(base + "Ubuntu-BI.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, c.getResourceAsStream(base + "Ubuntu-R.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, c.getResourceAsStream(base + "Ubuntu-RI.ttf")));
        } catch (Throwable ex) {
            Logger.getLogger(PC9LookAndFeel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
