package jmr.descriptor.mpeg7;

import java.awt.color.ColorSpace;
import java.awt.image.Raster;
import jmr.media.JMRExtendedBufferedImage;
import jmr.colorspace.ColorSpaceJMR;
import jmr.result.JMRResult;
import jmr.result.FloatResult;
import jmr.media.Media;
import jmr.descriptor.MediaDescriptor;

/**
 * Scalable Color Descriptor from MPEG7 standard.
 *
 * <p>
 * The Scalable Color Descriptor is a Color Histogram in HSV Color Space,
 * which is encoded by a Haar transform. Its binary representation is scalable
 * in terms of bin numbers and bit representation accuracy over a broad range of data rates.
 * The Scalable Color Descriptor is useful for image-to-image matching and retrieval based
 * on color feature. Retrieval accuracy increases with the {@link #nofCoefficients} used in
 * the representation.
 * <br/><a style="font-size:small;font-style:italic" href="http://www.chiariglione.org/mpeg/standards/mpeg-7">
 * Definition from this link</a>
 * </p>
 *
 * <p>
 * This class is inspired by ScalableColorImpl.java
 * from  <a href="http://www.semanticmetadata.net">Caliph & Emir project</a>
 * </p>
 *
 * @author RAT Benoit <br/>
 * (<a href="http://ivrg.epfl.ch" target="about_blank">IVRG-LCAV-EPFL</a> &
 *  <a href="http://decsai.ugr.es/vip" target="about_blank">VIP-DECSAI-UGR</a>)
 * @since 23 nov. 07
 *
 */
public class MPEG7ScalableColor extends MPEG7ColorDescriptor {

  /**  Should be on 3 bits and take value 16,32,64,128,256   */
  protected int nofCoefficients = 256;

  /**  Specifies discarding 0 to 8 bit planes (See MPEG7 specification).
   *
   * This properties are designed for hardware accelaration or specific code and
   * have no sens to be used in in this java interface.
   * <ul>
   * 	<li>If its value is 0, the coefficient are represented by their magitude and their signs
   * the Magnitude value also called bitPlanes is emulated by {@link #getBitPlane()}</li>
   *  <li>If its value is 8, the coefficient are represented only by their sign,
   *  the CoefficientSign value are emulated by the function {@link #getCoeffSign()}</li>
   *  </ul>
   *
   *   */
  protected int nofBitPlanesDiscarded = 0;

  /**  HSV histogram in Matrix Form   */
  protected int[][][] histoMx = null;

  /** <b>Final feature vector</b> set in a histogram */
  protected int[] histoHaar = null;

  /**  They respectively take values 16 ,4 and 4   */
  private int h_NofBins, s_NofBins, v_NofBins;
  private float h_Scale, s_Scale, v_Scale; //Using ColorSpaceJMR.CS_HSV it is 360+1/16, 1.001/4 and 1.001/4
  private int _xNumOfBlocks, _yNumOfBlocks; //Number of block to compute histoMx

