package jmr.descriptor.mpeg7;

import java.awt.image.Raster;
import jmr.media.JMRExtendedBufferedImage;
import jmr.colorspace.ColorSpaceJMR;
import jmr.descriptor.MediaDescriptor;
import jmr.result.JMRResult;
import jmr.result.FloatResult;
import jmr.media.Media;

/**
 * Color Structure Descriptor from MPEG7 standard.
 *
 * <p>The Color structure descriptor is a color feature descriptor that captures
 *  both color content (similar to a color histogram) and information about the
 *  structure of this content. Its main functionality is image-to-image matching
 *  and its intended use is for still-image retrieval, where an image may consist
 *  of either a single rectangular frame or arbitrarily shaped, possibly disconnected,
 *  regions. The extraction method embeds color structure information into the descriptor
 *  by taking into account all colors in a structuring element of 8x8 pixels that
 *  slides over the image, instead of considering each pixel separately. Unlike
 *  the color histogram, this descriptor can distinguish between two images in which
 *  a given color is present in identical amounts but where the structure of the
 *  groups of pixels having that color is different in the two images.
 *  Color values are given by the {@link ColorSpaceHMMD} which is quantized non-uniformly
 *  into 32, 64, 128 or 256 bins {@link #qLevels}.
 *  Each bin amplitude value is represented by an
 *  8-bit code. The Color Structure descriptor provides additional functionality
 *  and improved similarity-based image retrieval performance for natural images
 *  compared to the ordinary color histogram.
 * <br/><a style="font-size:small;font-style:italic" href="http://www.chiariglione.org/mpeg/standards/mpeg-7">
 * Definition from this link</a>
 * </p>
 *
 * <p>
 * This class is inspired by ColorStructureImplementation.java
 * from  <a href="http://www.semanticmetadata.net">Caliph & Emir project</a>
 * </p>
 *
 * @author RAT Benoit <br/>
 * (<a href="http://ivrg.epfl.ch" target="about_blank">IVRG-LCAV-EPFL</a> &
 *  <a href="http://decsai.ugr.es/vip" target="about_blank">VIP-DECSAI-UGR</a>)
 * @version 1.0
 * @since 23 nov. 07
 *
 */
public class MPEG7ColorStructure extends MPEG7ColorDescriptor {

  /**  Quantization Level of the HMMD ColorSpace   */
  protected int qLevels = 256;

  /** <b>Final feature vector</b> set as the Color Structure Histogram  */
  protected int[] histo = null;

  private static final int HUE = 0;
  private static final int MAX = 1;
  private static final int MIN = 2;
  private static final int DIFF = 3;

  /** Quantization table for 256, 128, 64 and 32 quantisation bins and its Offset. */
  private int offset = 0;
  private static final int[][][] quantTable = {
    {{1 ,8 },{4 ,4 },{4 ,4 },{4 ,1 },{4 ,1 }},		// Hue, Sum - subspace 0,1,2,3,4 for  32 levels
    {{1 ,8 },{4 ,4 },{4 ,4 },{8 ,2 },{8 ,1 }},		// Hue, Sum - subspace 0,1,2,3,4 for  64 levels
    {{1 ,16},{4 ,4 },{8, 4 },{8, 4 },{8 ,4 }}, 		// Hue, Sum - subspace 0,1,2,3,4 for 128 level
    {{1 ,32},{4 ,8 },{16,4 },{16,4 },{16,4 }}};		// Hue, Sum - subspace 0,1,2,3,4 for 256 levels

  protected static final int DEFAULT_NUM_LEVELS = 256;

  /**
   * Default constructor without parameters.
   * <p>We use the MPEG7 recommendation as default parameters <ul>
   * <li>{@link #qLevels} = {@link #DEFAULT_NUM_LEVELS}</li>
   * </ul></p>
   */
  public MPEG7ColorStructure() {
    this(DEFAULT_NUM_LEVELS);
  }

