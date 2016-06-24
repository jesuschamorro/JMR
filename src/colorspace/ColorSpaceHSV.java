/**
 *
 */
package jmr.colorspace;


/**
 * The HSV (<i>H</i>ue,<i>S</i>aturation,<i>V</i>alue) color space.
 *
 * <p>
 * The HSV is one of the most popular color space using sector division, also called Hue division.
 * Mainly adopted by the computer graphic community for color selection and representation,
 * this color space has been used in image retrieval (Carson et al. 1997, Depalov et al. 2006b)
 * searching images by semantic color meaning.
 * </p>
 *
 * <img src="http://www.couleur.org/spaces/HSVPolspace.jpg"/>
 *
 *
 * @author  RAT Benoit <br/>
 * (<a href="http://ivrg.epfl.ch" target="about_blank">IVRG-LCAV-EPFL</a> &
 *  <a href="http://decsai.ugr.es/vip" target="about_blank">VIP-DECSAI-UGR</a>)
 * @version 1.0
 * @since 29 nov. 07
 *
 */
public class ColorSpaceHSV extends ColorSpaceJMR {

	private static final long serialVersionUID = 8258172467685956032L;

	/**
     * Constructs an instance of this class with <code>type</code>
     * {@link ColorSpaceJMR#CS_YCbCr}, 3 components, and preferred
     * intermediary space sRGB.
     */
	protected ColorSpaceHSV() {
		super(ColorSpaceJMR.CS_HSV,3);
	}


	/** fromRGB : transform a RGB pixel in a HSV pixel.
	 *
	 * @param 	rgbVec	a vector (length=3) with rgb values normalized R,G,B=[0,1]
	 * @return 			a vector (length=3) with hsv values normalized H=[0,360] and S,V=[0,1]
	 * @see <i>Introduction to MPEG-7, Manjunath et al. 2001, Section 13.2.1</i>
	 */
	public float[] fromRGB(float[] rgbVec) {

//		my.Debug.printCount("fromRGB (RGB > HSV):");
//		my.Debug.printCount(" - RGB input :  rgb=["+rgbVec[0]+","+rgbVec[1]+","+rgbVec[2]+"];");

		float[] hsvVec = new float[3];
		float h=0f;
		float r = rgbVec[0] ;
		float g = rgbVec[1] ;
		float b = rgbVec[2] ;

		r = (r < 0.0f)? 0.0f : ((r > 1.0f) ? 1.0f: r) ;
		g = (g < 0.0f)? 0.0f : ((g > 1.0f) ? 1.0f: g) ;
		b = (b < 0.0f)? 0.0f : ((b > 1.0f) ? 1.0f: b) ;

		float max = Math.max(Math.max(r,g), Math.max(g,b)); //max(R,G,B);
		float min = Math.min(Math.min(r,g), Math.min(g,b)); //min(R,G,B)
		float diff = (max - min);

		// set value
		hsvVec[2] = max;

		// set saturation
		if(max == 0.0f) hsvVec[1]=0.0f;
		else hsvVec[1]=diff/max;

		// set hue
		if(max == min) h=0f;
		else {
			if ( r == max ) {
				if(g >= b) 	h = ((g - b)/diff)*60.f;
				else h = 360.f + ((g - b)/diff)*60.f;
			}
			else if ( g == max )
				h = ( 2.0f +  (b - r)/diff)*60.f;
			else if ( b == max )
				h = ( 4.0f +  (r - g)/diff)*60.f;
		}
		hsvVec[0] = h;


//		my.Debug.printCount(" - HSV output: 	hsv=["+hsvVec[0]+","+hsvVec[1]+","+hsvVec[2]+"];");

		return hsvVec ;
	}


	/**
	 * Transform a HSV pixel in a RGB pixel.
	 *
	 * @param	hsvVec	a vector (length=3) with hsv values normalized H=[0,360] and S,V=[0,1]
	 * @return			a vector (length=3) with rgb values normalized R,G,B=[0,1]
	 * @see <a href="http://alvyray.com/Papers/hsv2rgb.htm">HSV2RGB</a>
	 */
	public float[] toRGB(float[] hsvVec) {
//		my.Debug.printCount("toRGB (HSV -> RGB)");
//		my.Debug.printCount(" - HSV input: 	hsi=["+hsvVec[0]+","+hsvVec[1]+","+hsvVec[2]+"];");

		float[] rgbVec = {0f,0f,0f};
		float h = hsvVec[0] ;
		float s = hsvVec[1] ;
		float v = hsvVec[2] ; // H is given on [0, 6] or UNDEFINED. S and V are given on [0, 1].
		// RGB are each returned on [0, 1].

		h = (h < 0.0f) ? 0.0f : ((h > (float)getMaxValue(0)) ? (float)getMaxValue(0) : h) ;
		s = (s < 0.0f) ? 0.0f : ((s > 1.0f) ? 1.0f : s) ;
		v = (v < 0.0f) ? 0.0f : ((v > 1.0f) ? 1.0f : v) ;

		h=h/60;
		float m, n, f;
		int i;

		i = (int)Math.floor((double)h);
		f = h - i;
		if (i%2== 1) f = 1 - f; // if i is even
		m = v * (1 - s);
		n = v * (1 - s * f);
		switch (i) {
		case 6:
		case 0: toRGBArray(v, n, m,rgbVec);
		case 1: toRGBArray(n, v, m,rgbVec);
		case 2: toRGBArray(m, v, n,rgbVec);
		case 3: toRGBArray(m, n, v,rgbVec);
		case 4: toRGBArray(n, m, v,rgbVec);
		case 5: toRGBArray(v, m, n,rgbVec);
		}

//		my.Debug.printCount(" - RGB : 	rgb=["+rgbVec[0]+","+rgbVec[1]+","+rgbVec[2]+"];");
		return rgbVec ;

	}
	private void toRGBArray(float r,float g, float b,float[] rgb) {
		rgb[0] = r;
		rgb[1] = g;
		rgb[2] = b;
	}


	public float getMaxValue(int cmp) {
		switch(cmp) {
		case 0:
			return (float)360.0f;
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
			return "V";
		default:
			return super.getName(cmp);
		}
	}

}
