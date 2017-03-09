package jmr.iu;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

import javax.swing.*;
import jmr.descriptor.Comparator;
import jmr.descriptor.SingleColorDescriptor;
import jmr.grid.SquareGrid;
import jmr.media.JMRBufferedImage;


public class Test {
    boolean packFrame = false;

    /**
     * Construct and show the application.
     */
    public Test() {
        
        JMRBufferedImage img = null;
        SingleColorDescriptor d = new SingleColorDescriptor(img);
        System.out.println("Color: " + d.getColor());
        SingleColorDescriptor d2 = new SingleColorDescriptor(Color.LIGHT_GRAY);
        System.out.println("Color: " + d2.getColor());
        SingleColorDescriptor d3 = new SingleColorDescriptor(Color.WHITE);
        System.out.println("Color: " + d3.getColor());
        
        Object o;
        o = d2.compare(d3);
        System.out.println("Output: " + o + " de tipo " + o.getClass());
        Comparator<SingleColorDescriptor, Double> c = (a,b)->1.0;
        d2.setComparator(c);
        o = d2.compare(d3);
        System.out.println("Output: " + o + " de tipo " + o.getClass());
        Comparator<SingleColorDescriptor, Color> c2 = (a,b)->Color.blue;
        d2.setComparator(c2);
        o = d2.compare(d3);
        System.out.println("Output: " + o + " de tipo " + o.getClass());
        
    }

    /**
     * Application entry point.
     *
     * @param args String[]
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {                
                new Test();
            }
        });
    }
}
