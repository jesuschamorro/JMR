package jmr.descriptor;

import java.util.LinkedList;
import java.util.Collection;

/**
 * <p>Title: JMR Project</p>
 * <p>Description: Java Multimedia Retrieval API</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: University of Granada</p>
 * @author Jesus Chamorro Martinez
 * @version 1.0
 */


/**
 * A <code>DescriptorTypeParameters</code> is a linked list of media descriptor types
 */

public class DescriptorTypeList extends LinkedList {

  /**
   * Constructs an empty list.
   */
  public DescriptorTypeList() {
    super();
  }

  /**
   * Constructs a list containing the elements of the specified array
   * @param array The array whose elements are to be placed into this list.
   */
  public DescriptorTypeList(int array[]) {
    for (int i = 0; i < array.length; i++) {
      this.add(new Integer(array[i]));
    }
  }

  /**
   * Appends the specified element to the end of this list.
   * <p> The integer is stored as an <code>Integer</code> object
   * @param type Element to be appended to this list.
   */
  public void add(int type) {
    this.add(new Integer(type));
  }

  /** Return the number of descriptors in the list
   * @return The number of descriptors in the list
   */
  int getNumDescriptors() {
    return (this.size());
  }
}
