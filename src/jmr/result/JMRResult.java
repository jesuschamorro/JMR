package jmr.result;

/**
 * <p>Title: JMR Project</p>
 * <p>Description: Java Multimedia Retrieval API</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: University of Granada</p>
 * @author Jesus Chamorro Martinez
 * @version 1.0
 */

public interface JMRResult {

  /**
   * Represents an undefined result type
   */
  public static final int TYPE_UNDEFINED = -1;

  /**
   * Represents a custom result type
   */
  public static final int TYPE_CUSTOM = 0;

  /**
   * Represents float result
   */
  public static final int TYPE_FLOAT = 1;

  /**
   * Represents a double result
   */
  public static final int TYPE_DOUBLE = 2;

  /**
   * Represents a integer result
   */
  public static final int TYPE_INTEGER = 3;


  /** Return the result type
     * @see #TYPE_UNDEFINED
     * @see #TYPE_CUSTOM
     * @see #TYPE_FLOAT
     * @see #TYPE_DOUBLE
     * @see #TYPE_INTEGER
     * @return The result type
     */
  public int getType();

  /**
   * Return a double number representing the result
   * <p> This representation will depend on the characteristics of the <code>JMRResult</code> objects.
   * In fact, for some structured <code>JMRResult</code> objects, summarizing the information into a number
   * will have no sense.
   * @return A double number representing the result
   */
  public double toDouble();

  /**
  * Return a float number representing the result
  * <p> This representation will depend on the characteristics of the <code>JMRResult</code> objects.
  * In fact, for some structured <code>JMRResult</code> objects, summarizing the information into a number
  * will have no sense.
  * @return A float number representing the result
  */
  public float toFloat();

  /**
    * Return a integer number representing the result
    * <p> This representation will depend on the characteristics of the <code>JMRResult</code> objects.
    * In fact, for some structured <code>JMRResult</code> objects, summarizing the information into a number
    * will have no sense.
    * @return A integer number representing the result
    */
  public int toInteger();
}
