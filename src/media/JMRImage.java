package jmr.media;

import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

/**
 * <p>Title: JMR Project</p>
 * <p>Description: Java Multimedia Retrieval API</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: University of Granada</p>
 * @author Jesus Chamorro Martinez
 * @version 1.0
 */

public interface JMRImage extends Media {

  /**
  * Represents a custom image type
  */
  public static final int TYPE_CUSTOM = 0;

  /**
  * Represents an image based on the BufferedImage class
  */
  public static final int TYPE_BUFFERED_IMAGE = 1;

  /**
  * Represents an image based on the JAIImage class
  */
  public static final int TYPE_JAI_IMAGE = 2;


  /** Return the height of the image
   * @return The height of the image
   */
  public int getHeight();

  /** Return the width of the image
   * @return The width of the image
   */
  public int getWidth();

  /** Return the image type
   * @see #TYPE_UNDEFINED
   * @see #TYPE_BUFFERED_IMAGE
   * @see #TYPE_JAI_IMAGE
   * @return The image type
   */
  public int getImageType();

}

