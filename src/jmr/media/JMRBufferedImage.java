package jmr.media;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.util.Hashtable;

/**
 * <p>Title: JMR Project</p>
 * <p>Description: Java Multimedia Retrieval API</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: University of Granada</p>
 * @author Jesus Chamorro Martinez
 * @version 1.0
 */

public class JMRBufferedImage extends BufferedImage implements JMRImage {

  /**
   * Constructs a <code>JMRBufferedImage</code> of one of the predefined
   * image types by means of the corresponding <code>BufferedImage</code> constructor.
   * The <code>ColorSpace</code> for the image is the
   * default sRGB space.
   * @param width     width of the created image
   * @param height    height of the created image
   * @param imageType type of the created image
   * @see ColorSpace
   * @see #TYPE_INT_RGB
   * @see #TYPE_INT_ARGB
   * @see #TYPE_INT_ARGB_PRE
   * @see #TYPE_INT_BGR
   * @see #TYPE_3BYTE_BGR
   * @see #TYPE_4BYTE_ABGR
   * @see #TYPE_4BYTE_ABGR_PRE
   * @see #TYPE_BYTE_GRAY
   * @see #TYPE_USHORT_GRAY
   * @see #TYPE_BYTE_BINARY
   * @see #TYPE_BYTE_INDEXED
   * @see #TYPE_USHORT_565_RGB
   * @see #TYPE_USHORT_555_RGB
   */
  public JMRBufferedImage(int width, int height, int imageType) {
    super(width, height, imageType);
  }

  /**
   *
   * Constructs a <code>JMRBufferedImage</code> of one of the predefined
   * image types TYPE_BYTE_BINARY or TYPE_BYTE_INDEXED by means of the
   * corresponding <code>BufferedImage</code> constructor.
   *
   * <p> If the image type is TYPE_BYTE_BINARY, the number of
   * entries in the color model is used to determine whether the
   * image should have 1, 2, or 4 bits per pixel.  If the color model
   * has 1 or 2 entries, the image will have 1 bit per pixel.  If it
   * has 3 or 4 entries, the image with have 2 bits per pixel.  If
   * it has between 5 and 16 entries, the image will have 4 bits per
   * pixel.  Otherwise, an IllegalArgumentException will be thrown.
   *
   * @param width     width of the created image
   * @param height    height of the created image
   * @param imageType type of the created image
   * @param cm        <code>IndexColorModel</code> of the created image
   * @throws IllegalArgumentException   if the imageType is not
   * TYPE_BYTE_BINARY or TYPE_BYTE_INDEXED or if the imageType is
   * TYPE_BYTE_BINARY and the color map has more than 16 entries.
   * @see #TYPE_BYTE_BINARY
   * @see #TYPE_BYTE_INDEXED
   */
  public JMRBufferedImage(int width, int height, int imageType, IndexColorModel cm) {
    super(width, height, imageType, cm);
  }

  /**
   * Constructs a new <code>JMRBufferedImage</code> with a specified
   * <code>ColorModel</code> and <code>Raster</code> by means of the
   * corresponding <code>BufferedImage</code> constructor.  If the number and
   * types of bands in the <code>SampleModel</code> of the
   * <code>Raster</code> do not match the number and types required by
   * the <code>ColorModel</code> to represent its color and alpha
   * components, a {@link RasterFormatException} is thrown.  This
   * method can multiply or divide the color <code>Raster</code> data by
   * alpha to match the <code>alphaPremultiplied</code> state
   * in the <code>ColorModel</code>.  Properties for this
   * <code>BufferedImage</code> can be established by passing
   * in a {@link Hashtable} of <code>String</code>/<code>Object</code>
   * pairs.
   * @param cm <code>ColorModel</code> for the new image
   * @param raster     <code>Raster</code> for the image data
   * @param isRasterPremultiplied   if <code>true</code>, the data in
   *                  the raster has been premultiplied with alpha.
   * @param properties <code>Hashtable</code> of
   *                  <code>String</code>/<code>Object</code> pairs.
   * @exception <code>RasterFormatException</code> if the number and
   * types of bands in the <code>SampleModel</code> of the
   * <code>Raster</code> do not match the number and types required by
   * the <code>ColorModel</code> to represent its color and alpha
   * components.
   * @exception <code>IllegalArgumentException</code> if
   *		<code>raster</code> is incompatible with <code>cm</code>
   * @see ColorModel
   * @see Raster
   * @see WritableRaster
   */
  public JMRBufferedImage(ColorModel cm, WritableRaster raster, boolean isRasterPremultiplied, Hashtable properties) {
    super(cm, raster, isRasterPremultiplied, properties);
  }


  /**
   * Constructs a new <code>JMRBufferedImage</code> from a <code>BufferedImage</code>.
   * <p> The new <code>JMRBufferedImage</code> uses the same color model and raster of the <code>BufferedImage</code>
   * @param image <code>BufferedImage</code> which data is used to construct this object
   */
  public JMRBufferedImage(BufferedImage image){
    super(image.getColorModel(),image.getRaster(),image.isAlphaPremultiplied(),null);
  }



  /** Return the media type, in this case a JMR image
   * <p> Method inherit from JMRImage interface
   * @see #TYPE_JMR_IMAGE
   * @return The media type
   */
  public int getMediaType() {
    return (Media.TYPE_JMR_IMAGE);
  }

  /** Return the image type, in this case a BufferedImage
   * <p> Method inherit from JMRImage interface
   * @see #TYPE_BUFFERED_IMAGE
   * @return The image type
   */
  public int getImageType() {
    return (JMRImage.TYPE_BUFFERED_IMAGE);
  }

}
