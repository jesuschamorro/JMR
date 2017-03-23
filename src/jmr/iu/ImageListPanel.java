package jmr.iu;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.List;
import javax.swing.JPanel;
import jmr.result.ResultMetadata;

/**
 * Panel for showing a list of images. 
 * 
 * @author Jesús Chamorro Martínez (jesus@decsai.ugr.es)
 */
public class ImageListPanel extends javax.swing.JPanel {
    /**
     * Internal panel for allocating images.
     */
    private JPanel internalPanel;
    /**
     * Default size for the images showed in this panel.
     */
    private final static Dimension DEFAULT_COMMON_SIZE = new Dimension(100,100);
    
    /**
     * Constructs an empty list panel.
     * 
     */
    public ImageListPanel() {
        initComponents();
        internalPanel = new JPanel();
        internalPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        scrollPanel.setViewportView(internalPanel);
        this.setPreferredSize(new Dimension(DEFAULT_COMMON_SIZE.width*5,DEFAULT_COMMON_SIZE.height+30));
    }

    /**
     * Constructs a list panel from the data stored in the given metadata list.
     * 
     * @param list list of {@link jmr.result.ResultMetadata} objetcs. The
     * metadata of each result must be an image (if not, an exception is thrown)
     */
    public ImageListPanel(List<ResultMetadata> list) {
        this(); 
        if (list!=null) add(list);
    }
    
    /**
     * Add a set of images to the list.
     * 
     * @param list list of {@link jmr.result.ResultMetadata} objetcs. The
     * metadata of each result must be an image (if not, an exception is thrown)
     */
    public void add(List<ResultMetadata> list) {  
        for (ResultMetadata r : list) {
            String label =  r.getResult().toString(); 
            this.add((BufferedImage)r.getMetadata(), label);           
        }
    }
    
    /**
     * Add a set of images to the list.
     * 
     * @param list the list of images to be added
     */
    public void add(Collection<BufferedImage> list) {  
        for (BufferedImage image : list) {
            this.add(image,null);           
        }
    }
    
    /**
     * Add a new image to the list with a tip label. By means the parameter 
     * <code>originalSize</code>, the original size or the default one can be
     * selected.
     * 
     * @param image the image to be added
     * @param label the tip label associated to the image
     * @param originalSize if <tt>true</tt>, the original image size is used
     */
    public void add(BufferedImage image, String label, boolean originalSize){
        ImagePanel imgPanel = new ImagePanel(image,label);
        if(!originalSize) imgPanel.setPreferredSize(DEFAULT_COMMON_SIZE);
        internalPanel.add(imgPanel);
    }
    
    /**
     * Add a new image to the list with a tip label and using the default size 
     * {@link #DEFAULT_COMMON_SIZE}.
     * 
     * @param image the image to be added
     * @param label the tip label associated to the image
     */
    public void add(BufferedImage image, String label){
        this.add(image,label,false);
    }
    
    /**
     * Add a new image to the list without tip label and using the default size 
     * {@link #DEFAULT_COMMON_SIZE}.
     * 
     * @param image the image to be added
     */
    public void add(BufferedImage image){
        this.add(image,null,false);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPanel = new javax.swing.JScrollPane();

        setLayout(new java.awt.BorderLayout());
        add(scrollPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane scrollPanel;
    // End of variables declaration//GEN-END:variables
}