  /**
   * Constructor
   *  @param qLevels Number of quantization levels
   */
  public MPEG7ColorStructure(int qLevels) {
    super(MediaDescriptor.TYPE_MPEG7_CSD_DESCRIPTOR, ColorSpaceJMR.CS_HMMD);
    init(qLevels);
  }

  /**
   * Constructs the object for an image using the default parameters.
   * @param im	The image
   */
  public MPEG7ColorStructure(JMRExtendedBufferedImage im) {
    this(im, DEFAULT_NUM_LEVELS);
  }

  /**
   * Constructs the object for an image using the specified parameters.
   * @param im	The image
   * @param qLevels Number of quantization levels
   */
  public MPEG7ColorStructure(JMRExtendedBufferedImage im, int qLevels) {
    this(qLevels);
    calculate(im);
  }

  /* Initialize qLevels */
  private void init(int qLevels) {
    if (qLevels <= 32) {
      this.qLevels = 32;
    }
    else if (qLevels <= 64) {
      this.qLevels = 64;
    }
    else if (qLevels <= 128) {
      this.qLevels = 128;
    }
    else {
      this.qLevels = 256;
    }
    this.offset = (int) log2(qLevels) - 5; //Because 2^5=32 ==> log2(32)-5 = 0
  }

  /** Compare this <code>MPEG7ColorStructure</code> obtect with the
   * <code>MPEG7ColorStructure</code> given by parameter
   * <p> This method is valid only for <code>MPEG7ColorStructure</code>
   *  media descriptors
   * @param mediaDescriptor MediaDescriptor object to be compared
   * @see #compare(MPEG7ColorStructure desc)
   * @return The difference between descriptors
   */
  public JMRResult compare(MediaDescriptor mediaDescriptor) {
    // Only MPEG7ColorStructure objects can be compared
    if (! (mediaDescriptor instanceof MPEG7ColorStructure)) {
      return (null);
    }
    return (compare( (MPEG7ColorStructure) mediaDescriptor));
  }

  /** Compare two CSD using the l1-norm between each bins of the histograms.
   *
   * <p>In the case that the two ColorStructure Descriptor don't have the same
   * {@link #qLevels} numbers we perform a resize operation on the biggest
   * FeatureVec using the {@link #resizeCSD(MPEG7ColorStructure, int)} method.
   * </p>
   * @param desc <code>MPEG7ColorStructure</code> object to be compared
   */
  public FloatResult compare(MPEG7ColorStructure desc) {
    int[] f1, f2;
    if (desc.histo == null || this.histo == null) {
      return(null);
    }
    //Resize featureVector in they are not the same size
    if (this.qLevels == desc.qLevels) {
      f1 = this.histo;
      f2 = desc.histo;
    }
    else if (this.qLevels < desc.qLevels) {
      f1 = this.histo;
      f2 = resizeCSD(desc, this.qLevels);
    }
    else {
      f1 = resizeCSD(this, desc.qLevels);
      f2 = desc.histo;
    }
    float val = 0f;
    for (int i = 0; i < f1.length; i++) {
      val += Math.abs(f1[i] - f2[i]);
    }
    val /= (256 * f1.length); //Normalization is the maximum distance multiply by the number of bins

    return new FloatResult(val);
  }

  /** Computes the <code>MPEG7ColorStructure</code> descriptor for
   * the media given by parameter
   * @param media The media from which the descriptor is calculated
   */
  public void calculate(Media media) {
    // The MPEG7ColorStructure can be only calculated from JMRExtendedBufferedImage
    if (media instanceof JMRExtendedBufferedImage) {
      calculate( (JMRExtendedBufferedImage) media);
    }
  }

  /** Computes the structured histogram descriptor for
   * the image given by parameter
   * @param im The image from which the descriptor is calculated
   */
  public void calculate(JMRExtendedBufferedImage im) {
    if (!checkImage(im)) {
      im = convertImg(im);
    }
    byte[][] imQ = quantHMMDImage(im);
    float[] histo = structuredHisto(imQ, im.getWidth(), im.getHeight());
    this.histo = reQuantization(histo);
  }

