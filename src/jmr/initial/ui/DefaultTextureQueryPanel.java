package jmr.initial.ui;

import javax.swing.JPanel;
import java.util.Collection;
import jmr.initial.descriptor.VisualLowLevelDescriptor;
import jmr.initial.descriptor.TextureDescriptor;
import jmr.query.Query;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.util.LinkedHashSet;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.JColorChooser;
import java.util.Iterator;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.Icon;
import java.awt.image.BufferedImage;
import jmr.initial.media.JMRBufferedImage;
import java.awt.MediaTracker;

/**
 * <p>Title: VisualPanel</p>
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

public class DefaultTextureQueryPanel extends TextureQueryPanel {

  JButton BtnSelectedTexture;
  BtnPaletteTexture_mouseAdapter BtnPaletteTextureAdapter = new BtnPaletteTexture_mouseAdapter(this);
  BtnSelectedTexture_mouseAdapter BtnSelectedTextureAdapter = new BtnSelectedTexture_mouseAdapter(this);
  JPanel palettePanel = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel selectedFeaturePanel = new JPanel();
  GridLayout gridLayout1 = new GridLayout();
  JPanel basicDefaultTexturePanel = new JPanel();
  JPanel TextureDescriptorPanel = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JColorChooser masColores = new JColorChooser();
  JComboBox cmbTextureDescriptor = new JComboBox();

  public DefaultTextureQueryPanel() {
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
    basicDefaultTexturePanel.setLayout(gridLayout1);
    gridLayout1.setColumns(3);
    gridLayout1.setHgap(2);
    gridLayout1.setRows(3);
    gridLayout1.setVgap(2);
    palettePanel.setLayout(borderLayout2);
    cmbTextureDescriptor.setPreferredSize(new Dimension(100, 20));
    this.add(selectedFeaturePanel, java.awt.BorderLayout.SOUTH);
    this.add(palettePanel, java.awt.BorderLayout.CENTER);
    palettePanel.add(basicDefaultTexturePanel, java.awt.BorderLayout.CENTER);
    palettePanel.add(TextureDescriptorPanel, java.awt.BorderLayout.SOUTH);
    TextureDescriptorPanel.add(cmbTextureDescriptor);
    JButton btn;
    String[] listImages = {
        (this.getClass().getResource("images//TexturaCesped.jpg")).getPath(),
        (this.getClass().getResource("images//TexturaBarro.jpg")).getPath(),
        (this.getClass().getResource("images//TexturaPiedra.jpg")).getPath(),
        (this.getClass().getResource("images//TexturaH.jpg")).getPath(),
        (this.getClass().getResource("images//TexturaLadrillo.jpg")).getPath(),
        (this.getClass().getResource("images//TexturaCielo.jpg")).getPath(),
        (this.getClass().getResource("images//TexturaCafe.jpg")).getPath(),
        (this.getClass().getResource("images//TexturaPelo.jpg")).getPath(),
        (this.getClass().getResource("images//TexturaV.jpg")).getPath()};

    for (int i = 0; i < listImages.length; i++) {

      System.out.println(listImages[i]);

      btn = new JButton(new ImageIcon("file://"+listImages[i]));
      btn.addMouseListener(BtnPaletteTextureAdapter);
      basicDefaultTexturePanel.add(btn);
    }
  }

  public Query createQuery() {
    return null;
  }

  public boolean addToSelected(VisualLowLevelDescriptor c) {
    return selectedDescriptors.add(c);
  }

  public boolean removeFromSelected(VisualLowLevelDescriptor c) {
      return selectedDescriptors.remove(c);
    }

  public VisualLowLevelDescriptor getSelected(int pos) {
    TextureDescriptor td = null;
    int i = 0;
    for (Iterator it = selectedDescriptors.iterator(); it.hasNext() && i < pos;
         i++) {
      td = (TextureDescriptor) it.next();
    }
    return td;

  }

  private void visualizeSelectedDescriptors(Collection c) {
    //FALTA IMPLEMENTAR VISUALIZACI�N DE TEXTURAS SELECCIONADAS
    /*selectedFeaturePanel.removeAll();
             for( Iterator it = c.iterator(); it.hasNext(); ){
        TextureDescriptor td = (TextureDescriptor) it.next();
        BtnSelectedTexture = new JButton();
        //BtnSelectedTexture.setBackground(cd.getColor());
        BtnSelectedTexture.setPreferredSize(new Dimension(20,20));
        BtnSelectedTexture.setToolTipText("Right Button to remove");
        BtnSelectedTexture.addMouseListener(BtnSelectedTextureAdapter);
        selectedFeaturePanel.add(BtnSelectedTexture);
             }
             selectedFeaturePanel.updateUI();*/

  }


  public void BtnPaletteTexture_mouseClicked(MouseEvent e) {
    if (e.getButton() == e.BUTTON1) {
      JButton b = (JButton) e.getSource();
      Icon ic = b.getIcon();
      JMRBufferedImage buff = new JMRBufferedImage(ic.getIconWidth(),
                                             ic.getIconHeight(),
                                             BufferedImage.TYPE_INT_RGB);
      ic.paintIcon(this, buff.createGraphics(), 0, 0);

      //HAY QUE CREAR EL TEXTURE DESCRIPTOR CON EL BUFFEREDIMAGE....
      //if(addToSelected(new TextureDescriptor()))
      //    visualizeFeatures(selectedDescriptors);
    }
  }


  public void BtnSelectedTexture_mouseClicked(MouseEvent e) {
    /*JButton b = (JButton) e.getSource();
             if (e.getButton() == e.BUTTON3) {
        //HAY QUE CREAR UN OBJETO TEXTUREDESCRIPTOR A PARTIR DEL BOTON VISUALIZADOR
        TextureDescriptor c = new TextureDescriptor();
        removeToSelected(c);
             }
             visualizeFeatures(selectedDescriptors);*/
  }


}

class BtnPaletteTexture_mouseAdapter
    extends MouseAdapter {
  private DefaultTextureQueryPanel adaptee;
  BtnPaletteTexture_mouseAdapter(DefaultTextureQueryPanel adaptee) {
    this.adaptee = adaptee;
  }

  public void mouseClicked(MouseEvent e) {
    adaptee.BtnPaletteTexture_mouseClicked(e);
  }
}

class BtnSelectedTexture_mouseAdapter
    extends MouseAdapter {
  private DefaultTextureQueryPanel adaptee;
  BtnSelectedTexture_mouseAdapter(DefaultTextureQueryPanel adaptee) {
    this.adaptee = adaptee;
  }

  public void mouseClicked(MouseEvent e) {
    adaptee.BtnSelectedTexture_mouseClicked(e);
  }
}
