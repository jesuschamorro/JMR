package jmr.media;

/**
 * <p>Title: JMR Project</p>
 * <p>Description: Java Multimedia Retrieval API</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: University of Granada</p>
 * @author Jesus Chamorro Martinez
 * @version 1.0
 */

public interface JMRAudio extends Media {

  /**
  * Represents a custom audio type
  */
  public static final int TYPE_CUSTOM = 0;

  /**
  * Represents an audio based on the JMF audio classes
  */
  public static final int TYPE_JMF_AUDIO = 1;

  /** Return the duration of the audio in seconds
   * @return The duration of the audio
   */
  public float getDuration();

  /** Return the audio type
   * @see #TYPE_UNDEFINED
   * @see #TYPE_JMF_AUDIO
   * @return The audio type
   */
  public int getAudioType();
}
