package jmr.initial.ui;

import jmr.initial.media.Media;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.*;
import jmr.query.Query;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.imageio.ImageIO;
import java.util.ListIterator;
import java.util.LinkedList;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.awt.geom.*;

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
public class DefaultSketchQueryPanel extends SketchQueryPanel {
  private ImageIcon penIcon = new ImageIcon((this.getClass().getResource(".//images//Lapiz.gif")).getPath());
  private ImageIcon lineIcon = new ImageIcon((this.getClass().getResource(".//images//Linea.gif")).getPath());
  private ImageIcon ovalIcon = new ImageIcon((this.getClass().getResource(".//images//Ovalo.gif")).getPath());
  private ImageIcon rubberIcon = new ImageIcon((this.getClass().getResource(".//images//Goma.gif")).getPath());
  private ImageIcon rectangleIcon = new ImageIcon((this.getClass().getResource(".//images//Rectangulo.gif")).getPath());
  private ImageIcon textIcon = new ImageIcon((this.getClass().getResource(".//images//Texto.gif")).getPath());
  private ImageIcon saveIcon = new ImageIcon((this.getClass().getResource(".//images//DisketePaint.gif")).getPath());
  private ImageIcon newIcon = new ImageIcon((this.getClass().getResource(".//images//NuevoBoceto.gif")).getPath());
  private JToggleButton btnFreeLine = new JToggleButton(penIcon);
  private JToggleButton btnRubber = new JToggleButton(rubberIcon);
  private JToggleButton btnText = new JToggleButton(textIcon);
  private JToggleButton btnOval = new JToggleButton(ovalIcon);
  private JToggleButton btnRectangle = new JToggleButton(rectangleIcon);
  private JToggleButton btnLine = new JToggleButton(lineIcon);

  private ButtonGroup group = new ButtonGroup();
  ToolButtonAdapter adapter = new ToolButtonAdapter(this);
  //Canvas_mouseAdapter canvasAdapter = new Canvas_mouseAdapter(this);
  //Canvas_mouseMotionAdapter canvasMotion = new Canvas_mouseMotionAdapter(this);
  Context context = new Context();

  public static final int LINE = 0;
  public static final int RECTANGLE = 1;
  public static final int OVAL = 2;
  public static final int FREELINE = 3;
  public static final int TEXT = 4;
  public static final int RUBBER = 5;

  int selectedTool=0;
  int width=300,height=200;
  JPanel pal;
  /*Point p1,p2,p3;
  Point minCoord, maxCoord;
  //GeneralPath freePath = new GeneralPath();
  LinkedList shapesList = new LinkedList();
  ListIterator itList;
  Shape shape = null;
  BufferedImage image=null;
  Context rubberContext = new Context();*/

  BorderLayout borderLayout1 = new BorderLayout();
  JPanel canvasPanel = new JPanel();
  Canvas canvas = new Canvas(context);
  JPanel toolPanel = new JPanel();
  JPanel drawingToolPanel = new JPanel();
  GridLayout gridLayout1 = new GridLayout();
  JPanel optionsPanel = new JPanel();
  JPanel palettePanel = new JPanel();
  JLabel lblOptions = new JLabel();
  JCheckBox cboxFill = new JCheckBox();
  JSlider sliderThick = new JSlider();
  JLabel lblThick = new JLabel();
  JSpinner spnHeight = new JSpinner(new SpinnerNumberModel(height, 10, 2048, 1));
  JSpinner spnWidth = new JSpinner(new SpinnerNumberModel(width, 10, 2048, 1));
  JPanel foreBackColorPanel = new JPanel();
  JPanel foregroundPanel = new JPanel();
  JPanel backgroundPanel = new JPanel();
  JPanel colorPalette = new JPanel();
  GridLayout gridLayout2 = new GridLayout();
  JLabel lblMore = new JLabel();
  JLabel lblWidth = new JLabel();
  JLabel lblHeight = new JLabel();
  JButton btnNewSketch = new JButton();
  JButton btnSaveSketch = new JButton();
  JPanel descriptorsPanel = new JPanel();
  JComboBox cboxColorDescriptors = new JComboBox();
  JPanel colorDescriptorPanel = new JPanel();
  GridLayout gridLayout3 = new GridLayout();
  JLabel lblColorDescriptor = new JLabel();
  JPanel textureDescriptorPanel = new JPanel();
  JLabel lblTextureDescriptor = new JLabel();
  JComboBox cmbTextureDescriptor = new JComboBox();
  JPanel shapeDescriptorPanel = new JPanel();
  JLabel lblShapeDescriptor = new JLabel();
  JComboBox cmbShapeDescriptor = new JComboBox();
  FlowLayout flowLayout1 = new FlowLayout();
  JPanel southPanel = new JPanel();

