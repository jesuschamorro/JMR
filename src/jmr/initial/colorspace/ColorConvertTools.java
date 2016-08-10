/**
 *
 */
package jmr.initial.colorspace;

import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import jmr.initial.media.JMRExtendedBufferedImage;


/**
 * This class implement the method for color convertion using {@link ImageJMR} and
 * {@link ColorSpaceJMR} classes.
 *
 *
 *
 *
 * @author  RAT Benoit <br/>
 * (<a href="http://ivrg.epfl.ch" target="about_blank">IVRG-LCAV-EPFL</a> &
 *  <a href="http://decsai.ugr.es/vip" target="about_blank">VIP-DECSAI-UGR</a>)
 * @version 1.0
 * @since 5 dec. 07
 *
 */
public class ColorConvertTools {



	/** This tools is designed to replace ColorConvertOp that process too many operation
	 * when the ColorSpace is not a {@link ICC_ColorSpace}.
	 *
	 * <p>
	 * The ColorConvertOp contains a strange behaviour near:
	 * <code>
	 *	color = srcCM.getNormalizedComponents(spixel, color, 0); //From spixel obtain RGB in [0,1]
	 *	tmpColor = srcColorSpace.toCIEXYZ(color); //Transform from RGB to CIEXYZ
	 *	--> tmpColor = dstColorSpace.fromCIEXYZ(tmpColor); //Transform CIEXYZ to CS (strange why loop)
	 * 	--> tmpColor = dstColorSpace.toCIEXYZ(tmpColor); //Transform CS to CIEXYZ
	 * 	tmpColor = dstColorSpace.fromCIEXYZ(tmpColor); //Transform CIEXYZ to CS
	 * </code></p>
	 *
	 * @param 	src 	The source image with type {@link BufferedImage#TYPE_INT_ARGB} or {@link BufferedImage#TYPE_INT_RGB}
	 * @param 	dstCs 	A color Space instance of {@link ColorSpaceJMR}.
	 * @return			An ImageJMR object with a {@link ImageJMR#specialType}
	 */
	public static JMRExtendedBufferedImage colorConvertOp(BufferedImage src, ColorSpace dstCs) {


		//Create destination following the colorSpace.
		JMRExtendedBufferedImage dst = null;
		if(dstCs.getType() == ColorSpaceJMR.CS_HMMD) {
			dst = JMRExtendedBufferedImage.getInstance(src.getWidth(),src.getHeight(),
					JMRExtendedBufferedImage.TYPE_JMR_4F_INTERLEAVED, dstCs);
		}
		else {
			dst = JMRExtendedBufferedImage.getInstance(src.getWidth(),src.getHeight(),
					JMRExtendedBufferedImage.TYPE_JMR_3F_INTERLEAVED, dstCs);
		}

		//Check that source is not Gray nor Custom
		if(src.getType() != BufferedImage.TYPE_CUSTOM) {

			WritableRaster dstMx = dst.getRaster();
			//ColorSpace dstCs = dst.getColorModel().getColorSpace();
			int RGB;
			float[] p_in = new float[3];
			float[] p_out = new float[dstMx.getNumBands()];
			for(int x=0;x<src.getWidth();x++) {
				for(int y=0;y<src.getHeight();y++) {
					RGB = src.getRGB(x,y);
					p_in[0] = (float)((RGB >> 16) & 0xFF)/255.0f;
					p_in[1] = (float)((RGB >> 8) & 0xFF)/255.0f;
					p_in[2] = (float)(RGB & 0xFF)/255.0f;
					p_out = dstCs.fromRGB(p_in);
					dstMx.setPixel(x,y,p_out);
				}
			}
		}

		return dst;
	}