  private static int[][] scalableColorQuantValues =
          {
                  {217, 9, 255}, {-71, 9, 255}, {-27, 8, 127}, {-54, 9, 255}, {-8, 7, 63}, {-14, 7, 63}, {-22, 7, 63}, {-29, 8, 127},
                  {-6, 6, 31}, {-13, 7, 63}, {-11, 6, 31}, {-22, 7, 63}, {-9, 7, 63}, {-14, 7, 63}, {-19, 7, 63}, {-22, 7, 63},
                  {0, 4, 7}, {-1, 5, 15}, {0, 3, 3}, {-2, 6, 31}, {1, 5, 15}, {-5, 6, 31}, {0, 5, 15}, {0, 7, 63},
                  {2, 5, 15}, {-2, 6, 31}, {-2, 5, 15}, {0, 7, 63}, {3, 5, 15}, {-5, 6, 31}, {-1, 6, 31}, {4, 7, 63},
                  {0, 3, 3}, {0, 3, 3}, {0, 3, 3}, {-1, 5, 15}, {0, 3, 3}, {0, 3, 3}, {-1, 5, 15}, {-2, 5, 15},
                  {-1, 5, 15}, {-1, 4, 7}, {-1, 5, 15}, {-3, 5, 15}, {-1, 5, 15}, {-2, 5, 15}, {-4, 5, 15}, {-5, 5, 15},
                  {-1, 5, 15}, {0, 3, 3}, {-2, 5, 15}, {-2, 5, 15}, {-2, 5, 15}, {-3, 5, 15}, {-3, 5, 15}, {0, 5, 15},
                  {0, 5, 15}, {0, 5, 15}, {0, 5, 15}, {2, 5, 15}, {-1, 5, 15}, {0, 5, 15}, {3, 6, 31}, {3, 5, 15},
                  {0, 2, 1}, {0, 2, 1}, {0, 3, 3}, {0, 4, 7}, {0, 2, 1}, {0, 2, 1}, {0, 3, 3}, {-1, 4, 7},
                  {-1, 4, 7}, {-1, 4, 7}, {-2, 5, 15}, {-1, 5, 15}, {-2, 5, 15}, {-2, 5, 15}, {-2, 5, 15}, {-1, 5, 15},
                  {0, 3, 3}, {0, 2, 1}, {0, 3, 3}, {-1, 4, 7}, {0, 2, 1}, {0, 3, 3}, {-1, 4, 7}, {-1, 5, 15},
                  {-2, 5, 15}, {-1, 4, 7}, {-2, 5, 15}, {-1, 5, 15}, {-3, 5, 15}, {-3, 5, 15}, {-2, 5, 15}, {0, 5, 15},
                  {0, 3, 3}, {0, 3, 3}, {0, 3, 3}, {-1, 4, 7}, {0, 3, 3}, {0, 3, 3}, {-2, 5, 15}, {-2, 5, 15},
                  {-2, 5, 15}, {-2, 4, 7}, {-2, 5, 15}, {-1, 5, 15}, {-3, 5, 15}, {-3, 5, 15}, {-1, 5, 15}, {0, 5, 15},
                  {1, 4, 7}, {0, 3, 3}, {0, 4, 7}, {-1, 4, 7}, {0, 3, 3}, {0, 4, 7}, {-1, 4, 7}, {0, 4, 7},
                  {-1, 4, 7}, {-1, 3, 3}, {-1, 4, 7}, {0, 4, 7}, {-1, 5, 15}, {0, 5, 15}, {1, 5, 15}, {-1, 5, 15},
                  {0, 2, 1}, {0, 2, 1}, {0, 3, 3}, {0, 3, 3}, {0, 2, 1}, {0, 2, 1}, {0, 3, 3}, {0, 3, 3},
                  {0, 2, 1}, {0, 2, 1}, {0, 3, 3}, {0, 4, 7}, {0, 2, 1}, {0, 2, 1}, {0, 3, 3}, {0, 3, 3},
                  {0, 3, 3}, {0, 2, 1}, {0, 3, 3}, {1, 4, 7}, {0, 2, 1}, {0, 3, 3}, {-1, 4, 7}, {1, 4, 7},
                  {0, 3, 3}, {0, 3, 3}, {0, 3, 3}, {0, 4, 7}, {0, 3, 3}, {0, 3, 3}, {-1, 4, 7}, {0, 4, 7},
                  {0, 3, 3}, {0, 2, 1}, {0, 3, 3}, {0, 3, 3}, {0, 2, 1}, {0, 2, 1}, {0, 3, 3}, {0, 3, 3},
                  {0, 3, 3}, {0, 2, 1}, {0, 3, 3}, {1, 4, 7}, {0, 2, 1}, {0, 3, 3}, {0, 4, 7}, {1, 4, 7},
                  {0, 3, 3}, {0, 2, 1}, {0, 3, 3}, {1, 5, 15}, {0, 3, 3}, {0, 3, 3}, {-1, 5, 15}, {2, 5, 15},
                  {0, 3, 3}, {0, 3, 3}, {0, 3, 3}, {0, 4, 7}, {0, 3, 3}, {0, 3, 3}, {-1, 4, 7}, {1, 5, 15},
                  {0, 3, 3}, {0, 2, 1}, {0, 3, 3}, {0, 3, 3}, {0, 2, 1}, {0, 3, 3}, {0, 4, 7}, {0, 4, 7},
                  {0, 3, 3}, {0, 2, 1}, {0, 3, 3}, {1, 4, 7}, {0, 3, 3}, {0, 3, 3}, {-1, 5, 15}, {1, 5, 15},
                  {0, 3, 3}, {0, 2, 1}, {-1, 3, 3}, {1, 5, 15}, {0, 3, 3}, {-1, 4, 7}, {-1, 5, 15}, {2, 5, 15},
                  {0, 3, 3}, {0, 3, 3}, {0, 3, 3}, {0, 4, 7}, {0, 3, 3}, {-1, 3, 3}, {0, 4, 7}, {1, 4, 7},
                  {1, 3, 3}, {0, 2, 1}, {-1, 3, 3}, {0, 3, 3}, {0, 3, 3}, {0, 3, 3}, {0, 3, 3}, {1, 4, 7},
                  {0, 3, 3}, {0, 2, 1}, {-1, 3, 3}, {0, 4, 7}, {0, 3, 3}, {0, 3, 3}, {0, 4, 7}, {1, 4, 7},
                  {0, 3, 3}, {0, 2, 1}, {0, 3, 3}, {0, 4, 7}, {0, 3, 3}, {-1, 3, 3}, {0, 4, 7}, {1, 4, 7},
                  {0, 3, 3}, {0, 3, 3}, {0, 3, 3}, {0, 3, 3}, {0, 3, 3}, {-1, 3, 3}, {0, 3, 3}, {-1, 4, 7}
          };