  public DefaultSketchQueryPanel() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    canvasPanel.setBackground(Color.gray);
    canvasPanel.setLayout(null);
    canvas.setBackground(Color.white);
    canvas.setBounds(new Rectangle(1, 0, 300, 200));
    toolPanel.setBorder(BorderFactory.createEtchedBorder());
    toolPanel.setPreferredSize(new Dimension(80, 10));
    toolPanel.setLayout(null);
    drawingToolPanel.setBounds(new Rectangle(6, 4, 69, 98));
    drawingToolPanel.setLayout(gridLayout1);
    gridLayout1.setColumns(2);
    gridLayout1.setRows(3);
    optionsPanel.setBorder(BorderFactory.createEtchedBorder());
    optionsPanel.setBounds(new Rectangle(3, 119, 74, 164));
    optionsPanel.setLayout(null);
    palettePanel.setBorder(BorderFactory.createEtchedBorder());
    palettePanel.setPreferredSize(new Dimension(10, 75));
    palettePanel.setLayout(null);
    btnFreeLine.setSelected(true);
    btnFreeLine.setToolTipText("Free line");
    btnFreeLine.setFocusPainted(false);
    btnFreeLine.addActionListener(adapter);
    lblOptions.setHorizontalAlignment(SwingConstants.CENTER);
    lblOptions.setText("Options");
    lblOptions.setBounds(new Rectangle(15, 105, 51, 14));
    cboxFill.setToolTipText("Fill the shapes");
    cboxFill.setHorizontalAlignment(SwingConstants.CENTER);
    cboxFill.setText("Fill");
    cboxFill.setBounds(new Rectangle(4, 10, 65, 23));
    cboxFill.addActionListener(new
                               DefaultSketchQueryPanel_cboxFill_actionAdapter(this));
    sliderThick.setToolTipText("Thick of the border");
    sliderThick.setBounds(new Rectangle(5, 56, 64, 24));
    sliderThick.addChangeListener(new
        DefaultSketchQueryPanel_sliderThick_changeAdapter(this));
    lblThick.setHorizontalAlignment(SwingConstants.CENTER);
    lblThick.setText("Thick");
    lblThick.setBounds(new Rectangle(20, 45, 34, 14));
    spnHeight.setBounds(new Rectangle(15, 102, 46, 20));
    spnHeight.setToolTipText("Sketch Height");
    spnHeight.addChangeListener(new
                                DefaultSketchQueryPanel_spnHeight_changeAdapter(this));
    spnWidth.setBounds(new Rectangle(15, 137, 46, 20));
    spnWidth.setToolTipText("Sketch Width");
    spnWidth.addChangeListener(new
                               DefaultSketchQueryPanel_spnWidth_changeAdapter(this));
    foreBackColorPanel.setBorder(null);
    foreBackColorPanel.setBounds(new Rectangle(8, 4, 61, 46));
    foreBackColorPanel.setLayout(null);
    foregroundPanel.setBackground(Color.black);
    foregroundPanel.setToolTipText("Foreground Color");
    foregroundPanel.setBounds(new Rectangle(5, 2, 34, 30));
    backgroundPanel.setBackground(Color.white);
    backgroundPanel.setToolTipText("Background Color");
    backgroundPanel.setBounds(new Rectangle(21, 9, 34, 32));
    colorPalette.setBorder(BorderFactory.createEtchedBorder());
    colorPalette.setBounds(new Rectangle(108, 5, 179, 43));
    colorPalette.setLayout(gridLayout2);
    gridLayout2.setColumns(8);
    gridLayout2.setHgap(1);
    gridLayout2.setRows(2);
    gridLayout2.setVgap(1);
    lblWidth.setHorizontalAlignment(SwingConstants.CENTER);
    lblWidth.setText("Width");
    lblWidth.setBounds(new Rectangle(19, 124, 37, 14));
    lblHeight.setHorizontalAlignment(SwingConstants.CENTER);
    lblHeight.setText("Height");
    lblHeight.setBounds(new Rectangle(19, 89, 37, 14));
    btnNewSketch.setBounds(new Rectangle(324, 12, 31, 28));
    btnNewSketch.setToolTipText("New sketch");
    btnNewSketch.setIcon(newIcon);
    btnNewSketch.addActionListener(adapter);
    btnSaveSketch.setBounds(new Rectangle(367, 12, 31, 28));
    btnSaveSketch.setToolTipText("Save the sketch as an image");
    btnSaveSketch.setIcon(saveIcon);
    btnSaveSketch.addActionListener(adapter);
    descriptorsPanel.setBorder(BorderFactory.createRaisedBevelBorder());
    descriptorsPanel.setPreferredSize(new Dimension(10, 35));
    descriptorsPanel.setLayout(flowLayout1);
    cboxColorDescriptors.setPreferredSize(new Dimension(80, 20));
    cboxColorDescriptors.setToolTipText("Texture Descriptor");
    colorDescriptorPanel.setBorder(null);
    colorDescriptorPanel.setPreferredSize(new Dimension(92, 40));
    colorDescriptorPanel.setLayout(gridLayout3);
    gridLayout3.setColumns(1);
    gridLayout3.setRows(2);
    lblColorDescriptor.setPreferredSize(new Dimension(77, 10));
    lblColorDescriptor.setHorizontalAlignment(SwingConstants.CENTER);
    lblColorDescriptor.setText("Color Descriptor");
    textureDescriptorPanel.setBorder(null);
    textureDescriptorPanel.setPreferredSize(new Dimension(92, 40));
    textureDescriptorPanel.setLayout(gridLayout3);
    lblTextureDescriptor.setPreferredSize(new Dimension(90, 10));
    lblTextureDescriptor.setHorizontalAlignment(SwingConstants.CENTER);
    lblTextureDescriptor.setText("Texture Descriptor");
    cmbTextureDescriptor.setPreferredSize(new Dimension(80, 20));
    cmbTextureDescriptor.setToolTipText("Texture Descriptor");
    shapeDescriptorPanel.setBorder(null);
    shapeDescriptorPanel.setPreferredSize(new Dimension(92, 40));
    shapeDescriptorPanel.setLayout(gridLayout3);
    lblShapeDescriptor.setPreferredSize(new Dimension(90, 10));
    lblShapeDescriptor.setHorizontalAlignment(SwingConstants.CENTER);
    lblShapeDescriptor.setText("Shape Descriptor");
    cmbShapeDescriptor.setPreferredSize(new Dimension(80, 20));
    cmbShapeDescriptor.setToolTipText("Texture Descriptor");
    flowLayout1.setHgap(10);
    flowLayout1.setVgap(0);
    southPanel.setPreferredSize(new Dimension(10, 110));
    southPanel.setLayout(gridLayout3);
    group.add(btnFreeLine);
    btnRubber.setToolTipText("Rubber");
    btnRubber.setFocusPainted(false);
    btnRubber.addActionListener(adapter);
    group.add(btnRubber);
    btnText.setEnabled(false);
    btnText.setToolTipText("Formated text");
    btnText.setFocusPainted(false);
    btnText.addActionListener(adapter);
    group.add(btnText);
    btnOval.setToolTipText("Circle");
    btnOval.setFocusPainted(false);
    btnOval.addActionListener(adapter);
    group.add(btnOval);
    btnRectangle.setToolTipText("Rectangle");
    btnRectangle.setFocusPainted(false);
    btnRectangle.addActionListener(adapter);
    group.add(btnRectangle);
    btnLine.setToolTipText("Straight line");
    btnLine.setFocusPainted(false);
    btnLine.addActionListener(adapter);
    group.add(btnLine);
    drawingToolPanel.add(btnFreeLine, null);
    drawingToolPanel.add(btnLine, null);
    drawingToolPanel.add(btnRectangle, null);
    drawingToolPanel.add(btnOval, null);
    drawingToolPanel.add(btnRubber, null);
    drawingToolPanel.add(btnText, null);
    toolPanel.add(lblOptions);
    sliderThick.setValue(2);
    sliderThick.setMaximum(50);
    sliderThick.setAutoscrolls(true);

