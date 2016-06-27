package jmr.media;

/**
 * <p>Title: JMR Project</p>
 * <p>Description: Java Multimedia Retrieval API</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: University of Granada</p>
 * @author Jesus Chamorro Martinez
 * @version 1.0
 */

public interface JMRVideo extends Media {

  /**
  * Represents a custom video type
  */
  public static final int TYPE_CUSTOM = 0;

  /**
  * Represents a video based on the JMF video classes
  */
  public static final int TYPE_JMF_VIDEO = 1;

  /** Return the height of the video
   * @return The height of the video
   */
  public int getHeight();

  /** Return the width of the video
   * @return The width of the video
   */
  public int getWidth();

  /** Return the number of frames of the video
   * @return The number of frames of the video
   */
  public int getFrames();

  /** Return the video type
   * @see #TYPE_UNDEFINED
   * @see #TYPE_JMF_VIDEO
   * @return The video type
   */
  public int getVideoType();
}