  private static final int[][] tabelle = new int[][] {
      {
      0, 2, 4, 6, 8, 10, 12, 14, 0, 2, 4, 6, 8, 10, 12, 14, 0, 2, 4, 6, 8, 10,
      12, 14, 0, 2, 4, 6, 8, 10, 12, 14, 0, 2, 4, 6, 8, 10, 12, 14, 0, 2, 4, 6,
      8, 10, 12, 14, 0, 2, 4, 6, 8, 10, 12, 14, 0, 2, 4, 6, 8, 10, 12, 14, 0, 2,
      4, 6, 8, 10, 12, 14, 0, 2, 4, 6, 8, 10, 12, 14, 0, 2, 4, 6, 8, 10, 12, 14,
      0, 2, 4, 6, 8, 10, 12, 14, 0, 2, 4, 6, 8, 10, 12, 14, 0, 2, 4, 6, 8, 10,
      12, 14, 0, 2, 4, 6, 8, 10, 12, 14, 0, 2, 4, 6, 8, 10, 12, 14, 0, 2, 4, 6,
      8, 10, 12, 14, 0, 2, 4, 6, 8, 10, 12, 14, 0, 2, 4, 6, 8, 10, 12, 14, 0, 2,
      4, 6, 8, 10, 12, 14, 0, 2, 4, 6, 8, 10, 12, 14, 0, 2, 4, 6, 8, 10, 12, 14,
      0, 2, 4, 6, 8, 10, 12, 14, 0, 2, 4, 6, 8, 10, 12, 14, 0, 2, 4, 6, 8, 10,
      12, 14, 0, 2, 4, 6, 8, 10, 12, 14, 0, 2, 4, 6, 8, 10, 12, 14, 0, 2, 4, 6,
      8, 10, 12, 14, 0, 4, 8, 12, 0, 4, 8, 12, 0, 4, 8, 12, 0, 4, 8, 12, 0, 4,
      8, 12, 0, 4, 8, 12, 0, 4, 8, 12, 0, 8, 0}
      , {
      0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2,
      2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5,
      5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8,
      8, 8, 8, 8, 8, 8, 9, 9, 9, 9, 9, 9, 9, 9, 10, 10, 10, 10, 10, 10, 10, 10,
      11, 11, 11, 11, 11, 11, 11, 11, 12, 12, 12, 12, 12, 12, 12, 12, 13, 13,
      13, 13, 13, 13,
      13, 13, 14, 14, 14, 14, 14, 14, 14, 14, 15, 15, 15, 15, 15, 15, 15, 15, 0,
      0, 0, 0,
      0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 4, 4, 4, 4, 4, 6, 6,
      6, 6, 6, 6, 6, 6, 8, 8, 8, 8, 8, 8, 8, 8, 10, 10, 10, 10, 10, 10, 10, 10,
      12, 12, 12, 12, 12, 12, 12, 12, 14, 14, 14, 14, 14, 14, 14, 14, 0, 0, 0,
      0, 0, 0,
      0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 8, 8, 8, 8, 8, 8, 8, 8, 10, 10, 10, 10,
      10, 10, 10, 10, 0, 0, 0, 0, 2, 2, 2, 2, 8, 8, 8, 8, 10, 10, 10, 10, 0, 0,
      0, 0, 8, 8, 8, 8, 0, 0, 0, 0, 0, 0, 0}
      ,

      {
      1, 3, 5, 7, 9, 11, 13, 15, 1, 3, 5, 7, 9, 11, 13, 15, 1, 3, 5, 7, 9, 11,
      13, 15, 1, 3, 5, 7, 9, 11, 13, 15, 1, 3, 5, 7, 9, 11, 13, 15, 1, 3, 5, 7,
      9, 11, 13, 15, 1, 3, 5, 7, 9, 11, 13, 15, 1, 3, 5, 7, 9, 11, 13, 15, 1, 3,
      5, 7, 9, 11, 13, 15, 1, 3, 5, 7, 9, 11, 13, 15, 1, 3, 5, 7, 9, 11, 13, 15,
      1, 3, 5, 7, 9, 11, 13, 15, 1, 3, 5, 7, 9, 11, 13, 15, 1, 3, 5, 7, 9, 11,
      13, 15, 1, 3, 5, 7, 9, 11, 13, 15, 1, 3, 5, 7, 9, 11, 13, 15, 0, 2, 4, 6,
      8, 10, 12, 14, 0, 2, 4, 6, 8, 10, 12, 14, 0, 2, 4, 6, 8, 10, 12, 14, 0, 2,
      4, 6, 8, 10, 12, 14, 0, 2, 4, 6, 8, 10, 12, 14, 0, 2, 4, 6, 8, 10, 12, 14,
      0, 2, 4, 6, 8, 10, 12, 14, 0, 2, 4, 6, 8, 10, 12, 14, 0, 2, 4, 6, 8, 10,
      12, 14, 0, 2, 4, 6, 8, 10, 12, 14, 0, 2, 4, 6, 8, 10, 12, 14, 0, 2, 4, 6,
      8, 10, 12, 14, 2, 6, 10, 14, 2, 6, 10, 14, 2, 6, 10, 14, 2, 6, 10, 14, 0,
      4,
      8, 12, 0, 4, 8, 12, 0, 4, 8, 12, 4, 12, 8}
      ,

      {
      0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2,
      2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5,
      5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8,
      8, 8, 8, 8, 8, 8, 9, 9, 9, 9, 9, 9, 9, 9, 10, 10, 10, 10, 10, 10, 10, 10,
      11, 11, 11, 11, 11, 11, 11, 11, 12, 12, 12, 12, 12, 12, 12, 12, 13, 13,
      13, 13, 13, 13,
      13, 13, 14, 14, 14, 14, 14, 14, 14, 14, 15, 15, 15, 15, 15, 15, 15, 15, 1,
      1, 1, 1,
      1, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 5, 5, 5, 5, 5, 5, 5, 5, 7, 7,
      7, 7, 7, 7, 7, 7, 9, 9, 9, 9, 9, 9, 9, 9, 11, 11, 11, 11, 11, 11, 11, 11,
      13, 13, 13, 13, 13, 13, 13, 13, 15, 15, 15, 15, 15, 15, 15, 15, 4, 4, 4,
      4, 4, 4,
      4, 4, 6, 6, 6, 6, 6, 6, 6, 6, 12, 12, 12, 12, 12, 12, 12, 12, 14, 14, 14,
      14,
      14, 14, 14, 14, 0, 0, 0, 0, 2, 2, 2, 2, 8, 8, 8, 8, 10, 10, 10, 10, 2, 2,
      2, 2, 10, 10, 10, 10, 8, 8, 8, 8, 0, 0, 0}
      ,

      {
      128, 128, 128, 128, 128, 128, 128, 128, 128, 128, 128,
      128, 128, 128, 128, 128, 128, 128, 128, 128, 128, 128,
      128, 128, 128, 128, 128, 128, 128, 128, 128, 128, 128,
      128, 128, 128, 128, 128, 128, 128, 128, 128, 128, 128,
      128, 128, 128, 128, 128, 128, 128, 128, 128, 128, 128,
      128, 128, 128, 128, 128, 128, 128, 128, 128, 128, 128,
      128, 128, 128, 128, 128, 128, 128, 128, 128, 128, 128,
      128, 128, 128, 128, 128, 128, 128, 128, 128, 128, 128,
      128, 128, 128, 128, 128, 128, 128, 128, 128, 128, 128,
      128, 128, 128, 128, 128, 128, 128, 128, 128, 128, 128,
      128, 128, 128, 128, 128, 128, 128, 128, 128, 128, 128,
      128, 128, 128, 128, 128, 128, 128, 64, 64, 64, 64,
      64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64,
      64, 64, 64, 64,
      64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64,
      64, 64, 64, 64,
      64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 32, 32,
      32, 32, 32, 32,
      32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32,
      32, 32, 32, 32,
      32, 32, 32, 32, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16,
      16, 16, 8, 8,
      8, 8, 8, 8, 8, 8, 4, 4, 4, 4, 2, 2, 1}
  };

