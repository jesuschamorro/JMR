package jmr.descriptor;

/**
 * <p>Title: JMR Project</p>
 * <p>Description: Java Multimedia Retrieval API</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: University of Granada</p>
 * @author Jesus Chamorro Martinez
 * @version 1.0
 */

public abstract class ColorDescriptor  extends VisualLowLevelDescriptor{

  protected int colorSpaceType;


  protected ColorDescriptor(int type, int colorSpaceType) {
    super(type);
    this.colorSpaceType = colorSpaceType;
  }

}
