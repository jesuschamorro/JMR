package jmr.initial.descriptor;

import jmr.descriptor.mpeg7.MPEG7ColorStructure;
import jmr.initial.descriptor.mpeg7.MPEG7ScalableColor;
import jmr.initial.descriptor.mpeg7.MPEG7HomogeneousTexture;
import jmr.initial.descriptor.mpeg7.MPEG7EdgeHistogram;
import java.awt.Color;
import jmr.initial.media.Media;
import jmr.result.JMRResult;
import jmr.initial.media.JMRExtendedBufferedImage;

/**
 * <p>Title: JMR Project</p>
 * <p>Description: Java Multimedia Retrieval API</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: University of Granada</p>
 * @author Jesus Chamorro Martinez
 * @version 1.0
 */

public abstract class MediaDescriptor {

  /** Represents a custom descriptor type */
  public static final int TYPE_CUSTOM_DESCRIPTOR = 0;

  /** Represents a color data descriptor */
  public static final int TYPE_COLOR_DATA_DESCRIPTOR = 1;

  /** Represents for MPEG7 Homegeneous Texture Descriptors */
  public static final int TYPE_MPEG7_HTD_DESCRIPTOR = 2;

  /** Represents for MPEG7 Edge Histograms Descriptors */
  public static final int TYPE_MPEG7_EHD_DESCRIPTOR = 3;

  /** Represents for MPEG7 Scalable color descriptor */
  public static final int TYPE_MPEG7_SCD_DESCRIPTOR = 4;

  /** Represents for MPEG7 Color Structure Descriptor */
  public static final int TYPE_MPEG7_CSD_DESCRIPTOR = 5;

  /** Represents for MPEG7 Dominant Color Descriptor */
  public static final int TYPE_MPEG7_DCD_DESCRIPTOR = 6;

  /** Represents for MPEG7 Color Layout Descriptors */
  public static final int TYPE_MPEG7_CL_DESCRIPTOR = 7;

  /** Represents the type of the media descriptor */
  protected int type;


  /** Contruct a <code>MediaDescriptor</code> object
   * @param type Media descriptor type
   */
  protected MediaDescriptor(int type){
    this.type=type;
  }

  /** Returns the media descriptor type
   * @return A <code>JMRResult</code> object with the result
   */
  public int getType(){
    return(type);
  }

  /** 
   * Compares this <code>MediaDescriptor</code> obtect with
   * the <code>MediaDescriptor</code> given by parameter
   * @param mediaDescriptor <code>MediaDescriptor</code> object to be compared
   * @return A <code>JMRResult</code> object with the result
   */
  public abstract JMRResult compare(MediaDescriptor mediaDescriptor);

  /** Computes the media descriptor for the media given by parameter
   * @param media The media from which the descriptor is calculated
   */
  public abstract void calculate(Media media);

  /** Create a <code>MediaDescriptor</code> object of the type indicated by the parameter
    * using the default constructor (i.e. the constructor without parameters).
    * @param type The type of descriptor to be calculated
    * @return A media descriptor (or null if there is not default constructor
    *                             or the media type does not exit)
    */
  static public MediaDescriptor getInstance(int type) {
      return getInstance(type,(Object[])null);
  }

  /** Create a <code>MediaDescriptor</code> object for the media given by parameter
   * using the default constructor (i.e. the constructor without parameters).
   * @param type The type of descriptor to be calculated
   * @param media The media from which the descriptor is calculated
   * @return A media descriptor (or null if there is not default constructor
   *                             or the media type does not exit)
   */
  static public MediaDescriptor getInstance(int type, Media media) {
    return getInstance(type,media,null);
  }

  /** Create a <code>MediaDescriptor</code> object for the media given by parameter
   * @param type The type of descriptor to be calculated
   * @param param An array of parameters for constructing the descriptor. If it is null, the default constructor is used
   * @param media The media from which the descriptor is calculated
   * @return A media descriptor (or null if there is not constructor for the specified parameters
   *                             or the media type does not exit)
   */
  static public MediaDescriptor getInstance(int type, Media media, Object[] param) {
    MediaDescriptor md = getInstance(type,param);
    if(md!=null) {
      md.calculate(media);
      return(md);
    }
    return (null);
  }

  /** Create a <code>MediaDescriptor</code> object of the type indicated by the parameter.
   * @param type The type of descriptor to be calculated
   * @param param An array of parameters for constructing the descriptor. If it is null, the default constructor is used
   * @return A media descriptor (or null if there is not constructor for the specified parameters
   *                             or the media type does not exit)
   */
  static public MediaDescriptor getInstance(int type, Object[] param) {
    switch (type) {
      case TYPE_COLOR_DATA_DESCRIPTOR:
        return getColoDataInstance(param);
      case TYPE_MPEG7_SCD_DESCRIPTOR:
        return getMPEG7ScalableColorInstance(param);
      case TYPE_MPEG7_HTD_DESCRIPTOR:
        return getMPEG7HomogeneousTextureInstance(param);
      case TYPE_MPEG7_EHD_DESCRIPTOR:
        return getMPEG7EdgeHistogramInstance(param);
      case TYPE_MPEG7_CSD_DESCRIPTOR:

      default:
        return (null);
    }
  }

