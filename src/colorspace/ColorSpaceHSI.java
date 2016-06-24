/**
 *
 */
package jmr.colorspace;


/**
 * The HSI (<i>H</i>ue,<i>S</i>aturation,<i>I</i>ntensity) color space (also known as IHS or HIS).
 *
 * No more details here but you can find more information in the
 * <a href="http://java.sun.com/products/java-media/jai/forDevelopers/jai-apidocs/javax/media/jai/IHSColorSpace.html">
 * JAI API of IHSColorSpace</a>
 *
 * <img src="http://www.couleur.org/spaces/HSIPolspace.jpg"/>
 *
 * @author  RAT Benoit <br/>
 * (<a href="http://ivrg.epfl.ch" target="about_blank">IVRG-LCAV-EPFL</a> &
 *  <a href="http://decsai.ugr.es/vip" target="about_blank">VIP-DECSAI-UGR</a>)
 * @version 1.0,
 * @since 29 nov. 07
 *
 */
public class ColorSpaceHSI extends ColorSpaceJMR {

	private static final long serialVersionUID = 1L;

	 /**
     * Constructs an instance of this class with <code>type</code>
     * <code>ColorSpace.TYPE_HSV</code>, 3 components, and preferred
     * intermediary space sRGB.
     */

	protected ColorSpaceHSI() {
		super(ColorSpaceJMR.CS_HSI,3);
	}


	/** transform a RGB pixel in a HSI pixel.
	 *
	 * More information about this transformation can be find at
	 * <a href="http://java.sun.com/products/java-media/jai/forDevelopers/jai-apidocs/javax/media/jai/IHSColorSpace.html">
	 * javax.media.jai.IHSColorSpace
	 * </a>
	 * @param 	rgbVec	a float vector (length=3) with rgb values normalized R,G,B=[0,1]
	 * @return 			a float vector (length=3) with hsv values normalized H=[0,2*PI] and S,I=[0,1]
	 */
	public float[] fromRGB(float[] rgbVec) {

//		my.Debug.printCount("fromRGB (RGB > HSI):");
//		my.Debug.printCount(" - RGB input :  rgb=["+rgbVec[0]+","+rgbVec[1]+","+rgbVec[2]+"];");

		float[] hsiVec = new float[3] ;
		float h,s,i=0.0f;
		float r = rgbVec[0] ;
		float g = rgbVec[1] ;
		float b = rgbVec[2] ;

		//val = (condition)? val_true : val_false
		r = (r < 0.0f)? 0.0f : ((r > 1.0f) ? 1.0f: r) ;
		g = (g < 0.0f)? 0.0f : ((g > 1.0f) ? 1.0f: g) ;
		b = (b < 0.0f)? 0.0f : ((b > 1.0f) ? 1.0f: b) ;

		i = (r + g + b)/3.0f ;
		float drg = r - g ;
		float drb = r - b ;
		float temp = (float) Math.sqrt(drg * (double)drg + drb * (double)(drb - drg)) ;

		// when temp is zero, R=G=B. Hue should be NaN. To make
		// numerically consistent, set it to 2PI
		if (temp != 0.0f) {
			temp = (float) Math.acos((drg + drb) / (double)temp / 2) ;
			if (g < b)
				h = (float) (PI2 - temp) ;
			else h = temp ;
		} else h = (float)PI2 ;

		float min = (r < g) ? r : g ;
		min = (min < b) ? min : b ;

		// when intensity is 0, means R=G=B=0. S can be set to 0 to indicate
		// R=G=B.
		if (i == 0.0f)
			s = 0.0f ;
		else
			s = 1.0f - min / i ;

		hsiVec[0] = h;
		hsiVec[1] = s;
		hsiVec[2] = i;

//		my.Debug.printCount(" - HSI output: 	hsi=["+hsiVec[0]+","+hsiVec[1]+","+hsiVec[2]+"];");

		return hsiVec ;
	}


	/**
	 * Transform a HSI pixel in a RGB pixel.
	 *
	 * More information about this transformation can be find at
	 * <a href="http://java.sun.com/products/java-media/jai/forDevelopers/jai-apidocs/javax/media/jai/IHSColorSpace.html">
	 * javax.media.jai.IHSColorSpace
	 * </a>
	 *
	 * @param	hsiVec	a float vector (length=3) with hsv values normalized H=[0,2*PI] and S,I=[0,1]
	 * @return  		a float vector (length=3) with rgb values normalized R,G,B=[0,1]
	 */
	public float[] toRGB(float[] hsiVec) {
//		my.Debug.printCount("toRGB (HSI -> RGB)");
//		my.Debug.printCount(" - HSI input: 	hsi=["+hsiVec[0]+","+hsiVec[1]+","+hsiVec[2]+"];");

		float h = hsiVec[0] ;
		float s = hsiVec[1] ;
		float i = hsiVec[2] ;

		i = (i < 0.0f) ? 0.0f : ((i > 1.0f) ? 1.0f : i) ;
		h = (h < 0.0f) ? 0.0f : ((h > (float)PI2) ? (float)PI2 : h) ;
		s = (s < 0.0f) ? 0.0f : ((s > 1.0f) ? 1.0f : s) ;

		float[] rgb = new float[3] ;

		// when the saturation is 0, the color is grey. so R=G=B=I.
		if (s == 0.0f) {
			rgb[0] = rgb[1] = rgb[2] = i ;
		}
		else {
			if (h >= PI23 && h < PI43) {
				float r = (1 - s) * i ;
				float c1 = 3 * i - r ;
				float c2 = (float) (SQRT3 * (r - i) * Math.tan(h)) ;
				rgb[0] = r ;
				rgb[1] = (c1 + c2) / 2 ;
				rgb[2] = (c1 - c2) / 2 ;
			}
			else if (h >PI43) {
				float g = (1 - s) * i ;
				float c1 = 3 * i - g ;
				float c2 = (float) (SQRT3 * (g - i) * Math.tan(h - PI23)) ;
				rgb[0] = (c1 - c2) / 2 ;
				rgb[1] = g ;
				rgb[2] = (c1 + c2) / 2 ;
			}
			else if (h < PI23) {
				float b = (1 - s) * i ;
				float c1 = 3 * i - b ;
				float c2 = (float) (SQRT3 * (b - i) * Math.tan(h - PI43)) ;
				rgb[0] = (c1 + c2) / 2 ;
				rgb[1] = (c1 - c2) / 2 ;
				rgb[2] = b ;
			}
		}
//		my.Debug.printCount(" - RGB : 	rgb=["+rgb[0]+","+rgb[1]+","+rgb[2]+"];");
		return rgb ;

	}


	public float getMaxValue(int cmp) {
		switch(cmp) {
		case 0:
			return (float)PI2;
		case 1:
			return 1.0f;
		case 2:
			return 1.0f;
		default:
			return super.getMaxValue(cmp);
		}
	}

	public float getMinValue(int cmp) {
		switch(cmp) {
		default:
			return super.getMinValue(cmp);
		}
	}

	public String getName(int cmp) {
		switch(cmp) {
		case 0:
			return "H";
		case 1:
			return "S";
		case 2:
			return "I";
		default:
			return super.getName(cmp);
		}
	}

}
