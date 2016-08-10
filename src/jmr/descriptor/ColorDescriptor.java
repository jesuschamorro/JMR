package jmr.descriptor;

import java.awt.color.ColorSpace;
import jmr.initial.media.JMRExtendedBufferedImage;
import jmr.initial.colorspace.ColorSpaceJMR;
import jmr.initial.colorspace.ColorConvertTools;

/**
 * 
 * @author Jesús Chamorro Martínez (jesus@decsai.ugr.es)
 */
public abstract class ColorDescriptor extends MediaDescriptor {
    
  protected int colorSpaceType;  
    
  protected ColorDescriptor(int colorSpaceType) {
    this.colorSpaceType = colorSpaceType;
  }

  /**
   * Check if the color space of the input image is correct for the subclass 
   * descriptor.
   *
   * In the case that more than one {@link ColorSpace} is acceptable for the
   * subclass descriptor, this method should be override in the <code>subclass</code>.
   * 
   * @param cS the color space that is used as input image.
   * @return true if we use the correct color space.
   */
  protected boolean checkColorSpace(ColorSpace cS) {
    return (cS.getType() == colorSpaceType);
  }

  protected JMRExtendedBufferedImage convertImg(JMRExtendedBufferedImage imSrc) {
    return ColorConvertTools.colorConvertOp(imSrc,ColorSpaceJMR.getInstance(colorSpaceType));
  }

}