  private static final int[] sorttab = new int[] {
      0, 4, 8, 12, 32, 36, 40, 44, 128, 132, 136, 140, 160, 164, 168, 172,
      2, 6, 10, 14, 34, 38, 42, 46, 130, 134, 138, 142, 162, 166, 170, 174,
      64, 66, 68, 70, 72, 74, 76, 78, 96, 98, 100, 102, 104, 106, 108, 110, 192,
      194, 196, 198, 200, 202, 204, 206, 224, 226, 228, 230, 232, 234, 236, 238,
      16, 18, 20, 22, 24, 26, 28, 30, 48, 50, 52, 54, 56, 58, 60, 62, 80, 82,
      84, 86, 88, 90, 92, 94, 112, 114, 116, 118, 120, 122, 124, 126, 144, 146,
      148, 150, 152, 154, 156, 158, 176, 178, 180, 182, 184, 186, 188, 190, 208,
      210, 212, 214, 216, 218, 220, 222, 240, 242, 244, 246, 248, 250, 252, 254,
      1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, 33, 35, 37, 39,
      41, 43, 45, 47, 49, 51, 53, 55, 57, 59, 61, 63, 65, 67, 69, 71, 73, 75,
      77, 79, 81, 83, 85, 87, 89, 91, 93, 95, 97, 99, 101, 103, 105, 107, 109,
      111, 113, 115, 117, 119, 121, 123, 125, 127, 129, 131, 133, 135, 137, 139,
      141, 143, 145, 147, 149, 151, 153, 155, 157, 159, 161, 163, 165, 167, 169,
      171, 173, 175, 177, 179, 181, 183, 185, 187, 189, 191, 193, 195, 197, 199,
      201, 203, 205, 207, 209, 211, 213, 215, 217, 219, 221, 223, 225, 227, 229,
      231, 233, 235, 237, 239, 241, 243, 245, 247, 249, 251, 253, 255
  };

  /**
   * Constructor initiating the descriptor without computing the feature vector from an image
   * the method {@link #extract(ImageJMR)} need to be called after construction of this descriptor
   *
   * @param numC  Number of coeefficient in the histogram : 32,64,128,256
   * @param numB  Number of bitplanes discarded in the histograms response
   * (Actually this is not implemented for this Java code, but its emulated to follow MPEG7 standard)
   */
  public MPEG7ScalableColor(int numC, int numB) {
    super(MediaDescriptor.TYPE_MPEG7_SCD_DESCRIPTOR, ColorSpaceJMR.CS_HSV);
    this.nofBitPlanesDiscarded = numB;
    this.nofCoefficients = numC;
    _xNumOfBlocks = 1;
    _yNumOfBlocks = 1;
    h_NofBins = 16;
    s_NofBins = 4;
    v_NofBins = 4;
  }