	/**
	 * convertColor using a BufferedImage as source and the ColorSpace Type that
	 * can be find at <a href="colspace/ColorSpaceJMR.html#field_summary">Field Summary</a>
	 *
	 * <p>
	 * If the ColorSpaceType return an instance of {@link ColorSpaceJMR} it will call a special convert
	 * method otherwise we use the classic {@link ColorConvertOp}.
	 * </p>
	 *
	 * @param src
	 * @param colorSpaceType
	 * @return BufferedImage
	 */
	public static JMRExtendedBufferedImage convertColor(BufferedImage src, int colorSpaceType) {
		ColorConvertOp op =  null;
		ColorSpace cS = null;
		JMRExtendedBufferedImage dst = null;

		switch(colorSpaceType) {
		case ColorSpaceJMR.CS_CIEXYZ:
		case ColorSpaceJMR.CS_GRAY:
		case ColorSpaceJMR.CS_LINEAR_RGB:
		case ColorSpaceJMR.CS_PYCC:
		case ColorSpaceJMR.CS_sRGB:
			op = new ColorConvertOp(ColorSpace.getInstance(colorSpaceType),null);
			return new JMRExtendedBufferedImage(op.filter(src,null));
//		case ColorSpaceJMR.CS_HSI:
//			 cS = ColorSpaceHSI.getInstance();
//			 //dst = op.createCompatibleDestImage(src,new ComponentColorModel(cS, false,false,Transparency.BITMASK,DataBuffer.TYPE_BYTE));
//			 dst = ImageJMR.getInstance(src.getWidth(),src.getHeight(),ImageJMR.TYPE_JMR_3F_INTERLEAVED, cS);
//			 op = new ColorConvertOp(cS,null);
//			 dst = (ImageJMR)op.filter(src,dst);
//			 break;
		case ColorSpaceJMR.CS_HSI:
		case ColorSpaceJMR.CS_YCbCr:
		case ColorSpaceJMR.CS_HSV:
		case ColorSpaceJMR.CS_HMMD:
		case ColorSpaceJMR.CS_Lab:
		case ColorSpaceJMR.CS_Luv:
			cS = ColorSpaceJMR.getInstance(colorSpaceType);
			dst = colorConvertOp(src,cS);
			break;
		default:
			System.err.println("No transformation find for this color space");
			return null;
		}
		System.out.println(dst.toString());
		return dst;
	}



	/**
	 * Convert an {@link BufferedImage} with <code>N</code> components in
	 * <code>N</code> {@link BufferedImage#TYPE_BYTE_GRAY} images.
	 *
	 * <p>
	 * This method has been replaced by {@link ImageJMR#getLayeredByteImages()}.
	 * </p>
	 *
	 * @param src
	 * @return <code>N</code> {@link BufferedImage#TYPE_BYTE_GRAY} images.
	 * @see ImageJMR#getLayeredByteImages().
	 * @deprecated not used in JMR SIAR
	 */
	public static BufferedImage[] convert2SlideImage(BufferedImage src) {

		int numBands = src.getSampleModel().getNumBands();
		BufferedImage[] dst = new BufferedImage[numBands];
		float[] pix = new float[numBands];
		float[] centerVal = new float[numBands];
		float[] normVal = new float[numBands];
		ColorSpace srcCs = src.getColorModel().getColorSpace();

		for(int i=0; i< numBands; i++) {
			centerVal[i] = srcCs.getMinValue(i);
			normVal[i] = (srcCs.getMaxValue(i) - centerVal[i])/255.0f;
			dst[i] = new BufferedImage(src.getWidth(),src.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
		}

		Raster srcRsr = src.getRaster();
		int pixVal;

		for(int x=0;x<src.getWidth();x++) {
			for(int y=0;y<src.getHeight();y++) {
				srcRsr.getPixel(x,y, pix); //Work even if image is in int or double
				for(int i=0;i<numBands;i++) {
					pixVal = Math.round((pix[i]-centerVal[i])/normVal[i]);
					dst[i].setRGB(x,y,pixVal);
				}
			}
		}
		return dst;
	}


	/**
	 * Same kind of function as {@link #convert2SlideImage(BufferedImage)} excepting that is
	 * not returning <code>N</code> {@link BufferedImage} with {@link BufferedImage#TYPE_BYTE_GRAY}
	 * but an array of byte.
	 *
	 * <p style="color:red">WARNING : byte are signed so values E [-127,128]</p>
	 *
	 * @param im
	 * @return <code>N</code> byte vectors of size im.getWidth()*im.getHeight().
	 * @deprecated  not really implemented.
	 */
	public static byte[][] layeredImArray(BufferedImage im) {
		int width = im.getWidth();
		int height = im.getHeight();
		byte[][] pixelarray = new byte[3][width * height];

		return pixelarray;
	}


	/**
	 * Convert a {@link BufferedImage} with <code>N</code> components or band in a
	 * <code>int[]</code> array with pixels interleaved.
	 *
	 * <p>
	 *	eg: <code>pixel(1,0) -> {20,255,200}</code> is converted into
	 * <code>
	 * pixArray[3]=20;
	 * pixArray[4]=255;
	 * pixArray[5]=200;
	 * </code>
	 *
	 * @param im
	 * @return an array of size: numBands * width * height
	 * @deprecated not used in JMR SIAR
	 */
	public static int[] interleavedImArray(BufferedImage im) {
		int width = im.getWidth();
		int height = im.getHeight();
		int[] pixelarray = new int[im.getSampleModel().getNumBands() * width * height];
		int j = 0;
		WritableRaster raster = im.getRaster();
		int[] pixel = new int[3];
		for (int i = 0; i < width; i++) { //row
			for (int ii = 0; ii < height; ii++) {//column
				raster.getPixel(i, ii, pixel);
				pixelarray[3 * j] = pixel[0];
				pixelarray[3 * j + 1] = pixel[1];
				pixelarray[3 * j + 2] = pixel[2];
				j++;
			}
		}
		return pixelarray;
	}
}
