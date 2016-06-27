package jmr.descriptor.mpeg7;

import jmr.media.Media;
import jmr.result.JMRResult;
import jmr.result.FloatResult;
import jmr.descriptor.MediaDescriptor;
import jmr.media.JMRBufferedImage;
import java.awt.Color;
import jmr.descriptor.ColorData;
import java.util.Vector;

/**
 * <p>Title: JMR Project</p>
 * <p>Description: Java Multimedia Retrieval API</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: University of Granada</p>
 * @author Jesus Chamorro Martinez
 * @version 1.0
 */

public class MPEG7DominantColors
    extends MPEG7ColorDescriptor {

  /** A vector of <code>MPEG7SingleDominatColor</code> */
  private Vector dominantColor;

  /** Represets a distance calculated as.... */
  public static final int DISTANCE_ALL_TO_ALL = 1;

  /** Represets a distance calculated as.... */
  public static int DEFAULT_DISTANCE = DISTANCE_ALL_TO_ALL;

  /**
   * Constructor
   * @param cs Color space
   */
  public MPEG7DominantColors(int cs) {
    super(MediaDescriptor.TYPE_MPEG7_DCD_DESCRIPTOR, cs);
  }

  /** Computes the <code>MPEG7DominantColors</code> descriptor for
   * the media given by parameter
   * @param media The media from which the descriptor is calculated
   */
  public void calculate(Media media) {
    // The MPEG7DominantColors only can be calculated from JMRExtendedBufferedImage
    if (media instanceof JMRBufferedImage) {
      calculate( (JMRBufferedImage) media);
    }
  }

  /** Computes the structured histogram descriptor for
   * the image given by parameter
   * @param im The image from which the descriptor is calculated
   */
  public void calculate(JMRBufferedImage im) {
    // TODO
  }

  /** Compare this <code>MPEG7DominantColors</code> obtect with the
   * <code>MPEG7DominantColors</code> given by parameter
   * <p> This method is valid only for <code>MPEG7DominantColors</code>
   *  media descriptors
   * @param mediaDescriptor MediaDescriptor object to be compared
   * @see #compare(MPEG7DominantColors desc)
   * @return The difference between descriptors
   */
  public JMRResult compare(MediaDescriptor mediaDescriptor) {
    // Only MPEG7DominantColors objects can be compared
    if (! (mediaDescriptor instanceof MPEG7DominantColors)) {
      return (null);
    }
    return (compare( (MPEG7DominantColors) mediaDescriptor));
  }

  /** Compare this <code>MPEG7DominantColors</code> obtect with the
   * <code>MPEG7DominantColors</code> given by parameter using the
   * default distance (#DEFAULT_DISTANCE)
   * @param desc <code>MPEG7DominantColors</code> object to be compared
   * @return The difference between descriptors as float result
   */
  public FloatResult compare(MPEG7DominantColors desc) {
    compare(desc, DEFAULT_DISTANCE);
    return (null);
  }

  /** Compare this <code>MPEG7DominantColors</code> obtect with the
   * <code>MPEG7DominantColors</code> given by parameter using the distance
   * indicated by parameter
   * @param desc <code>MPEG7DominantColors</code> object to be compared
   * @param typeDistance Distance to be applied
   * @return The difference between descriptors as float result
   */
  public FloatResult compare(MPEG7DominantColors desc, int typeDistance) {
    switch (type) {
      case DISTANCE_ALL_TO_ALL:
        return compareAllToAll(desc);
      default:
        return null;
    }
  }

  /** Compare this <code>MPEG7DominantColors</code> obtect with the
   * <code>MPEG7DominantColors</code> given by parameter. To calculate
   * the distance....
   * @param desc <code>MPEG7DominantColors</code> object to be compared
   * @return The difference between descriptors as float result
   */
  public FloatResult compareAllToAll(MPEG7DominantColors desc) {
    //TODO
    return (null);
  }

  /** Inserts the specified <code>MPEG7SingleDominatColor</code>
   * at the specified position in the dominant color vector.
   * Shifts the element currently at that position (if any) and
   * any subsequent elements to the right (adds one to their indices).
   * @param index Index at which the specified element is to be inserted
   * @param dColor The dominant color to be inserted
   */
  public void addDominantColor(int index, MPEG7SingleDominatColor dColor) {
    dominantColor.add(index, dColor);
  }

  /** Appends the specified <code>MPEG7SingleDominatColor</code>
   * to the end of the dominant color vector
   * @param dColor The dominant color to be inserted
   */
  public boolean addDominantColor(MPEG7SingleDominatColor dColor) {
    return dominantColor.add(dColor);
  }

  /** Returns the element at the specified position in the dominant color vector
   * @param index Index of element to be returned
   */
  public MPEG7SingleDominatColor getDominantColor(int index) {
    return (MPEG7SingleDominatColor) (dominantColor.get(index));
  }

  /**
   * Nested class representing a single dominant color
   */
  class MPEG7SingleDominatColor
      extends ColorData {

    /** The variance of the dominant color */
    float variance;

    /** The percent of the dominant color */
    float percent;

    /** The spatial coherence of the dominant color */
    float spatialCoherence;

    /** Represents a non calculated variance data */
    public static final float NO_VARIANCE = 0.0f;

    /** Represents a non calculated variance data */
    public static final float NO_PERCENT = 0.0f;

    /** Represents a non calculated variance data */
    public static final float NO_SPATIAL_COHERENCE = 0.0f;

    /**
     * Constructs a <code>MPEG7SingleDominatColor</code> from a color
     * @param color  The data color
     */
    public MPEG7SingleDominatColor(Color color) {
      this(color, NO_VARIANCE, NO_PERCENT, NO_SPATIAL_COHERENCE);
    }

    /**
     * Constructs a <code>MPEG7SingleDominatColor</code> from a color
     * with the specified properties
     * @param color  The data color
     * @param variance  The variance of the color
     * @param percent  The percent of the color
     * @param spatialCoherence  The spatial coherence of the color
     */
    public MPEG7SingleDominatColor(Color color, float variance, float percent,
                                   float spatialCoherence) {
      super(color);
      this.variance = variance;
      this.percent = percent;
      this.spatialCoherence = spatialCoherence;
    }
  } // End of inner class

}