  /**
   *  Constructor calling {@link #MPEG7ScalableColor(int, int)} with default
   * value {@link #nofCoefficients}=256 and {@link #nofBitPlanesDiscarded}=0
   * */
  public MPEG7ScalableColor() {
    this(256, 0);
  }

  /**
   * Constructor initiating the descriptor and computing the resulting feature vector from an image
   *
   * @param im		Input image
   * @param numC		Number of coeefficient in the histogram : 32,64,128,256
   * @param numB  	Number of bitplanes discarded in the histograms response
   * (Actually this is not implemented for this Java code, but its emulated to follow MPEG7 standard)
   */
  public MPEG7ScalableColor(JMRExtendedBufferedImage im, int numC, int numB) {
    this(numC,numB);
    calculate(im);
  }

  /**
   *  Constructor calling {@link #MPEG7ScalableColor(ImageJMR, int, int)} with default
   * value {@link #nofCoefficients}=256 and {@link #nofBitPlanesDiscarded}=0
   * */
  public MPEG7ScalableColor(JMRExtendedBufferedImage im) {
    this(im, 256, 0);
  }

  /** Compare two DSC using the l1-norm between each bins of the histograms.
   *
   * <p>In the case that the two SCD have different paramaters or that
   * the Feature Vector is still not computed the  code will thrown an
   * exeception of the type {@link UnsupportedOperationException}.
   * </p>
   *
   * @param desc <code>MPEG7ScalableColor</code> object to be compared
   */
  public FloatResult compare(MPEG7ScalableColor desc) {
    //Check if the parameters between the two SCD are the same:
    if (checkParam(desc)) {
      int diffsum = 0;
      for (int i = 0; i < nofCoefficients; i++) {
        diffsum += Math.abs(this.histoHaar[i] - desc.histoHaar[i]);
      }
      return (new FloatResult( (float) diffsum));
    }
    else {
      return (null);
    }
  }

  /** Compare this <code>MPEG7ScalableColor</code> obtect with the
   * <code>MPEG7ScalableColor</code> given by parameter
   * <p> This method is valid only for <code>MPEG7ScalableColor</code>
   *  media descriptors
   * @param mediaDescriptor MediaDescriptor object to be compared
   * @see #compare(MPEG7ScalableColor desc)
   * @return The difference between descriptors
   */
  public JMRResult compare(MediaDescriptor mediaDescriptor) {
    // Only MPEG7ScalableColor objects can be compared
    if (! (mediaDescriptor instanceof MPEG7ScalableColor)) {
      return (null);
    }
    return (compare( (MPEG7ScalableColor) mediaDescriptor));
  }

  /** Simply check if the parameters between this ScalableColor Descriptor and the
   * one sent in paramenters are matching.
   * @param scd2
   * @return 	true if the matching is okay
   */
  private boolean checkParam(MPEG7ScalableColor scd2) {
    if (scd2.nofBitPlanesDiscarded != nofBitPlanesDiscarded) {
      throw new UnsupportedOperationException(
          "NumberOfBitPlanesDiscarded is not matching");
    }
    if (scd2.nofCoefficients != nofCoefficients) {
      throw new UnsupportedOperationException("nofCoefficients is not matching");
    }
    if (scd2.histoHaar == null || this.histoHaar == null) {
      throw new UnsupportedOperationException(
          "One of the Descriptor histograms is NULL");
    }
    return true;
  }

  /** Computes the <code>MPEG7ScalableColor</code> descriptor for
   * the media given by parameter
   * @param media The media from which the descriptor is calculated
   */
  public void calculate(Media media) {
    // The MPEG7ScalableColor can be calculated only from JMRExtendedBufferedImage
    if (media instanceof JMRExtendedBufferedImage) {
      calculate( (JMRExtendedBufferedImage) media);
    }
  }

  /** Computes the <code>MPEG7ScalableColor</code> descriptor for
   * the image given by parameter
   * @param im The image from which the descriptor is calculated
   */
  public void calculate(JMRExtendedBufferedImage im) {
    if (!checkImage(im)) {
      im = convertImg(im);
    }
    int wImg = im.getWidth();
    int hImg = im.getHeight();
    Raster imRst = im.getRaster();
    float[] pix = new float[3];

    //Check if NumOfBlocks is not bigger than number of pixel
    if (_xNumOfBlocks > wImg) {
      _xNumOfBlocks = wImg;
    }
    if (_yNumOfBlocks > hImg) {
      _yNumOfBlocks = hImg;

      //Compute the width and height of the blocks
    }
    int wBlock = wImg / _xNumOfBlocks;
    int hBlock = hImg / _yNumOfBlocks;

    //Reset the histograms count
    histoMxReset();
    histoMxSetNorma(im.getColorModel().getColorSpace());

    // Position of the block
    int xBlock = 0;
    int yBlock = 0;

    /* Quantisation and histogram-calculation using block-based iterator */

    //Iterate on each block
    for (int m = 0; m < _xNumOfBlocks; m++, xBlock += wBlock, yBlock = 0) {
      for (int n = 0; n < _yNumOfBlocks; n++, yBlock += hBlock) {
        //Iterate on heach pixel inside the block
        for (int y = yBlock; y < (yBlock + hBlock); y++) {
          for (int x = xBlock; x < xBlock + wBlock; x++) {
            imRst.getPixel(x, y, pix);
            histoMxQuantAndFill(pix);
          }
        }
      }
    }
    //Change the histogram from a Matrix structure to a Vector structure
    int[] histoVec = histoMx2histoVec();
    QuantizeHistogram(histoVec);
    this.histoHaar = HaarTransform(histoVec);
  }