  /**
   * Convert the image in the HMMD color space quantization.
   *
   * <p> It first look in which subspace a HMMD value is by looking at the DIFF component.
   * Then it used the {@link #quantTable} to obtain the bin value for HUE and SUM and
   * finally each bins value for each component is merge to be put in the qLevels quantification.
   * </p>
   *
   * @param 	imSrc 	an image in the HMMD Color Space {@link ColorSpaceHMMD}
   * @return			a byte matrix representing the quantifized values between [0,qLevels]
   * This matrix is transposed. It is in the form : imgQuant[height][width].
   */
  private byte[][] quantHMMDImage(JMRExtendedBufferedImage imSrc) {
    //Source image variable
    int wImg = imSrc.getWidth();
    int hImg = imSrc.getHeight();
    Raster imRst = imSrc.getRaster();
    float[] pix = new float[4];
    //Destination image array
    byte[][] imDst = new byte[hImg][wImg];
    int subspace, hue_bin, sum_bin, v;
    int[] startSubSpacePos = getStartSubspacePos();
    for (int y = 0; y < hImg; y++) {
      for (int x = 0; x < wImg; x++) {
        imRst.getPixel(x, y, pix);
        // define the subspace along the Diff axis
        subspace = getSubspace(pix[DIFF]);
        //Obtain the value of the hue in this quantization space
        hue_bin = (int) ( (pix[HUE] / 361.0f) * quantTable[offset][subspace][0]);
        //Obtain the value of the sum and multiply it by the hue value
        float tmp = ( (pix[MIN] + pix[MAX]) / 2 - 1 / 255);
        sum_bin = (int) (tmp * quantTable[offset][subspace][1]);
        if (sum_bin != 0) {
          tmp = 0;
          //Shift until the start position for this subspace in the histogram
        }
        v = startSubSpacePos[subspace] +
            sum_bin * quantTable[offset][subspace][0] + hue_bin;
        //Check if value is not bigger than qLevels
        if (v >= qLevels) {
          System.err.println("Value computed is bigger than qLevels");
          return null;
        }
        //Set the value of the float
        imDst[y][x] = (byte) (v);
      }
    }
    return imDst;
  }

  /**
   * Return the CSD histograms with value between 0 and 1.
   *
   * <p> Create a structuring block elements according to the size of the
   * quantified HMMD image. Then compute a local histogram with the 8x8 structuring
   *  elements in the "sliding windows" block element. If one color is present at least once
   *  on the local histogram of the sliding windows, fill the CSD histogram with this
   *  color.</p>
   *
   *
   * @param imQ 	a byte matrix representing the quantifized values between [0,qLevels] (heigh x width)
   * @param wImg 	width of the image
   * @param hImg 	height of the image
   * @return		a {@link #qLevels} histograms
   */
  private float[] structuredHisto(byte[][] imQ, int wImg, int hImg) {
    int m = 0;
    double hw = Math.sqrt(hImg * wImg);
    double p = Math.floor(Math.log(hw) / Math.log(2) - 7.5); //Formula given by Manjunath2002
    if (p < 0) {
      p = 0; //Minimum size of the division factor to have K=1
    }
    double K = Math.pow(2, p); //Determine the space between each structuring element
    double E = 8 * K; //Determine the size of the moving windows
    // Setting the local temporary and the CDS histograms
    float histo[] = new float[qLevels]; // CSD histograms
    int winHisto[] = new int[qLevels]; // local histo for a specific windows
    for (int i = 0; i < qLevels; i++) {
      histo[i] = 0.0f;
      /**
       *  Histogram using the structuring windows and the 8x8 elements
       *  if K=1 we have a  convolution with a windows of 8x8
       *  */
      //Moving the structuring windows shifting it of one structuring element
    }
    for (int y = 0; y < hImg - E; y += K) {
      for (int x = 0; x < wImg - E; x += K) {

        // re initialize the local windows histogram t[m]
        for (m = 0; m < qLevels; m++) {
          winHisto[m] = 0;
          //Iterate over the 8x8 structuring element in the moving windows.
        }
        for (int yy = y; yy < y + E; yy += K) {
          for (int xx = x; xx < x + E; xx += K) {
            //Obtain the pixel values of the HMMD quantized image
            m = (int) (imQ[yy][xx] & 0x000000FF); //WARNING imQ is signed byte
            //pixel value correspond to the bin value in qLevels CSD Histo
            winHisto[m]++;
          }
        } //End of local histogram for a local windows
        // Increment the color structure histogram for each color present in the structuring element
        for (m = 0; m < qLevels; m++) {
          if (winHisto[m] > 0) {
            histo[m]++;
          }
        }
      }
    }
    //Normalize the histograms by the number of times the windows was shift
    int winShift_X = ( (wImg - 1) - (int) E + (int) K);
    int winShift_Y = ( (hImg - 1) - (int) E + (int) K);
    int S = (winShift_X / (int) K) * (winShift_Y / (int) K);
    for (m = 0; m < qLevels; m++) {
      histo[m] = histo[m] / S;
    }
    return histo;
  }

