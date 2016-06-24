package jmr.result;

/**
 * <p>Title: JMR Project</p>
 * <p>Description: Java Multimedia Retrieval API</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: University of Granada</p>
 * @author Jesus Chamorro Martinez
 * @version 1.0
 */

public class FloatResult implements JMRResult {
  float result;

  /**
   * Constructs a <code>FloatResults</code> from a float number.
   * @param data  The float number corresponding to the result
   */
  public FloatResult(float data) {
    result = data;
  }

  /** Return the result type, in this case TYPE_FLOAT
   * @return The result type
   */
  public int getType() {
    return (this.TYPE_FLOAT);
  }

  /**
   * Return a double number representing the result
   * @return A double number representing the result
   */
  public double toDouble() {
    return ( (double) result);
  }

  /**
   * Return a float number representing the result
   * @return A float number representing the result
   */
  public float toFloat() {
    return (result);
  }

  /**
   * Return a integer number representing the result
   * @return A integer number representing the result
   */
  public int toInteger() {
    return ( (int) result);
  }

  /**
   * Return the float number representing the result
   * @return The float number representing the result
   */
  public float getValue() {
    return (result);
  }

  /**
  * Sets the float number representing the result
  */
 public void setValue(float value) {
   result = value;
 }


}