    this.add(canvasPanel, java.awt.BorderLayout.CENTER);
    canvasPanel.add(canvas);
    this.add(toolPanel, java.awt.BorderLayout.WEST);
    toolPanel.add(optionsPanel);
    optionsPanel.add(cboxFill);
    optionsPanel.add(sliderThick);
    optionsPanel.add(spnHeight);
    optionsPanel.add(spnWidth);
    optionsPanel.add(lblHeight);
    optionsPanel.add(lblWidth);
    optionsPanel.add(lblThick);
    toolPanel.add(drawingToolPanel);
    foreBackColorPanel.add(foregroundPanel);
    foreBackColorPanel.add(backgroundPanel);
    this.add(southPanel, java.awt.BorderLayout.SOUTH);
    palettePanel.add(colorPalette);
    palettePanel.add(btnNewSketch);
    palettePanel.add(btnSaveSketch);
    palettePanel.add(foreBackColorPanel);
    shapeDescriptorPanel.add(lblShapeDescriptor);
    shapeDescriptorPanel.add(cmbShapeDescriptor);
    southPanel.add(palettePanel);
    southPanel.add(descriptorsPanel);
    textureDescriptorPanel.add(lblTextureDescriptor);
    textureDescriptorPanel.add(cmbTextureDescriptor);
    colorDescriptorPanel.add(lblColorDescriptor);
    colorDescriptorPanel.add(cboxColorDescriptors);
    descriptorsPanel.add(colorDescriptorPanel);
    descriptorsPanel.add(textureDescriptorPanel);
    descriptorsPanel.add(shapeDescriptorPanel);

    float grado, radio, radio_orig;
    int color;

