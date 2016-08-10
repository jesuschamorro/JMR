package jmr.descriptor;

import jmr.result.JMRResult;


/**
 *
 *
 * @author Jesús Chamorro Martínez (jesus@decsai.ugr.es)
 */
public abstract class MediaDescriptor {

  protected MediaDescriptor(){
    
  }

  /** 
   * Compares this <code>MediaDescriptor</code> obtect with
   * the <code>MediaDescriptor</code> given by parameter
   * @param mediaDescriptor descriptor to be compared
   * @return A <code>JMRResult</code> object with the result
   */
  public abstract JMRResult compare(MediaDescriptor mediaDescriptor);
}