  /** Create a color data descriptor.
    * @param param An array of parameters for constructing the descriptor
    * @return A color data descriptor
    */
  static public ColorData getColoDataInstance(Object[] param) {
    if (param == null || param.length == 0) return(null);
    if (param[0] instanceof Color) return (new ColorData( (Color) param[0]));
    return (null);
  }

  /** Create a MPEG7 scalable color descriptor.
    * @param param An array of parameters for constructing the descriptor
    * @return A MPEG7 scalable color descriptor
    */
  static public MPEG7ScalableColor getMPEG7ScalableColorInstance(Object[] param) {
    if (param == null || param.length == 0) return(new MPEG7ScalableColor());
    if (param.length == 1 && param[0] instanceof JMRExtendedBufferedImage)
      return new MPEG7ScalableColor( (JMRExtendedBufferedImage)param[0] );
    if (param.length == 2 && param[0] instanceof Integer && param[0] instanceof Integer)
      return new MPEG7ScalableColor( ((Integer)param[0]).intValue() , ((Integer)param[1]).intValue() );
    if (param.length == 3 && param[0] instanceof JMRExtendedBufferedImage && param[1] instanceof Integer && param[2] instanceof Integer)
      return new MPEG7ScalableColor( (JMRExtendedBufferedImage)param[0], ((Integer)param[1]).intValue() , ((Integer)param[2]).intValue() );
    return(null);
  }

  /** Create a MPEG7 homogeneous texture descriptor.
    * @param param An array of parameters for constructing the descriptor
    * @return A MPEG7 homogeneous texture descriptor
    */
  static public MPEG7HomogeneousTexture getMPEG7HomogeneousTextureInstance(Object[] param) {
    if (param == null || param.length == 0) return(new MPEG7HomogeneousTexture());
    if (param.length == 1 && param[0] instanceof JMRExtendedBufferedImage)
      return new MPEG7HomogeneousTexture( (JMRExtendedBufferedImage)param[0] );
    if (param.length == 3 && param[0] instanceof Integer && param[1] instanceof Integer && param[2] instanceof Boolean)
      return new MPEG7HomogeneousTexture( ((Integer)param[0]).intValue() , ((Integer)param[1]).intValue() , ((Boolean)param[2]).booleanValue() );
    if (param.length == 4 && param[0] instanceof JMRExtendedBufferedImage && param[1] instanceof Integer && param[2] instanceof Integer && param[3] instanceof Boolean)
      return new MPEG7HomogeneousTexture( (JMRExtendedBufferedImage)param[0] , ((Integer)param[1]).intValue() , ((Integer)param[2]).intValue() , ((Boolean)param[3]).booleanValue() );
    return(null);
  }

  /** Create a MPEG7 edge histogram descriptor.
    * @param param An array of parameters for constructing the descriptor
    * @return A MPEG7 edge histogram descriptor
    */
  static public MPEG7EdgeHistogram getMPEG7EdgeHistogramInstance(Object[] param) {
    if (param == null || param.length == 0) return(new MPEG7EdgeHistogram());
    if (param.length == 1 && param[0] instanceof JMRExtendedBufferedImage)
      return new MPEG7EdgeHistogram( (JMRExtendedBufferedImage)param[0] );
    if (param.length == 2 && param[0] instanceof Integer && param[1] instanceof Integer)
      return new MPEG7EdgeHistogram( ((Integer)param[0]).intValue() , ((Integer)param[1]).intValue() );
    if (param.length == 3 && param[0] instanceof JMRExtendedBufferedImage && param[1] instanceof Integer && param[2] instanceof Integer)
      return new MPEG7EdgeHistogram( (JMRExtendedBufferedImage)param[0] , ((Integer)param[1]).intValue() , ((Integer)param[2]).intValue() );
    return(null);
  }

  /** Create a MPEG7 edge histogram descriptor.
    * @param param An array of parameters for constructing the descriptor
    * @return A MPEG7 edge histogram descriptor
    */
  static public MPEG7ColorStructure getMPEG7ColorStructureInstance(Object[] param) {
    if (param == null || param.length == 0) return(new MPEG7ColorStructure());
    if (param.length == 1 && param[0] instanceof JMRExtendedBufferedImage)
      return new MPEG7ColorStructure( (JMRExtendedBufferedImage)param[0] );
    if (param.length == 1 && param[0] instanceof Integer)
      return new MPEG7ColorStructure( ((Integer)param[0]).intValue() );
    if (param.length == 2 && param[0] instanceof JMRExtendedBufferedImage && param[1] instanceof Integer)
      return new MPEG7ColorStructure( (JMRExtendedBufferedImage)param[0] , ((Integer)param[1]).intValue() );
    return(null);
  }

}
