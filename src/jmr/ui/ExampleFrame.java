package jmr.ui;

import java.awt.*;
import java.util.Vector;
import javax.swing.*;


/**
 * <p>Title: VisualQueryPanel</p>
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
public class ExampleFrame extends JFrame {
  JPanel contentPane;
  BorderLayout borderLayout1 = new BorderLayout();
  JTabbedPane jTabbedPane1 = new JTabbedPane();
  JPanel panelColor = new DefaultColorQueryPanel();
  JPanel panelTextura = new DefaultTextureQueryPanel();
  JPanel panelImagen = new DefaultImageQueryPanel();
  JPanel panelBoceto = new DefaultSketchQueryPanel();
  JButton jButton1 = new JButton();

  public ExampleFrame() {
    try {
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      jbInit();
    }
    catch (Exception exception) {
      exception.printStackTrace();
    }



  }

  /**
   * Component initialization.
   *
   * @throws java.lang.Exception
   */
  private void jbInit() throws Exception {
    contentPane = (JPanel) getContentPane();
    contentPane.setLayout(borderLayout1);
    setSize(new Dimension(500, 422));
    setTitle("Example");
    jButton1.setText("");
    this.getContentPane().add(jTabbedPane1, BorderLayout.CENTER);
    jTabbedPane1.add(panelColor, "Colores");
    jTabbedPane1.add(panelTextura, "Textura");
    jTabbedPane1.add(panelImagen, "Imagen");
    jTabbedPane1.add(panelBoceto, "Boceto");
    this.getContentPane().add(jButton1, BorderLayout.NORTH);
  }
}