  private int[] histoMx2histoVec() {
    int[] histoVec = new int[h_NofBins * v_NofBins * s_NofBins];
    int count = 0;
    for (int k = 0; k < v_NofBins; k++) {
      for (int l = 0; l < s_NofBins; l++) {
        for (int m = 0; m < h_NofBins; m++) {
          histoVec[count] = histoMx[m][l][k];
          count++;
        }
      }
    }
    return histoVec;
  }

  private void histoMxQuantAndFill(float[] pix) {
    int i, j, k;
//		i = (int) ((int)(255*(pix[0]/360f)) *(h_NofBins / 256f));            //H in bin levels
//		j = (int) ((int)(255*pix[1]) *(s_NofBins / 256f));            //S in bin levels
//		k = (int) ((int)(255*pix[2])* (v_NofBins / 256f));            //V in bin levels

    i = (int) (pix[0] / h_Scale); //H in bin levels
    j = (int) (pix[1] / s_Scale); //S in bin levels
    k = (int) (pix[2] / v_Scale); //V in bin levels

    histoMx[i][j][k]++;
  }

  /**
   * Obtain the scaling number to transform an HSV value in a bin index for the matrix
   * <p>
   * For example : S=[0,1] and its histogram has 4 bins: The scaling is then (1+epsilon)/4 because
   * using 1/4 will gave us 5 bins which is bad:
   * Round(1/(1/4))=4 and bin[4] doesn't exist whereas Round(4*1/1+epsilon) = 3.
   * </p>
   *
   * @param cS the ColorSpace to know the maximum value of each component
   */
  private void histoMxSetNorma(ColorSpace cS) {
    if (cS.getType() == ColorSpaceJMR.CS_HSV) {
      h_Scale = (cS.getMaxValue(0) + 1) / (float) h_NofBins;
      s_Scale = (cS.getMaxValue(1) + 1 / 255f) / (float) s_NofBins;
      v_Scale = (cS.getMaxValue(2) + 1 / 255f) / (float) v_NofBins;
    }
    else {
      System.err.println(
          "You need to use HSV ColorSpace to perform this operation");
    }
  }

  private void histoMxReset() {
    histoMx = new int[h_NofBins][s_NofBins][v_NofBins];
    for (int k = 0; k < h_NofBins; k++) {
      for (int l = 0; l < s_NofBins; l++) {
        for (int m = 0; m < v_NofBins; m++) {
          histoMx[k][l][m] = 0;
        }
      }
    }
  }

  int[] QuantizeHistogram(int[] aHist) {

    int sumPixels = 0;
    for (int i = 0; i < aHist.length; i++) {
      sumPixels += aHist[i];

      //System.out.println("Sum of Pixels:"+sumPixels+" NumOfCoeff="+nofCoefficients);

      // ** from XM ...
    }
    int factor = 0, ibinwert;
    //	  unsigned long NoOfBitsProBin=11;
    double binwert;

    //	  factor=0;
    //	  for (i=0; i<NoOfBitsProBin;i++)
    //	    factor=2*factor+1;

    factor = 0x7ff; //NoBitsProBin=11

    //quantisierung der bins
    for (int i = 0; i < nofCoefficients; i++) {
      binwert = (double) (factor) * (double) (aHist[i] / (double) sumPixels);

      ibinwert = (int) (binwert + 0.49999);
      if (ibinwert > factor) {
        ibinwert = factor; //obsolete

      }
      aHist[i] = ibinwert;
    }

    factor = 15;
    int iwert = 0;
    double wert, potenz = 0.4;
    double arg, maxwert;

    maxwert = (double) 40 * (double) 2047 / (double) 100;

    for (int i = 0; i < nofCoefficients; i++) {
      wert = (double) (aHist[i]);

      if (wert > maxwert) {
        iwert = factor;

      }
      if (wert <= maxwert) {
        arg = wert / maxwert;
        wert = (float) factor * Math.pow(arg, potenz);
        iwert = (int) (wert + 0.5);
      }

      if (iwert > factor) {
        iwert = factor;

      }
      aHist[i] = iwert;
    }
    return aHist;
  }

