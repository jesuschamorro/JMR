package jmr.iu;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import jmr.result.ResultList;
import jmr.result.ResultMetadata;

/**
 *
 * @author Jesús Chamorro Martínez (jesus@decsai.ugr.es)
 */
public class ImageListPanel extends javax.swing.JPanel {
    private JPanel internalPanel;
    private final static Dimension DEFAULT_COMMON_SIZE = new Dimension(100,100);
    
    /**
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
     * 
     * @param list list of result objetcs 
     */
    public ImageListPanel(ResultList list) {
        this(); 
        if (list!=null) add(list);
    }
    
    /**
     * 
     * @param list list of result objetcs 
     */
    public void add(ResultList list) {
        //Comprobar tipo de los elementos
        for (Object o : list) {
            ResultMetadata r = (ResultMetadata)o;            
            String label =  r.getResult().toString(); 
            this.add((BufferedImage)r.getMetadata(), label);           
        }
    }
    
    /**
     * 
     * @param image
     * @param imageInfo
     * @param originalSize 
     */
    public void add(BufferedImage image, String imageInfo, boolean originalSize){
        ImagePanel imgPanel = new ImagePanel(image,imageInfo);
        if(!originalSize) imgPanel.setPreferredSize(DEFAULT_COMMON_SIZE);
        internalPanel.add(imgPanel);
    }
    
    /**
     * 
     * @param image
     * @param imageInfo 
     */
    public void add(BufferedImage image, String imageInfo){
        this.add(image,imageInfo,false);
    }
    
    /**
     * 
     * @param image 
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