    ColorPalette_mouseAdapter adapterColorPalette = new ColorPalette_mouseAdapter(this);

    radio = radio_orig = 1;
    for (grado = 0; grado < 360; grado += 40) {
      radio = radio_orig;
      while (radio > 0) {
        color = Color.HSBtoRGB(grado / 360, radio / radio_orig, 1);
        Color c;
        if(grado==80)
          c = Color.yellow;
        else
          c = new Color(color);
        pal = new JPanel();
        pal.setBackground(c);
        pal.setBorder(BorderFactory.createEtchedBorder());
        pal.addMouseListener(adapterColorPalette);
        colorPalette.add(pal, null);
        radio--;
      }
    }
    pal = new JPanel();
    pal.setBackground(Color.BLACK);
    pal.addMouseListener(adapterColorPalette);
    pal.setBorder(BorderFactory.createEtchedBorder());
    colorPalette.add(pal, null);
    pal = new JPanel();
    pal.setBackground(Color.DARK_GRAY);
    pal.addMouseListener(adapterColorPalette);
    pal.setBorder(BorderFactory.createEtchedBorder());
    colorPalette.add(pal, null);
    pal = new JPanel();
    pal.setBackground(Color.GRAY);
    pal.addMouseListener(adapterColorPalette);
    pal.setBorder(BorderFactory.createEtchedBorder());
    colorPalette.add(pal, null);
    pal = new JPanel();
    pal.setBackground(Color.LIGHT_GRAY);
    pal.addMouseListener(adapterColorPalette);
    pal.setBorder(BorderFactory.createEtchedBorder());
    colorPalette.add(pal, null);
    pal = new JPanel();
    pal.setBackground(new Color(230, 230, 230));
    pal.addMouseListener(adapterColorPalette);
    pal.setBorder(BorderFactory.createEtchedBorder());
    colorPalette.add(pal, null);
    pal = new JPanel();
    pal.setBackground(Color.WHITE);
    pal.addMouseListener(adapterColorPalette);
    pal.setBorder(BorderFactory.createEtchedBorder());
    colorPalette.add(pal, null);
    lblMore.setFont(new java.awt.Font("Dialog", 1, 13));
    lblMore.setForeground(Color.RED);
    lblMore.setHorizontalAlignment(SwingConstants.CENTER);
    lblMore.setText("+");
    pal = new JPanel();
    pal.setToolTipText("More colors");
    pal.setBorder(BorderFactory.createEtchedBorder());
    pal.setLayout(new BorderLayout());
    pal.addMouseListener(adapterColorPalette);
    pal.add(lblMore, BorderLayout.CENTER);
    colorPalette.add(pal,null);
  }

  public void toolButtonActionPerformed(ActionEvent e) {
    if((e.getSource()).getClass()==JToggleButton.class){
      JToggleButton b = (JToggleButton) e.getSource();
      Icon icon = b.getIcon();
      if (icon.equals(penIcon)){
        selectedTool = FREELINE;
        canvas.setSelectedTool(FREELINE);
      }
      if (icon.equals(lineIcon)){
        selectedTool = LINE;
        canvas.setSelectedTool(LINE);
      }
      if (icon.equals(rectangleIcon)){
        selectedTool = RECTANGLE;
        canvas.setSelectedTool(RECTANGLE);
      }
      if (icon.equals(ovalIcon)){
        selectedTool = OVAL;
        canvas.setSelectedTool(OVAL);
      }
      if (icon.equals(textIcon)){
        selectedTool = TEXT;
        canvas.setSelectedTool(TEXT);
      }
      if (icon.equals(rubberIcon)){
        selectedTool = RUBBER;
        canvas.setSelectedTool(RUBBER);
      }
    }
    else{
      JButton b = (JButton) e.getSource();
      Icon icon = b.getIcon();
      if (icon.equals(newIcon))
        newSketch();
      if (icon.equals(saveIcon))
        saveSketch();
    }
  }

  public void newSketch(){
    int returnval=JOptionPane.showConfirmDialog(this,"Do you really want to create a new sketch?", "New Sketch", JOptionPane.OK_CANCEL_OPTION);
    if(returnval == JOptionPane.OK_OPTION){
      canvas.removeAll();
      canvas.reset();
      canvas.repaint();
      canvasPanel.updateUI();
    }

  }

  public void saveSketch(){
    JFileChooser jfchooser = new JFileChooser();
    File file;
    String typeFile = "png";
    jfchooser.setCurrentDirectory(new File(".//"));

    jfchooser.setDialogTitle("Save image");
    jfchooser.setDialogType(JFileChooser.SAVE_DIALOG);

    int returnVal = jfchooser.showSaveDialog(this);

    if(returnVal == JFileChooser.APPROVE_OPTION){
      file = jfchooser.getSelectedFile();

      try {
        ImageIO.write(canvas.getImage(), typeFile, file);
      }
      catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
  }


  public Query createQuery() {
    return null;
  }

  public void cboxFill_actionPerformed(ActionEvent e) {
    if(cboxFill.isSelected())
      context.setFill(true);
    else
      context.setFill(false);
  }

  public void sliderThick_stateChanged(ChangeEvent e) {
    context.setStroke(new BasicStroke(sliderThick.getValue()));
  }

  public void spnHeight_stateChanged(ChangeEvent e) {
    height = ( (Integer) spnHeight.getValue()).intValue();
    canvas.setBounds(new Rectangle(1, 1, width, height));
    canvas.setSize(new Dimension(width, height));
    canvas.validate();
  }

  public void spnWidth_stateChanged(ChangeEvent e) {
    width = ( (Integer) spnWidth.getValue()).intValue();
    canvas.setBounds(new Rectangle(1, 1, width, height));
    canvas.setSize(new Dimension(width, height));
    canvas.validate();
  }

  void paletteColor_mouseClicked(MouseEvent e) {
    JPanel panel = (JPanel) e.getSource();
    Color col = panel.getBackground();
    int button = e.getButton();

    //if no more colors option
    if (panel.getComponentCount() == 0) {
      if (button == MouseEvent.BUTTON1) {
        foregroundPanel.setBackground(col);
        context.setForegroundColor(col);
        backgroundPanel.updateUI();
        foregroundPanel.updateUI();
      }
      else {
        if (button == MouseEvent.BUTTON3) {
          backgroundPanel.setBackground(col);
          context.setBackgroundColor(col);
          backgroundPanel.updateUI();
          foregroundPanel.updateUI();
        }
      }
    }
    else {
      JColorChooser moreColors = new JColorChooser();
      col = moreColors.showDialog(this, "Color selection", null);
      if (col != null) {
        if (button == MouseEvent.BUTTON1) {
          foregroundPanel.setBackground(col);
          context.setForegroundColor(col);
          backgroundPanel.updateUI();
          foregroundPanel.updateUI();
        }
        else {
          if (button == MouseEvent.BUTTON3) {
            backgroundPanel.setBackground(col);
            context.setBackgroundColor(col);
            backgroundPanel.updateUI();
            foregroundPanel.updateUI();
          }
        }
      }
    }
  }

  void paletteColor_mouseEntered(MouseEvent e) {
    JPanel panel = (JPanel)e.getSource();
    panel.setBorder(BorderFactory.createRaisedBevelBorder());
  }

  void paletteColor_mouseExited(MouseEvent e) {
    JPanel panel = (JPanel)e.getSource();
    panel.setBorder(BorderFactory.createEtchedBorder());
  }

}

