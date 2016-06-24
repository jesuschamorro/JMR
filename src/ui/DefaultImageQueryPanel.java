package jmr.ui;

import jmr.query.Query;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.*;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

/**
 * <p>Title: JMR Project</p>
 *
 * <p>Description: Java Multimedia Retrieval API</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: University of Granada</p>
 *
 * @author Jesus Chamorro Martinez
 * @version 1.0
 */
public class DefaultImageQueryPanel extends ImageQueryPanel {

  public DefaultImageQueryPanel() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public Query createQuery() {
    return null;
  }

  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    toolPanel.setLayout(gridLayout1);
    gridLayout1.setColumns(1);
    gridLayout1.setRows(2);
    toolPanel.setPreferredSize(new Dimension(0, 90));
    descriptorsPanel.setBorder(BorderFactory.createRaisedBevelBorder());
    descriptorsPanel.setPreferredSize(new Dimension(10, 38));
    descriptorsPanel.setLayout(flowLayout1);
    colorDescriptorPanel.setPreferredSize(new Dimension(92, 40));
    lblColorDescriptor.setPreferredSize(new Dimension(77, 10));
    lblColorDescriptor.setHorizontalAlignment(SwingConstants.CENTER);
    lblColorDescriptor.setText("Color Descriptor");
    cmbColorDescriptor.setPreferredSize(new Dimension(80, 20));
    cmbColorDescriptor.setToolTipText("Texture Descriptor");
    textureDescriptorPanel.setPreferredSize(new Dimension(92, 40));
    lblTextureDescriptor.setPreferredSize(new Dimension(90, 10));
    lblTextureDescriptor.setHorizontalAlignment(SwingConstants.CENTER);
    lblTextureDescriptor.setText("Texture Descriptor");
    cmbTextureDescriptor.setPreferredSize(new Dimension(80, 20));
    cmbTextureDescriptor.setToolTipText("Texture Descriptor");
    shapeDescriptorPanel.setPreferredSize(new Dimension(92, 40));
    lblShapeDescriptor.setPreferredSize(new Dimension(90, 10));
    lblShapeDescriptor.setHorizontalAlignment(SwingConstants.CENTER);
    lblShapeDescriptor.setText("Shape Descriptor");
    cmbShapeDescriptor.setPreferredSize(new Dimension(80, 20));
    cmbShapeDescriptor.setToolTipText("Texture Descriptor");
    flowLayout1.setHgap(9);
    flowLayout1.setVgap(0);
    imageInfoPanel.setBorder(BorderFactory.createEtchedBorder());
    lblName.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
    lblName.setText("Name: ");
    lblImageName.setPreferredSize(new Dimension(100, 14));
    lblImageSize.setPreferredSize(new Dimension(60, 14));
    lblSize.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
    lblSize.setText("Size: ");
    lblType.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
    lblType.setText("Type: ");
    lblImageType.setPreferredSize(new Dimension(30, 14));
    btnOpenImage.setText("Open");
    btnOpenImage.addActionListener(new
                                   DefaultImageQueryPanel_btnOpenImage_actionAdapter(this));
    this.add(imageContainerPanel, java.awt.BorderLayout.CENTER);
    this.add(toolPanel, java.awt.BorderLayout.SOUTH);
    toolPanel.add(imageInfoPanel);
    imageInfoPanel.add(lblName);
    imageInfoPanel.add(lblImageName);
    imageInfoPanel.add(lblSize);
    imageInfoPanel.add(lblImageSize);
    imageInfoPanel.add(lblType);
    imageInfoPanel.add(lblImageType);
    imageInfoPanel.add(btnOpenImage);
    toolPanel.add(descriptorsPanel);
    colorDescriptorPanel.setLayout(gridLayout1);
    textureDescriptorPanel.setLayout(gridLayout1);
    shapeDescriptorPanel.setLayout(gridLayout1);
    descriptorsPanel.add(colorDescriptorPanel);
    descriptorsPanel.add(textureDescriptorPanel);
    descriptorsPanel.add(shapeDescriptorPanel);
    shapeDescriptorPanel.add(lblShapeDescriptor);
    shapeDescriptorPanel.add(cmbShapeDescriptor);
    textureDescriptorPanel.add(lblTextureDescriptor);
    textureDescriptorPanel.add(cmbTextureDescriptor);
    colorDescriptorPanel.add(lblColorDescriptor);
    colorDescriptorPanel.add(cmbColorDescriptor);
    //imageContainerPanel.add(imagePanel, BorderLayout.CENTER);
    imageContainerPanel.add(imagePanel);
  }
  ImagePanel imagePanel = new ImagePanel();
  JScrollPane imageContainerPanel = new JScrollPane();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel toolPanel = new JPanel();
  GridLayout gridLayout1 = new GridLayout();
  JPanel descriptorsPanel = new JPanel();
  JPanel colorDescriptorPanel = new JPanel();
  JLabel lblColorDescriptor = new JLabel();
  JComboBox cmbColorDescriptor = new JComboBox();
  JPanel textureDescriptorPanel = new JPanel();
  JLabel lblTextureDescriptor = new JLabel();
  JComboBox cmbTextureDescriptor = new JComboBox();
  JPanel shapeDescriptorPanel = new JPanel();
  JLabel lblShapeDescriptor = new JLabel();
  JComboBox cmbShapeDescriptor = new JComboBox();
  FlowLayout flowLayout1 = new FlowLayout();
  JPanel imageInfoPanel = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JLabel lblName = new JLabel();
  JLabel lblImageName = new JLabel();
  JLabel lblImageSize = new JLabel();
  JLabel lblSize = new JLabel();
  JLabel lblType = new JLabel();
  JLabel lblImageType = new JLabel();
  JButton btnOpenImage = new JButton();

  public void btnOpenImage_actionPerformed(ActionEvent e) {
    openImage();
    if(imagePanel!=null){
      lblImageName.setText(imagePanel.name);
      lblImageSize.setText(Integer.toString(imagePanel.x)+"x"+Integer.toString(imagePanel.y));
      lblImageType.setText("type");
    }
  }

  public void openImage(){
    JFileChooser jfchooser = new JFileChooser();
    jfchooser.setCurrentDirectory(new File(".//"));

    jfchooser.setDialogTitle("Open image");
    jfchooser.setDialogType(JFileChooser.OPEN_DIALOG);

    int returnVal = jfchooser.showOpenDialog(this);

    if(returnVal == JFileChooser.APPROVE_OPTION){
      File file = jfchooser.getSelectedFile();
      try {
        BufferedImage img = ImageIO.read(file);
        if(img!=null){
          imageContainerPanel.getViewport().add(imagePanel, null);
          imagePanel.setName(file.getName());
          imagePanel.setImage(img);
          imagePanel.setPreferredSize(new Dimension(img.getWidth(),img.getHeight()));
          imageContainerPanel.revalidate();
        }
      }
      catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
  }
}


