package jmr.descriptor;

import java.util.Vector;
import jmr.result.JMRResult;
import jmr.media.Media;
import jmr.descriptor.MediaDescriptor;
import java.util.Iterator;
import jmr.result.FloatResult;

/**
 * <p>Title: JMR Project</p>
 * <p>Description: Java Multimedia Retrieval API</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: University of Granada</p>
 * @author Jesus Chamorro Martinez
 * @version 1.0
 */

public class DescriptorVector extends Vector implements DescriptorCollection {

  public DescriptorVector(Media media, DescriptorTypeList descriptorList) {
  }

  /** Return the collection type, in this case TYPE_VECTOR
   * @see #TYPE_VECTOR
   * @return The collection type
   */
  public int getCollectionType() {
    return (this.TYPE_VECTOR);
  }

  /** Compares two descriptor vectors.
   * <p> The <code>DescriptorCollection</code> parameter object must be a <code>DescriptorVector</code>
   * @param dc The <code>DescriptorCollection</code> object to be compared
   * @return A <code>JMRResult</code> object with the result
   */
  public JMRResult compare(DescriptorCollection dc) {
    if(dc.getCollectionType() != this.getCollectionType())
      return (null);
    return( compare((DescriptorVector)dc) );
  }

  /** Compares two descriptor vectors as the Euclidean distance between the vectors
   * @param dc The <code>DescriptorVector</code> object to be compared
   * @return A <code>FloatResult</code> object with the result
   */
  public FloatResult compare(DescriptorVector dv) {

    // The number of descriptor and its types and arrangement must be the same
    if(this.getDescriptorTypes().equals(dv.getDescriptorTypes())==false)
      return (null);

    // The Euclidean distance between vectors is calculated. For each element, the
    // 'compare' method is invoked and the results is transformed to a float number
    // by means of the method 'toFloat' in the class JMRResult
    Iterator it1 = this.iterator();
    Iterator it2 = dv.iterator();
    MediaDescriptor md1,md2;
    JMRResult elmentDif;
    float rtmp,rsum=0.0F;
    while(it1.hasNext()){
      md1 = (MediaDescriptor)it1.next();
      md2 = (MediaDescriptor)it2.next();
      elmentDif = md1.compare(md2);  // The media are compared
      rtmp = elmentDif.toFloat();    // The results is transformed to float
      rsum += rtmp*rtmp;
    }
    rsum = (float)Math.sqrt((double)rsum);
    return (new FloatResult(rsum));
  }


  /** Returns a <code>DescriptorVector</code> calculated over a <code>Media</code>.
   * The number and type of descriptor to be calculated will be defined in the
   * <code>DescriptorTypeParameters</code> object
   * @param media The <code>Media</code> from which the <code>DescriptorVector</code> is obtained
   * @param parameters The <code>DescriptorTypeParameters</code> object with information about
   * the number and type of descriptor to be calculated
   * @see #Media
   * @see #DescriptorTypeParameters
   * @return The <code>DescriptorVector</code> obtained
   */
  public DescriptorCollection getInstance(Media media, DescriptorTypeList descriptorList) {
    //TODO
    return (null);
  }

  /** Return a list with the types and arrangement of the descriptor inside the collection
   * @return A list of descriptor types
   */
  public DescriptorTypeList getDescriptorTypes(){
    //TODO
    return(null);
  }

}