class Canvas extends JPanel{
  private int selectedTool=DefaultSketchQueryPanel.FREELINE;
  private Context context;
  Canvas_mouseAdapter canvasAdapter = new Canvas_mouseAdapter(this);
  Canvas_mouseMotionAdapter canvasMotion = new Canvas_mouseMotionAdapter(this);
  Point p1,p2,p3;
  Point minCoord, maxCoord;
  //GeneralPath freePath = new GeneralPath();
  LinkedList shapesList = new LinkedList();
  ListIterator itList;
  Shape shape = null;
  BufferedImage image=null;
  Context rubberContext = new Context();

  Canvas(Context cont){
    this.addMouseListener(canvasAdapter);
    this.addMouseMotionListener(canvasMotion);
    rubberContext.setForegroundColor(this.getBackground());
    rubberContext.setFill(false);
    AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f);
    rubberContext.setComposite(ac);
    maxCoord = new Point(Integer.MIN_VALUE,Integer.MIN_VALUE);
    minCoord = new Point(Integer.MAX_VALUE,Integer.MAX_VALUE);
    context = cont;
  }

  public void reset(){
    shapesList.clear();
    image=null;
  }

  public void setSelectedTool(int tool){
    selectedTool = tool;
  }
  public void setContext(Context cont){
    context = cont;
  }

  public void paint( Graphics g )
  {
    super.paint(g);
    Graphics2D g2D = (Graphics2D) g;

    if(image!=null)
      g.drawImage(image,0,0,this);
    if(shape!=null){
      if(context.getAntialiasing())
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
      else
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

      g2D.setStroke(context.getStroke());
      if(selectedTool == DefaultSketchQueryPanel.RUBBER){ //herramienta borrar
        g2D.setPaint(this.getBackground());
        g2D.setComposite(rubberContext.getComposite());
      }
      else{
        g2D.setPaint(context.getForegroundColor());
        g2D.setComposite(context.getComposite());
      }
      g2D.draw(shape);
    }
  }

  private void createShape(){
    if(p1!=null && p2!=null){
      switch(selectedTool){
        case DefaultSketchQueryPanel.LINE:  //linea
          shape = new Line2D.Float(p1,p2);
          break;
        case DefaultSketchQueryPanel.RECTANGLE:  //rectangulo
          shape = new Rectangle2D.Float();
          ((RectangularShape)shape).setFrameFromDiagonal(p1,p2);
          break;
        case DefaultSketchQueryPanel.OVAL:  //ovalo
          shape = new Ellipse2D.Float();
          ((RectangularShape)shape).setFrameFromDiagonal(p1,p2);
          break;
        case DefaultSketchQueryPanel.FREELINE:  //Trazo libre
          ((GeneralPath)shape).lineTo(p2.x,p2.y);
          break;
        case DefaultSketchQueryPanel.TEXT: // texto
          break;
        case DefaultSketchQueryPanel.RUBBER:  //borrar
          ((GeneralPath)shape).lineTo(p2.x,p2.y);
          break;
      }
    }
  }

  private void saveShape(){
    switch(selectedTool){
      case DefaultSketchQueryPanel.LINE: //linea

      case DefaultSketchQueryPanel.RECTANGLE: //rectangulo

      case DefaultSketchQueryPanel.OVAL: //ovalo

      case DefaultSketchQueryPanel.FREELINE: //Trazo libre
        if(shape !=null){
          if(selectedTool==DefaultSketchQueryPanel.FREELINE){
            if (context.getFill())
              ( (GeneralPath) shape).lineTo(p1.x, p1.y);
          }
          shapesList.addLast(new MyShape(shape, context));
        }
        break;
      case DefaultSketchQueryPanel.TEXT: // texto
        break;
      case DefaultSketchQueryPanel.RUBBER: //borrar
        if(shape !=null){
          rubberContext.setForegroundColor(this.getBackground());
          rubberContext.setStroke(context.getStroke());
          rubberContext.setAntialiasing(context.getAntialiasing());
          shapesList.addLast(new MyShape(shape,rubberContext));
        }
        break;
    }
    shape = null;
  }

  private void createBuffer(){
    int y = this.getHeight();
    int x = this.getWidth();

    image = new BufferedImage(x,y,BufferedImage.TYPE_INT_RGB);
    Graphics2D g2Daux = image.createGraphics();
    g2Daux.setPaint(this.getBackground());

    g2Daux.fill(new Rectangle(0,0,x,y));

    //volcamos el contenido de la lista a la nueva imagen buffer
    if(!shapesList.isEmpty()){
      ((MyShape)shapesList.getFirst()).drawShape(g2Daux);
      itList = shapesList.listIterator(0);
      while(itList.hasNext())
        ((MyShape)itList.next()).drawShape(g2Daux);
    }
  }

  public BufferedImage getImage(){
    int y = this.getHeight();
    int x = this.getWidth();

    BufferedImage image = new BufferedImage(x,y,BufferedImage.TYPE_INT_RGB);
    Graphics2D g2Daux = image.createGraphics();
    g2Daux.setPaint(this.getBackground());

    g2Daux.fill(new Rectangle(0,0,x,y));

    //volcamos el contenido de la lista a la nueva imagen buffer
    if(!shapesList.isEmpty()){
      ((MyShape)shapesList.getFirst()).drawShape(g2Daux);
      itList = shapesList.listIterator(0);
      while(itList.hasNext())
        ((MyShape)itList.next()).drawShape(g2Daux);
    }
    return image;
  }

  void canvas_mousePressed(MouseEvent e) {
    p1=e.getPoint();
    checkCoordMaxMin(p1);
    //trazo libre o borrador
    if(selectedTool==DefaultSketchQueryPanel.FREELINE || selectedTool ==DefaultSketchQueryPanel.RUBBER){
      shape = new GeneralPath();
      ((GeneralPath)shape).moveTo(p1.x,p1.y);
    }
    image =null;  //se borra el buffer para crear uno nuevo
  }

  void canvas_mouseReleased(MouseEvent e) {
    checkCoordMaxMin(e.getPoint());
    if(shape!=null)
      saveShape();
    createBuffer();
    this.repaint();
  }

  void canvas_mouseDragged(MouseEvent e) {
    p2=e.getPoint();
    createShape();
    if(image == null)  //para crear el buffer una sola vez
      createBuffer();
    //if(miPaint.cboxTiempoReal.isSelected())
      //miPaint.busq.BusquedaTiempoReal(getImagen());
    this.repaint();
  }

  private void checkCoordMaxMin(Point p){
    if(p.x > maxCoord.x)
      maxCoord.x = p.x;
    if(p.y > maxCoord.y)
      maxCoord.y = p.y;

    if(p.x < minCoord.x)
      minCoord.x = p.x;
    if(p.y < minCoord.y)
      minCoord.y= p.y;
  }
}

