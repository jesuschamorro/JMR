package jmr.video;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import jmr.descriptor.MediaDescriptor;
import jmr.descriptor.MediaDescriptorAdapter;

/**
 *
 * @author Jesús Chamorro Martínez (jesus@decsai.ugr.es)
 */
public class KeyFramesDescriptor extends MediaDescriptorAdapter<JMRVideo> implements Serializable{

    
    private ArrayList<MediaDescriptor<BufferedImage>> descriptors;
    /**
     * List of the descriptor classes associated to this database
     */
    private Class descriptorClasses[] = null;
    
    
    public KeyFramesDescriptor(JMRVideo video){
        super(video,null);        
    } 
    
    @Override
    public void init(JMRVideo video) {
        //Segmentar y, para cada keyframe
        for(int i=0; i<video.getNumFrames(); i++){
            
        }
    }
    
}