class ImagePanel extends JPanel {
  BufferedImage img;
  String name;
  int x, y;
  //BorderLayout borderLayout1 = new BorderLayout();
  BufferedImage bf;

  public ImagePanel(){
    //this.setBorder(BorderFactory.createLineBorder(Color.black));
    //this.setLayout(borderLayout1);
  }

  public ImagePanel(BufferedImage i, String n) {
    img=i;
    y=img.getHeight();
    x=img.getWidth();
    name = n;
  }

  public ImagePanel(BufferedImage i) {
    img=i;
    y=img.getHeight(this);
    x=img.getWidth(this);
    repaint();
  }


  public void paint (Graphics g){
    //Image img = getToolkit().getImage(path);
    if(img!=null)
      g.drawImage(img, 1, 1, getSize().width, getSize().height, this );
  }

  public void setImage(BufferedImage i){
    img=i;
    y=img.getHeight();
    x=img.getWidth();
    repaint();
  }

  public String getName(){
    return name;
  }

  public void setName(String s){
    name=s;
  }
}

class DefaultImageQueryPanel_btnOpenImage_actionAdapter
    implements ActionListener {
  private DefaultImageQueryPanel adaptee;
  DefaultImageQueryPanel_btnOpenImage_actionAdapter(DefaultImageQueryPanel
      adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.btnOpenImage_actionPerformed(e);
  }
}