class Canvas_mouseAdapter extends java.awt.event.MouseAdapter {
  Canvas adaptee;

  Canvas_mouseAdapter(Canvas adaptee) {
    this.adaptee = adaptee;
  }

  public void mousePressed(MouseEvent e) {
    adaptee.canvas_mousePressed(e);
  }

  public void mouseReleased(MouseEvent e) {
    adaptee.canvas_mouseReleased(e);
  }
}

class Canvas_mouseMotionAdapter extends java.awt.event.MouseMotionAdapter {
  Canvas adaptee;

  Canvas_mouseMotionAdapter(Canvas adaptee) {
    this.adaptee = adaptee;
  }

  public void mouseDragged(MouseEvent e) {
    adaptee.canvas_mouseDragged(e);
  }
}

class ColorPalette_mouseAdapter extends java.awt.event.MouseAdapter {
  DefaultSketchQueryPanel adaptee;

  ColorPalette_mouseAdapter(DefaultSketchQueryPanel adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.paletteColor_mouseClicked(e);
  }
  public void mouseEntered(MouseEvent e) {
    adaptee.paletteColor_mouseEntered(e);
  }
  public void mouseExited(MouseEvent e) {
    adaptee.paletteColor_mouseExited(e);
  }
}

class ToolButtonAdapter implements java.awt.event.ActionListener{
  DefaultSketchQueryPanel adaptee;