  /**
   * HaarTransform find in Caliph & Emir.
   * @param	aHist 	256 histograms value containing 16H*4S*4V
   * @return			The histogram after haar tranform and before quantification.
   */
  private int[] HaarTransform(int[] aHist) {
    int index, hist_nr;
    int[] histogram_in, histogram_out;
    int RecHistogram = 0;
    int max_color = 256;

    hist_nr = 256;

    RecHistogram = 0;

    histogram_in = new int[max_color];
    histogram_out = new int[max_color];

    for (int i = 0; i < nofCoefficients; i++) {
      histogram_in[i] = (int) aHist[i];
    }

    if (RecHistogram == 2) {
      histo_3d_hirarch_16_5(tabelle, tabelle[0].length, histogram_in, h_NofBins,
                            s_NofBins, v_NofBins, hist_nr);
      hsv_hir_quant_lin_5(histogram_in);
    }

    if (RecHistogram != 2) {
      histo_3d_hirarch_5(tabelle, tabelle[0].length, histogram_in, h_NofBins,
                         s_NofBins, v_NofBins, hist_nr);

      for (int j = 0; j < 256; ++j) {
        index = sorttab[j];
        histogram_out[j] = histogram_in[index];
      }

      hsv_hir_quant_lin_5(histogram_out);
      red_bits_pro_bin_5(histogram_out, nofBitPlanesDiscarded, 0);

    }
    int[] returnHist = new int[hist_nr];
    System.arraycopy(histogram_out, 0, returnHist, 0, hist_nr);

    return returnHist;

  }

  /**
   * This method came from <a href="www.semanticmetadata.net">Caliph & Emir project</a>
   *
   * @param tabelle represent which input is taken during the haar transform to perform the basic unit function
   * @param tablae Second dimension (height) of the tabelle = 254 links corresponding to the
   * @param histogram is the 256 histograms values
   * @param h_size Number of bin for H dimension which is normally equal to 16
   * @param s_size Number of bin for S dimension which is normally equal to 4
   * @param v_size Number of bin for V dimension which is normally equal to 4
   * @param hist_nr
   */
  static void histo_3d_hirarch_5(int[][] tabelle, int tablae, int[] histogram,
                                 int h_size, int s_size, int v_size,
                                 int hist_nr) {
    int sum, dif, x1, y1, x2, y2;

    //Matrix is the 2D transformation from the 3D Matrix histogram
    int[][] matrix = new int[16][16];

    //Filling matrix with Mx{H=[1,16]}{S*V=[1,4]*[1,4]}
    for (int i = 0; i < h_size * s_size * v_size; ++i) {
      matrix[i % (h_size)][i / (h_size)] = histogram[i];

    }

    for (int i = 0; i < tablae; ++i) {
      y1 = tabelle[0][i];
      x1 = tabelle[1][i];
      y2 = tabelle[2][i];
      x2 = tabelle[3][i];
      sum = matrix[y1][x1] + matrix[y2][x2];
      dif = matrix[y2][x2] - matrix[y1][x1];

      matrix[y1][x1] = sum;
      matrix[y2][x2] = dif;
    }

    for (int i = 0; i < h_size * s_size * v_size; ++i) {
      histogram[i] = matrix[i % (h_size)][i / (h_size)];
    }
  }

  // von XM ... :)
  private static void histo_3d_hirarch_16_5(int[][] tabelle, int tablae,
                                            int[] histogram,
                                            int h_size, int s_size, int v_size,
                                            int hist_nr) {
    int i, sum, dif, x1, y1, x2, y2;
    int[][] matrix = new int[16][16];
    int iprint = 0;

    for (i = 0; i < h_size * s_size * v_size; ++i) {
      matrix[i % (h_size)][i / (h_size)] = histogram[i];

    }
    for (i = 0; i < tablae; ++i) {
      if (tabelle[4][i] <= 8) {
        continue;
      }
      y1 = tabelle[0][i];
      x1 = tabelle[1][i];
      y2 = tabelle[2][i];
      x2 = tabelle[3][i];
      sum = matrix[y1][x1] + matrix[y2][x2];
      dif = matrix[y2][x2] - matrix[y1][x1];

      if (iprint == 1) {

        matrix[y1][x1] = sum;
      }
      matrix[y2][x2] = dif;
    }

    for (i = 0; i < h_size * s_size * v_size; ++i) {
      histogram[i] = matrix[i % (h_size)][i / (h_size)];
    }
  }

