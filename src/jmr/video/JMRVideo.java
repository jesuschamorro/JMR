package jmr.video;

import java.awt.image.BufferedImage;

/**
 * Interface representing a video media
 * 
 * 
 * @author Jesús Chamorro Martínez (jesus@decsai.ugr.es)
 */


public interface JMRVideo {
  /**
   * Returns the height of the video frame.
   * 
   * @return the height of the video frame
   */  
  public int getHeight();
  
  /**
   * Returns the width of the video frame.
   * 
   * @return the width of the video frame
   */  
  public int getWidth();

  /** Return the number of frames of the video
   * @return The number of frames of the video
   */
  
  /**
   * Returns the number of video frames.
   * 
   * @return the number of frames
   */
  public int getNumFrames();
  
  /**
   * Returns the frame at the given position.
   * 
   * @param frame_index frame index 
   * @return the frame 
   */
  public BufferedImage getFrame(int frame_index);

}