  ToolButtonAdapter(DefaultSketchQueryPanel adaptee){
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e){
    adaptee.toolButtonActionPerformed(e);
  }
}

/**
 * <p>Clase que almacena los objetos Shape o Formas con su determinado Contexto. Esta clase pinta
* tambien los objetos, por lo que seria necesario pasar el Graphics del objeto lienzo donde se pintara.
*  Ver {@link mpeg7.MiPanelDeDibujo Clase PanelDeDibujo o Lienzo}</p>
* <p>Proyecto 2004</p>
 * @author Jose Manuel Soto Hidalgo
 * @version 1.0
 */

class MyShape implements java.io.Serializable{
  private Shape shape;
  private Context context;

  /**
   * Constructor por defecto que crea e inicializa un objeto shape con su Shape y su Contexto
   * @param fig Forma u objeto <code>Shape</code>
   * @param context Contexto de la forma. Ver {@link mpeg7.Contexto Clase Contexto}
   */
  public MyShape(Shape fig, Context cont) {
    shape = fig;
    context = new Context(cont);
  }

  /** Metodo que pinta la figura actual con su contexto correspondiente en el lienzo de Dibujo. Para ello
   * hay que pasarle como par�metro el Graphics2D del contexto.
   *  @param g Graphics2D del lienzo o panel de Dibujo
   */
  public void drawShape(Graphics2D g) {
    putBackContext(g);
    if (context.getFill()) {
      g.setPaint(context.getBackgroundColor());
      g.fill(shape);
      g.setPaint(context.getForegroundColor());
    }
    g.draw(shape);
  }

  private void putBackContext(Graphics2D g) {
    g.setPaint(context.getForegroundColor());
    g.setStroke(context.getStroke());
    g.setComposite(context.getComposite());
    if (context.getAntialiasing())
      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                         RenderingHints.VALUE_ANTIALIAS_ON);
    else
      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                         RenderingHints.VALUE_ANTIALIAS_OFF);
  }

  /** M�todo que obtiene la Forma o Shape del objeto Figura
   *  @return Objeto <code>Shape</code> con la forma del objeto Figura
   */
  public Shape getShape() {
    return shape;
  }

  /** M�todo que obtiene el contexto actual del objeto Figura
   *  @return Instancia de la clase <code>Contexto</code> con el contexto del objeto Figura
   */
  public Context getContext() {
    return context;
  }
}


/**
 * <p>Clase para crear un contexto para dibujar figuras</p>
* <p>Tiene como atributos: </p>
*     <p>El color de relleno o de fondo de la figura</p>
*     <p>El color frontal o vigente de la figura</p>
*     <p>El grosor de la btnLine de la figura</p>
*     <p>Un booleno indicando si la figura tiene o no relleno</p>
*     <p>Un booleno indicando si se permite la t�cnia de antialiasing</p>
*     <p>Un objeto Composite para realizar la transparencia de los borrados</p>
* <p>Proyecto 2004</p>
 * @author Jose Manuel Soto Hidalgo
 * @version 1.0
 */
class Context implements java.io.Serializable{
  private Color backgroundColor;
  private Color foregroundColor;
  private Stroke stroke;
  private boolean fill;
  private boolean antialiasing;
  private Composite composite;

