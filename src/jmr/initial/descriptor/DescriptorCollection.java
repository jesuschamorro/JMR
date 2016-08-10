package jmr.initial.descriptor;

import java.util.Collection;
import jmr.result.JMRResult;
import jmr.initial.media.Media;

/**
 * <p>Title: JMR Project</p>
 * <p>Description: Java Multimedia Retrieval API</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: University of Granada</p>
 * @author Jesus Chamorro Martinez
 * @version 1.0
 */

public interface DescriptorCollection extends Collection {

  /**
   * Represents an undefined descriptor collection type
   */
  public static final int TYPE_UNDEFINED = -1;

  /**
   * Represents a custom type descriptor collection
   */
  public static final int TYPE_CUSTOM = 0;

  /**
   * Represents a vector type as descriptor collection
   */
  public static final int TYPE_VECTOR = 1;

  /**
   * Represents a set type as descriptor collection
   */
  public static final int TYPE_SET = 2;

  /**
   * Represents a tree type as descriptor collection
   */
  public static final int TYPE_TREE = 3;

  /**
   * Represents a graph type as descriptor collection
   */
  public static final int TYPE_GRAPH = 4;


  /** Return the collection type
   * @see #TYPE_UNDEFINED
   * @see #TYPE_CUSTOM
   * @see #TYPE_VECTOR
   * @return The collection type
   */
  public int getCollectionType();

  /** Return a list with the types and arrangement of the descriptor inside the collection
   * <p> For example, suppose we have as collection a vector of descriptors.
   * Independently of the type of that descriptors, the collection type returned by the
   * <code>getCollectionType</code> will be TYPE_VECTOR. Nevertheless, a lot of decriptor
   * combinations are possible in a vector: for example, a <code>ColorData</code> as the first
   * element and a <code>TextureData</code> as the second one. This methods will return a list
   * with these types and order
   * @return A list of descriptor types
   */
  public DescriptorTypeList getDescriptorTypes();

  /* * Compares two descriptor collections.
   * <p> The compared collections must share the same structure
   * @param dc The <code>DescriptorCollection</code> object to be compared
   * @return A <code>JMRResult</code> object with the result
   */
  public JMRResult compare(DescriptorCollection dc);

  /** Returns a <code>DescriptorCollection</code> calculated over a <code>Media</code>.
   * The number and type of descriptor to be calculated will be defined in the
   * <code>DescriptorTypeList</code> object
   * @param media The <code>Media</code> from which the <code>DescriptorCollection</code> is obtained
   * @param descriptorList The <code>DescriptorTypeList</code> object with information about
   * the number and type of descriptor to be calculated
   * @see #Media
   * @see #DescriptorTypeList
   * @return The <code>DescriptorCollection</code> obtained
   */
   public DescriptorCollection getInstance(Media media, DescriptorTypeList descriptorList);

}