  /**
   * Build the Subspace start position in the qLevels quantification.
   *
   * @return a array with the 5 start position depending on the qLevels (offset)
   */
  private int[] getStartSubspacePos() {
    return getStartSubspacePos(this.offset);
  }

  private static int[] getStartSubspacePos(int offset) {
    int[] startP = new int[5];
    startP[0] = 0;
    for (int i = 1; i < startP.length; i++) {
      startP[i] = startP[i - 1]; //Set the position of the previous subspace start
      startP[i] += quantTable[offset][i - 1][0] * quantTable[offset][i - 1][1]; //Add the length of the previous subspace
    }
    return startP;
  }

  /**
   * reQuantiuation find in Caliph & Emir.
   * <p>Description can be find in paragraph Bin Value quantification from Manjunath2002</p>
   * @param	colorHistogramTemp	a {@link #qLevels} non uniform CSD histograms containing values between [0-1]
   * @return						a {@link #qLevels} uniform histograms.
   *
   */
  //TODO: Change quantification tab.
  private int[] reQuantization(float[] colorHistogramTemp) {
    int[] uniformCSD = new int[colorHistogramTemp.length];
    for (int i = 0; i < colorHistogramTemp.length; i++) {
      uniformCSD[i] = quantFunc( (double) colorHistogramTemp[i]);
    }
    return uniformCSD;
  }

  static public int quantFunc(double x) {
    double[] stepIn = {0.000000001, 0.037, 0.08, 0.195, 0.32, 1};
    //int[] 	stepOut = {-1,0,25,45,80,115};
    int[] stepOut = {-1, 0, 25, 45, 80, 115};
    int y = 0;
    if (x <= 0) {
      y = 0;
    }
    else if (x >= 1) {
      y = 255;
    }
    else {
      y = (int) Math.round( ( (x - 0.32) / (1 - 0.32)) * 140.0);
      for (int i = 0; i < stepIn.length; i++) {
        if (x < stepIn[i]) {
          y += stepOut[i];
          break;
        }
      }
      //Since there is a bug in Caliph & emir version the data are between -66 and 255
      y = (int) (255.0 * ( (double) y + 66) / (255.0 + 66.0));
    }
    return y;
  }

