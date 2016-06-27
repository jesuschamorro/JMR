package jmr.ui;

import javax.swing.JPanel;
import java.util.Collection;
import java.awt.Color;
import jmr.descriptor.VisualLowLevelDescriptor;
import jmr.query.Query;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashSet;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.JColorChooser;
import java.util.Iterator;
import jmr.descriptor.ColorData;
import jmr.descriptor.MediaDescriptor;

/**
 * <p>Title: </p>
 *
 * <p>Description: Visual Panel for JMR</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DefaultColorQueryPanel extends ColorQueryPanel {
  JButton btnSelected;
  BtnPalette_mouseAdapter btnPaletteAdapter = new BtnPalette_mouseAdapter(this);
  BtnSelected_mouseAdapter btnSelectedAdapter = new BtnSelected_mouseAdapter(this);
  JPanel palettePanel = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel selectedFeaturePanel = new JPanel();
  GridLayout gridLayout1 = new GridLayout();
  JPanel basicDefaultColorPanel = new JPanel();
  JPanel moreColorsPanel = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JButton btnMoreColors = new JButton();

  public DefaultColorQueryPanel() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    selectedDescriptors = new LinkedHashSet();
  }

  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    selectedFeaturePanel.setBorder(BorderFactory.createEtchedBorder());
    selectedFeaturePanel.setPreferredSize(new Dimension(10, 80));
    basicDefaultColorPanel.setLayout(gridLayout1);
    gridLayout1.setColumns(6);
    gridLayout1.setHgap(1);
    gridLayout1.setRows(7);
    gridLayout1.setVgap(1);
    palettePanel.setLayout(borderLayout2);
    btnMoreColors.setText("More Colors");
    btnMoreColors.addActionListener(new
                                    DefaultColorPanel_btnMoreColors_actionAdapter(this));
    this.add(selectedFeaturePanel, java.awt.BorderLayout.SOUTH);
    this.add(palettePanel, java.awt.BorderLayout.CENTER);
    palettePanel.add(basicDefaultColorPanel, java.awt.BorderLayout.CENTER);
    palettePanel.add(moreColorsPanel, java.awt.BorderLayout.SOUTH);
    moreColorsPanel.add(btnMoreColors);
    float grado, radio, radio_orig;
    int color;
    JButton btn;
    Color c = new Color(0, 0, 0);

    radio = radio_orig = 2;
    for (grado = 0; grado < 360; grado += 20) {
      radio = radio_orig;
      while (radio > 0) {
        color = Color.HSBtoRGB(grado / 360, radio / radio_orig, 1);
        c = new Color(color);
        btn = new JButton();
        btn.setBackground(c);
        btn.addMouseListener(btnPaletteAdapter);
        basicDefaultColorPanel.add(btn);
        radio--;
      }
    }
    btn = new JButton();
    btn.setBackground(c.BLACK);
    btn.addMouseListener(btnPaletteAdapter);
    basicDefaultColorPanel.add(btn);
    btn = new JButton();
    btn.setBackground(c.DARK_GRAY);
    btn.addMouseListener(btnPaletteAdapter);
    basicDefaultColorPanel.add(btn);
    btn = new JButton();
    btn.setBackground(c.GRAY);
    btn.addMouseListener(btnPaletteAdapter);
    basicDefaultColorPanel.add(btn);
    btn = new JButton();
    btn.setBackground(c.LIGHT_GRAY);
    btn.addMouseListener(btnPaletteAdapter);
    basicDefaultColorPanel.add(btn);
    btn = new JButton();
    btn.setBackground(new Color(230, 230, 230));
    btn.addMouseListener(btnPaletteAdapter);
    basicDefaultColorPanel.add(btn);
    btn = new JButton();
    btn.setBackground(c.WHITE);
    btn.addMouseListener(btnPaletteAdapter);
    basicDefaultColorPanel.add(btn);
  }

  /** Creates a query with selected colors
     * @return A query with selected colors
   */
  public Query createQuery() {
    return null;
  }

  /** Adds VisualLowLevelDescriptors to selected features collection
     * @param c VisualLowLevelDescriptor
     * @return True if add is ok and false on the other case
   */
  public boolean addToSelected(VisualLowLevelDescriptor c) {
    return selectedDescriptors.add(c);
  }

  public boolean removeFromSelected(VisualLowLevelDescriptor c) {
    return selectedDescriptors.remove(c);
  }

  public VisualLowLevelDescriptor getSelected(int pos) {
    ColorData cd = null;
    int i = 0;

    for (Iterator it = selectedDescriptors.iterator(); it.hasNext() && i < pos;
         i++) {
      cd = (ColorData) it.next();
    }
    return cd;
  }

  private void visualizeSelectedDescriptors(Collection c) {
    selectedFeaturePanel.removeAll();
    for (Iterator it = c.iterator(); it.hasNext(); ) {
      ColorData cd = (ColorData) it.next();
      btnSelected = new JButton();
      btnSelected.setBackground(cd.getColor());
      btnSelected.setPreferredSize(new Dimension(20, 20));
      btnSelected.setToolTipText("Push the right button to remove");
      btnSelected.addMouseListener(btnSelectedAdapter);
      selectedFeaturePanel.add(btnSelected);
    }
    selectedFeaturePanel.updateUI();
  }


  public void btnPalette_mouseClicked(MouseEvent e) {
    if (e.getButton() == e.BUTTON1) {
      JButton b = (JButton) e.getSource();
      if (addToSelected(new ColorData(b.getBackground()))) {
        visualizeSelectedDescriptors(selectedDescriptors);
      }
    }
  }


  public void btnSelected_mouseClicked(MouseEvent e) {
    JButton b = (JButton) e.getSource();
    if (e.getButton() == e.BUTTON3) {
      ColorData c = new ColorData(b.getBackground());
      removeFromSelected(c);
    }
    visualizeSelectedDescriptors(selectedDescriptors);
  }

  public void btnMoreColors_actionPerformed(ActionEvent e) {
    JColorChooser moreColors = new JColorChooser();
    Color c = moreColors.showDialog(this, "Color selection", null);
    if (c != null) {
      if (addToSelected(new ColorData(c))) {
        visualizeSelectedDescriptors(selectedDescriptors);
      }
    }
  }

}

class BtnPalette_mouseAdapter extends MouseAdapter {
  private DefaultColorQueryPanel adaptee;
  BtnPalette_mouseAdapter(DefaultColorQueryPanel adaptee) {
    this.adaptee = adaptee;
  }

  public void mouseClicked(MouseEvent e) {
    adaptee.btnPalette_mouseClicked(e);
  }
}

class BtnSelected_mouseAdapter  extends MouseAdapter {
  private DefaultColorQueryPanel adaptee;
  BtnSelected_mouseAdapter(DefaultColorQueryPanel adaptee) {
    this.adaptee = adaptee;
  }

  public void mouseClicked(MouseEvent e) {
    adaptee.btnSelected_mouseClicked(e);
  }
}

class DefaultColorPanel_btnMoreColors_actionAdapter implements ActionListener {
  private DefaultColorQueryPanel adaptee;
  DefaultColorPanel_btnMoreColors_actionAdapter(DefaultColorQueryPanel adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.btnMoreColors_actionPerformed(e);
  }
}
