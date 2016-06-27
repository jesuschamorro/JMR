package jmr.descriptor;

import jmr.media.Media;
import jmr.result.JMRResult;
import jmr.result.FloatResult;
import java.awt.Color;
import java.awt.color.ColorSpace;

/**
 * <p>Title: JMR Project</p>
 * <p>Description: Java Multimedia Retrieval API</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: University of Granada</p>
 * @author Jesus Chamorro Martinez
 * @version 1.0
 */

public class ColorData extends ColorDescriptor {

  /** The color stores by this ColorData */
  protected Color colorData;

  /**
     * Constructs a <code>ColorData</code> from a color.
     * @param color  The data color
     */
  public ColorData(Color color) {
    super(MediaDescriptor.TYPE_COLOR_DATA_DESCRIPTOR,color.getColorSpace().getType());
    colorData = color;
  }

  /** Return the color associated to this ColorData object
   * @return The color data
   */
  public Color getColor() {
    return (colorData);
  }

  /** Set the color associated to this ColorData object
   * @param Color to be set
   */
  public void setColor(Color color) {
    colorData = color;
  }


  /** Calculates the Euclidean distance between colors
   * @param c1 Fisrt color
   * @param c2 Second color
   * @return A float value corresponding to the euclidean distance between colors
   */
  private float distance(Color c1, Color c2) {
    double dist = 0.0, dc;

    float c1Components[] = c1.getColorComponents(null);
    float c2Components[] = c2.getColorComponents(null);
    for (int i = 0; i < c1Components.length; i++) {
      dc = c1Components[i] - c2Components[i];
      dist += (dc * dc);
    }
    dist = Math.sqrt(dist);
    return ( (float) dist);
  }

  /** Compares this ColorData obtect with the ColorData given by parameter
   * @param color ColoData object to be compared
   * @return A float result corresponding to the distance between colors
   */
  public FloatResult compare(ColorData color) {
    float distance = distance(color.colorData, this.colorData);
    return ( new FloatResult(distance) );
  }

  /** Compare this ColorData obtect with the ColorData given by parameter
   * <p> This method is valid only for Colodata media descriptors
   * @param mediaDescriptor MediaDescriptor object to be compared
   * @see #compare(ColorData color)
   * @return The difference between descriptors
   */
  public JMRResult compare(MediaDescriptor mediaDescriptor) {
    // Only ColorData objects can be compared
    if (! (mediaDescriptor instanceof ColorData)) {
      return (null);
    }
    return ( compare((ColorData) mediaDescriptor) );
  }

  /** Create a new ColorData object from a given media
   * @param media MediaDescriptor object from which the ColoData is calculated
   * @see #compare(ColorData color)
   * @return A ColorData descriptor
   */
  public void calculate(Media media) {
    //TODO
  }


  public boolean equals(Object obj){
      return( colorData.equals(((ColorData)obj).colorData) );
  }

  public int hashCode(){
      return colorData.hashCode();
  }


}