  private static int[] resizeCSD(MPEG7ColorStructure c, int qSizeDst) {
    int qSizeSrc = c.getQuantLevels();
    int[] dstHisto = new int[qSizeDst];
    int[] srcHisto = c.histo;
    if (qSizeSrc > qSizeDst) {
      int offsetSrc = (int) log2(qSizeSrc);
      int offsetDst = (int) log2(qSizeDst) - 5;
      int[] subStartPosSrc = getStartSubspacePos(offsetSrc);
      int[] subStartPosDst = getStartSubspacePos(offsetDst);
      int tmp = 0, sVal;
      double sumBinSrc, hueBinSrc, hueBinDst, sumBinDst;
      //We resize this descriptors
      for (int i = 0; i < qSizeSrc; i++) {
        tmp = 0;
        //Obtain the subspace Value or DiffBin looking at starting position
        for (sVal = 1; sVal < 5; sVal++) {
          if (i < subStartPosSrc[sVal]) {
            break;
          }
        }
        sVal--;
        //Obtain the sum value
        tmp = i - subStartPosSrc[sVal];
        /* If tmp = 12 and there is:
         * 8 bins for hue (qTab[sVal][0]) and 2 bins for sum (qTab[sVal][1])
         * sumBin=floor(12/8)=floor(1.5)=1
         * hueBin=12%8=12-8*sumBin=4
         */
        hueBinSrc = tmp % quantTable[offsetSrc][sVal][0];
        sumBinSrc = Math.floor(tmp / quantTable[offsetSrc][sVal][0]);
        //Compute their analog value in destination histograms
        hueBinDst = quantTable[offsetDst][sVal][0] * (hueBinSrc / quantTable[offsetSrc][sVal][0]);
        sumBinDst = quantTable[offsetDst][sVal][1] * (sumBinSrc / quantTable[offsetSrc][sVal][1]);
        //Then compute find the exact position in the destination histogram and increment
        tmp = subStartPosDst[sVal] +
            quantTable[offsetDst][sVal][0] * (int) sumBinDst + (int) hueBinDst;
        dstHisto[tmp] += srcHisto[i];
      }
    }
    return dstHisto;
  }

  private static double log2(int x) {
    return Math.log(x) / Math.log(2);
  }

  protected boolean checkImage(JMRExtendedBufferedImage im) {
    boolean ok = checkColorSpace(im.getColorModel().getColorSpace());
    //if(!ok) System.err.println("This image is not encoded in the correct color space");
    ok = ok &&
        (im.getType() == JMRExtendedBufferedImage.TYPE_JMR_4F_INTERLEAVED);
    //if(!ok) System.err.println("This image does not use the correct sample model");
    return ok;
  }

  public int getQuantLevels() {
    return qLevels;
  }

  private int getSubspace(float diff) {
    if (diff < 7f / 255f) {
      return 0;
    }
    else if (diff < 21f / 255f) {
      return 1;
    }
    else if (diff < 61f / 255f) {
      return 2;
    }
    else if (diff < 111f / 255f) {
      return 3;
    }
    else {
      return 4;
    }
  }

  public boolean isComputed() {
    return (histo != null);
  }

  /*
   * @see es.ugr.siar.ip.desc.VisualDescriptor#multiply(float)
   */
  public void multiply(float factor) {
    for (int i = 0; i < histo.length; i++) {
      histo[i] *= factor;
    }
  }

  /*
   * @see es.ugr.siar.ip.desc.VisualDescriptor#sum(es.ugr.siar.ip.desc.VisualDescriptor)
   */
  public void sum(MPEG7ColorStructure desc) {
    for (int i = 0; i < histo.length; i++) {
      this.histo[i] += desc.histo[i];
    }
  }

  public byte[] getByteHisto() {
    byte[] bHisto = null;
    if (isComputed()) {
      bHisto = new byte[histo.length];
      for (int i = 0; i < histo.length; i++) {
        bHisto[i] = (byte) histo[i]; //No special operation because value are between [0-7]
      }
    }
    return bHisto;
  }

  public void setHisto(byte[] bHisto) {
    if (bHisto != null) {
      if (!isComputed()) {
        histo = new int[bHisto.length];
      }
      for (int i = 0; i < histo.length; i++) {
        histo[i] = (int) (bHisto[i] & 0xFF);
      }
    }
  }


}