  /**
   * Constructor por defecto que crea e inicializa el contexto a los valores por defecto
   * <p>Color de fill o fondo: Blanco
   * <p>Color frontal o vigente: Negro
   * <p>Grosor de la btnLine: 2 ptos
   * <p>fill: False
   * <p>Antialiasing: False
   * <p>composite: 1 -> Opaco
   */
  public Context() {
    backgroundColor = Color.white;
    foregroundColor = Color.black;
    stroke = new BasicStroke(2.0f);
    fill = false;
    antialiasing = false;
    AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f);
    composite = ac;
  }

  /**
   * Constructor por copia que crea e inicializa el contexto a los valores del contexto que se pasa por par�metro
   * @param c Contexto copia
   */
  public Context(Context c) {
    backgroundColor = c.getBackgroundColor();
    foregroundColor = c.getForegroundColor();
    stroke = c.getStroke();
    fill = c.getFill();
    antialiasing = c.getAntialiasing();
    composite = c.getComposite();
  }

  /** M�todo que pone el Color frontal o Vigente al contexto
   *  @param cv Color frontal o vigente del contexto
   */
  public void setForegroundColor(Color cv){
    foregroundColor = cv;
  }

  /** M�todo que obtiene el Color frontal o Vigente del contexto
   *  @return Color frontal o vigente del contexto
   */
  public Color getForegroundColor(){
    return foregroundColor;
  }


  /** M�todo que pone el Color de fondo o fill al contexto
   *  @param cr Color de fondo o fill del contexto
   */
  public void setBackgroundColor(Color cr){
    backgroundColor = cr;
  }

  /** M�todo que obtiene el Color de fondo o fill del contexto
   *  @return Color de fondo o fill del contexto
   */
  public Color getBackgroundColor(){
    return backgroundColor;
  }

  /** M�todo que pone el Grosor de la l�nea de las figuras al contexto
   *  @param st Grosor del contexto
   */
  public void setStroke(Stroke st){
    stroke = st;
  }

  /** M�todo que obtiene el Grosor de la l�nea de las figuras del contexto
   *  @return Grosor del contexto
   */
  public Stroke getStroke(){
    return stroke;
  }

  /** M�todo que pone si las figuras del contexto tendr�n fill. En caso de verdadero �stas tendr�n el Color de Fondo o fill del contexto
   *  @param r Verdadero si fill, Falso en caso contrario
   */
  public void setFill(boolean r){
    fill = r;
  }

  /** M�todo que obtiene si las figuras del contexto tendr�n fill. En caso de verdadero �stas tienen el Color de Fondo o fill del contexto
   *  @return Verdadero si fill, Falso en caso contrario
   */
  public boolean getFill(){
    return fill;
  }

  /** M�todo pone si el contexto tendr� antialiasing (tecnica eficiente para borrar y suavizar btnLines).
   *  @param a Verdadero si antialising, Falso en caso contrario
   */
  public void setAntialiasing(boolean a){
    antialiasing = a;
  }

  /** M�todo que obtiene si el contexto tendr� antialiasing (tecnica eficiente para borrar y suavizar btnLines).
   *  @return Verdadero si antialising, Falso en caso contrario
   */
  public boolean getAntialiasing(){
    return antialiasing;
  }

  /** M�todo que pone la transparencia al contexto
   *  @param c Objeto <code>Composite</code> para la transparencia
   */
  public void setComposite(Composite c){
    composite = c;
  }

  /** M�todo que obtiene la transparencia del contexto
   *  @return Objeto <code>Composite</code> con la transparencia
   */
  public Composite getComposite(){
    return composite;
  }
}

class DefaultSketchQueryPanel_spnWidth_changeAdapter
    implements ChangeListener {
  private DefaultSketchQueryPanel adaptee;
  DefaultSketchQueryPanel_spnWidth_changeAdapter(DefaultSketchQueryPanel
                                                 adaptee) {
    this.adaptee = adaptee;
  }

  public void stateChanged(ChangeEvent e) {
    adaptee.spnWidth_stateChanged(e);
  }
}

class DefaultSketchQueryPanel_spnHeight_changeAdapter
    implements ChangeListener {
  private DefaultSketchQueryPanel adaptee;
  DefaultSketchQueryPanel_spnHeight_changeAdapter(DefaultSketchQueryPanel
                                                  adaptee) {
    this.adaptee = adaptee;
  }

  public void stateChanged(ChangeEvent e) {
    adaptee.spnHeight_stateChanged(e);
  }
}

class DefaultSketchQueryPanel_sliderThick_changeAdapter
    implements ChangeListener {
  private DefaultSketchQueryPanel adaptee;
  DefaultSketchQueryPanel_sliderThick_changeAdapter(DefaultSketchQueryPanel
      adaptee) {
    this.adaptee = adaptee;
  }

  public void stateChanged(ChangeEvent e) {
    adaptee.sliderThick_stateChanged(e);
  }
}

class DefaultSketchQueryPanel_cboxFill_actionAdapter
    implements ActionListener {
  private DefaultSketchQueryPanel adaptee;
  DefaultSketchQueryPanel_cboxFill_actionAdapter(DefaultSketchQueryPanel
                                                 adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cboxFill_actionPerformed(e);
  }
}
