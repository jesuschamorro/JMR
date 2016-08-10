package jmr.initial.descriptor.mpeg7;

import java.awt.color.ColorSpace;
import jmr.initial.media.JMRExtendedBufferedImage;
import jmr.initial.colorspace.ColorSpaceJMR;
import jmr.initial.colorspace.ColorConvertTools;
import jmr.initial.descriptor.ColorDescriptor;

/**
 * <p>Title: JMR Project</p>
 * <p>Description: Java Multimedia Retrieval API</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: University of Granada</p>
 * @author Jesus Chamorro Martinez
 * @version 1.0
 */

public abstract class MPEG7ColorDescriptor extends ColorDescriptor {
  protected MPEG7ColorDescriptor(int type, int colorSpaceType) {
    super(type, colorSpaceType);
  }

  /**
   * Check if the {@link ColorSpace} of the input image is correct for the <code>subclass</code> descriptor.
   *
   * <p>In the case that more than one {@link ColorSpace} is acceptable for the
   * subclass descriptor, this method should be override in the <code>subclass</code>.
   * </p>
   *
   * @param	 cS 	The ColorSpace that is used as input image.
   * @return			True if we use the correct ColorSpace.
   */
  protected boolean checkColorSpace(ColorSpace cS) {
    return (cS.getType() == colorSpaceType);
  }

  /* (non-Javadoc)
   * @see es.ugr.siar.ip.desc.VisualDescriptor#convertImg(es.ugr.siar.ip.ImageJMR)
   */
  protected JMRExtendedBufferedImage convertImg(JMRExtendedBufferedImage imSrc) {
    return ColorConvertTools.colorConvertOp(imSrc,ColorSpaceJMR.getInstance(colorSpaceType));
  }

}