  // XM Kauderwelsch :)
  static void red_bits_pro_bin_5(int[] histogram,
                                 int NumberOfBitplanesDiscarded,
                                 int ivert) {
    int wert, wert1, bits_pro_bin, bits_pro_bild;
    int max_bits_pro_bin, anzkof;
    if (NumberOfBitplanesDiscarded == 0) {
      return;
    }

    bits_pro_bild = 0;
    max_bits_pro_bin = 0;
    anzkof = 0;
    if (NumberOfBitplanesDiscarded > 0) {
      for (int i = 0; i < 256; ++i) {
        bits_pro_bin = scalableColorQuantValues[i][1] -
            NumberOfBitplanesDiscarded;
        if (bits_pro_bin < 2) {
          wert = histogram[i];
          if (wert >= 0) {
            histogram[i] = 1;
          }
          if (wert < 0) {
            histogram[i] = 0;
          }
          bits_pro_bild = bits_pro_bild + 1;
        }
        if (bits_pro_bin >= 2) {
          wert = histogram[i];
          wert1 = wert;
          if (wert < 0) {
            wert = -wert;
          }
          bits_pro_bild = bits_pro_bild + bits_pro_bin;
          if (bits_pro_bin > max_bits_pro_bin) {
            max_bits_pro_bin = bits_pro_bin;
          }
          anzkof = anzkof + 1;

          for (int j = 0; j < NumberOfBitplanesDiscarded; ++j) {
            wert = wert >> 1;

//						if ((wert == 0) && (wert1 >= 0)) histogram[i] = 0;
//						if ((wert == 0) && (wert1 < 0)) histogram[i] = 1;
          }
          if (wert1 < 0) {
            wert = -wert;
          }
          histogram[i] = wert;
        }
      }
    }
  }

  // XM Kauderwelsch :)
  private static void hsv_hir_quant_lin_5(int[] histogram) {
    int i, wert, maxwert;
    for (i = 0; i < 256; ++i) {
      maxwert = scalableColorQuantValues[i][2];
      wert = histogram[i] - scalableColorQuantValues[i][0];
      if (wert > maxwert) {
        wert = maxwert;
      }
      if (wert < -maxwert) {
        wert = -maxwert;
      }
      histogram[i] = wert;
    }
  }

  public byte[] getCoeffSign() {
    byte[] coeffSign = new byte[nofCoefficients / 8];
    for (int i = 0; i < coeffSign.length; i++) {
      byte tmp = 0;
      int offset = i * 8;
      for (int j = 0; j < 8; j++) {
        if (this.histoHaar[j + offset] < 0) {

          //The operation ( 1 << j) shift the value 00000001 of j step: (1 << 3)=00001000
          tmp |= (1 << j); //10000001 OR 10001000 = 10001001 (change only if 1)
        }
        else {
          tmp &= ~ (1 << j); //10010001 AND NOT 10000000 =  00010001 (replace 1 by zero)
        }
      }
      coeffSign[i] = tmp;
    }
    return coeffSign;
  }

  public byte[] getBitPlane() {
    byte[] bitPlane = new byte[nofCoefficients];
    for (int i = 0; i < this.histoHaar.length; i++) {
      bitPlane[i] = (byte) (Math.abs(this.histoHaar[i]) & 0x000000FF);
    }
    return bitPlane;
  }

  public byte[] getFeatureVector() {
    byte[] sign = getCoeffSign();
    byte[] magn = getBitPlane();

    byte[] fVec = new byte[magn.length + sign.length];
    for (int i = 0; i < magn.length; i++) {
      fVec[i] = magn[i];
    }
    for (int i = 0; i < sign.length; i++) {
      fVec[i + magn.length] = sign[i];

    }
    return fVec;

  }

  public void setFeatureVector(byte[] data) {

  }

  public void setHistoHaar(byte[] magnitud, byte[] sign) {
    byte tmp = 0;
    if (magnitud.length != sign.length * 8) {
      System.err.println("setHistoHaar Error Magnitud != 8*sign");
      return;
    }
    histoHaar = new int[magnitud.length];
    for (int i = 0; i < magnitud.length; i++) {
      if (i % 8 == 0) {
        tmp = sign[i / 8]; //Take a byte each 8 incrementation
        //last bit=1 this mean negative sign
      }
      histoHaar[i] = magnitud[i] & 0x000000FF; //We don't use the sign here.
      if ( (tmp & 0x01) == 1) {
        histoHaar[i] *= -1;
      }
      tmp = (byte) (tmp >> 1); //Shift one bit each round
    }
    //my.Debug.printTab(histoHaar);
  }

  protected boolean checkImage(JMRExtendedBufferedImage im) {
    boolean ok = checkColorSpace(im.getColorModel().getColorSpace());
    //if(!ok) System.err.println("This image is not encoded in the correct color space");
    ok = ok &&
        (im.getType() == JMRExtendedBufferedImage.TYPE_JMR_3F_INTERLEAVED);
    //if(!ok) System.err.println("This image does not use the correct sample model");
    return ok;
  }

  public boolean isComputed() {
    return (histoHaar != null);
  }

  /*
   * @see es.ugr.siar.ip.desc.VisualDescriptor#multiply(float)
   */
  public void multiply(float factor) {
    for (int i = 0; i < histoHaar.length; i++) {
      histoHaar[i] *= factor;
    }
  }

  /*
   * @see es.ugr.siar.ip.desc.VisualDescriptor#sum(es.ugr.siar.ip.desc.VisualDescriptor)
   */
  public void sum(MPEG7ScalableColor desc) {
    if (desc instanceof MPEG7ScalableColor) {
      MPEG7ScalableColor scd = (MPEG7ScalableColor) desc;
      for (int i = 0; i < histoHaar.length; i++) {
        this.histoHaar[i] += scd.histoHaar[i];
      }
    }
  }

}
