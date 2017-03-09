package jmr.descriptor;

import java.lang.reflect.Constructor;

/**
 *
 * @author Jesús Chamorro Martínez (jesus@decsai.ugr.es)
 */
public class MediaDescriptorFactory {
    
    
   /**
    * 
    * @param <D>
    * @param <M>
    * @param descriptorClass
    * @param media
    * @return 
    */
   public <D extends MediaDescriptor, M> D getInstance(Class<D> descriptorClass, M media){
      D descriptor = null;
      try{
        Class parameters[] = {media.getClass()};
        Constructor constructor = descriptorClass.getConstructor(parameters);
        descriptor = (D)constructor.newInstance(media);
        descriptor.init(media);
      } catch (Exception ex) {
          
      }
      return descriptor;
  }
    
    
}
