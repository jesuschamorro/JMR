package jmr.media;

/**
 * <p>Title: JMR Project</p>
 * <p>Description: Java Multimedia Retrieval API</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: University of Granada</p>
 * @author Jesus Chamorro Martinez
 * @version 1.0
 */

public interface Media {

  /**
  * Represents an undefined media type
  */
  public static final int TYPE_UNDEFINED = -1;

  /**
    * Represents an image media type
    */
  public static final int TYPE_JMR_IMAGE = 1;

  /**
  * Represents a video media type
  */
  public static final int TYPE_JMR_VIDEO = 2;

  /**
  * Represents an audio media type
  */
  public static final int TYPE_JMR_AUDIO = 3;

  /** Return the media type
   * @see #TYPE_UNDEFINED
   * @see #TYPE_JMR_IMAGE
   * @see #TYPE_JMR_VIDEO
   * @see #TYPE_JMR_AUDIO
   * @return The media type
   */
  public int getMediaType();
}
