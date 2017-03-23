package jmr.descriptor;

import java.lang.reflect.Constructor;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import jmr.grid.Grid;

/**
 * Class representing a list of descriptors (one for each tile) associated to a 
 * gridded media. 
 * 
 * @param <T> the type of the media associated to this grid-based descriptor.
 * 
 * @author Jesús Chamorro Martínez (jesus@decsai.ugr.es)
 */
public class GriddedDescriptor<T> extends MediaDescriptorAdapter<T>{    
    /**
     * Grid associated to this descriptor
     */
    private final Grid<T> grid;
    
    /**
     * List of descriptors
     */
    private ArrayList<MediaDescriptor<T>> descriptors;
    
    
    /**
     * Constructs a new descriptor using the given grid (and its media) where
     * each tile is described by means of a descriptor of the class 
     * <code>descriptorClass</code>.
     * 
     * The class <code>descriptorClass</code> have to provide, at least, a 
     * constructor with a single parameter of type <code>T</code>.
     * 
     * @param grid the grid associated to this descriptor
     * @param descriptorClass the descriptor class for each tile
     */
    public GriddedDescriptor(Grid<T> grid, Class descriptorClass) {
        super((T)grid.getSource(), new DefaultComparator());
        // The previous call does not initialize the tile descriptors. It will 
        // be done in the following setTilesDescriptors() call
        this.grid = grid;
        this.setTilesDescriptors(descriptorClass);
    }
    /**
     * First initialization of the descriptor as an empty list of descriptor.
     * 
     * Later, the list should be filled in with the descriptors of each tile 
     * (by calling {@link #setTilesDescriptors(java.lang.Class) }).
     *
     * @param media the media associated to this descriptor
     */
    @Override
    public void init(T media) {
        descriptors = new ArrayList<>();
        // We also should add to the list the tiles descriptors, but this method
        // is call from the superclass constructor so, when this code is
        // executed, the local member data (used for constructing the tiles 
        // descriptors) are no initialized yet. Thus, after the super() call 
        // in the construtor, we have to initialize the rest of the descriptor.
    }
    
    /**
     * Set the list of descriptor by calculating a descriptor for each tile.  
     *
     */
    private void setTilesDescriptors(Class descriptorClass) {
        T tile;
        MediaDescriptor descriptor;
        for (int i = 0; i < grid.getNumTiles(); i++) {
            tile = (T)grid.getTile(i);            
            descriptor = MediaDescriptorFactory.getInstance(descriptorClass, tile);
            descriptors.add(descriptor);
        }
    }
    
    
    /**
     * Returns a string representation of this descriptor
     * .
     * @return a string representation of this descriptor 
     */
    @Override
    public String toString(){
        String output ="";
        for(MediaDescriptor descriptor : descriptors){
            output += descriptor.toString()+"\n";
        }
        return output;
    }

    /**
     * Functional (inner) class implementing a comparator between list descriptors
     */
    static class DefaultComparator implements Comparator<GriddedDescriptor, Double> {
        @Override
        public Double apply(GriddedDescriptor t, GriddedDescriptor u) {
            if(t.descriptors.size() != u.descriptors.size()){
                throw new InvalidParameterException("The descriptor lists must have the same size.");
            }
            Double item_distance, sum = 0.0;
            MediaDescriptor m1, m2;
            for(int i=0; i<t.descriptors.size(); i++){
                try{
                    m1 = (MediaDescriptor)t.descriptors.get(i);
                    m2 = (MediaDescriptor)u.descriptors.get(i);
                    item_distance = (Double)m1.compare(m2);
                    sum += item_distance*item_distance;
                }
                catch(ClassCastException e){
                    throw new InvalidParameterException("The comparision between descriptors is not interpetrable as a double value.");
                }
                catch(Exception e){
                    throw new InvalidParameterException("The descriptors are not comparables.");
                }                
            }
            return Math.sqrt(sum);
        }    
    }
    
}
