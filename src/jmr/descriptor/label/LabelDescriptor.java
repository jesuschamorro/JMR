package jmr.descriptor.label;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jmr.descriptor.Comparator;
import jmr.descriptor.MediaDescriptorAdapter;

/**
 * A descriptor representing a list of labels associated to a visual media.
 * 
 * @param <T> the type of media described by this descriptor.
 * 
 * @author Jesús Chamorro Martínez (jesus@decsai.ugr.es)
 */
public class LabelDescriptor<T> extends MediaDescriptorAdapter<T> implements Serializable{
    
    /**
     * List of Labels associated to this descriptor.
     */
    private List<String> labels;
    /**
     * A classifier used for labeling a given media. It uses a standard
     * functional interface, allowing lambda expressions.
     */
    private Classifier<T, ? extends LabeledClassification> classifier = null;
    
    
    /**
     * Constructs a multiple label descriptor using the default classifier to 
     * label the given image, and set as comparator the default one.
     * 
     * @param media the media source
     */
    public LabelDescriptor(T media) {
        this(media, new DefaultClassifier());
        // Particular case. If a new object is constructed by passing a single 
        // String, this constructor is called instead of the specific one for 
        // String type. Only works as expected if the 'new' operation is made 
        // by indicating explicitly the type (new MultipleLabelDescriptor<T>),
        // but probably this will no be the case. In order to avoid confusing 
        // results, this particular case is considered
        if(media!=null && media.getClass()==String.class){
            setSource(null); // Source and labels are set to null, but not the 
                             // comparator and classifier 
            this.labels =  new ArrayList();
            this.labels.add((String)media);
        }       
    }   
    
    /**
     * Constructs a multiple label descriptor using the given classifier to
     * label the given image, and set as comparator the default one.
     *
     * @param media the media source
     * @param classifier the classifier used for labeling a given media. The
     * result type of the classifier must be <code>List&lt;String&gt;</code>
     */
    public LabelDescriptor(T media, Classifier classifier) {
        super(media, new DefaultComparator()); //Implicit call to init 
        // The previous call does not initialize the label since the classifier
        // has not been assigned yet. Therefore, in the following sentences the
        // classifier data member is initialize and then used for obtaining the
        // label of this descriptor
        this.setClassifier(classifier);
        this.init(media); //Second call, but needed (see init method)
    }   
        
    /**
     * Constructs a multiple label descriptor, initializes it with the given 
     * labels and set as comparator and classifier the default ones.
     * 
     * @param label the first label of this descriptor.
     * @param labels the second and following labels of this descriptor.
     */
    public LabelDescriptor(String label, String... labels) {
        this((T)null); //Default comparator and classifier; null source
        this.labels =  new ArrayList(Arrays.asList(labels));          
        this.labels.add(0,label);
    }
    
    /**
     * Initialize the descriptor by using the classifier.
     *
     * @param media the media used for initializating this descriptor
     */
    @Override
    public void init(T media) {
        labels = media!=null && classifier!=null ? 
                classifier.apply(media).getLabels() : null;
        // When this method is called from the superclass constructor, the local
        // member data, and particularly the classifier, are not initialized 
        // yet. Thus, in the construction process, the previous code always 
        // initializes the labels to null. For this reason, after the super() 
        // call in the constructor, we have to (1) initialize the rest of the 
        // descriptor (particularly the classifier) and (2) to calculte the
        // labels again (for example, calling this init method again).
        //
        // Note that this method is not only called from the constructor, it is 
        // also called from the setSource method (which allow to chage de media
        // and, consequently, it changes the label using the current classidier
    }
    
    /**
     * Returns the number of labels in this descriptor.
     *
     * @return the number of labels in this descriptor.
     */
    public int size() {
        return labels!=null ? labels.size() : 0;
    }
    
    /**
     * Returns <tt>true</tt> if this descriptor contains no labels.
     *
     * @return <tt>true</tt> if this descriptor contains no labels.
     */
    public boolean isEmpty() {
        return labels!=null ? labels.isEmpty() : true;
    }
    
    /**
     * Returns the label at the specified position in this descriptor.
     *
     * @param  index index of the label to return
     * @return the label at the specified position in this descriptor
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public String getLabel(int index) {
        return labels.get(index);
    }
    
    /**
     * Set the classifier for this descriptor.
     *
     * @param classifier the new classifier. The result type of the classifier
     * must be of type {@link jmr.descriptor.label.LabeledClassification}. If
     * the given parameter is null, the default clasifier is assigned.
     */
    public void setClassifier(Classifier<T, ? extends LabeledClassification> classifier) {
        this.classifier = classifier != null ? classifier : new DefaultClassifier();        
        // No null classifier is allowed. If the given parameter is null, the 
        // default one is used.
    }
    
    /**
     * Returns the classifier of this descriptor. 
     * 
     * @return the classifier of this descriptor. 
     */
    public Classifier getClassifier(){
        return classifier;
    }
    
    /**
     * Returns a string representation of this descriptor.
     * 
     * @return a string representation of this descriptor 
     */
    @Override
    public String toString(){
        return this.getClass().getSimpleName()+": "+labels.toString();
    }
          
    /**
     * Functional (inner) class implementing a comparator between single label
     * descriptors. It returns 1.0 if the labels are the same in both
     * descriptors (ignoring upper cases and position), 0.0 in other case.
     */
    static class DefaultComparator implements Comparator<LabelDescriptor, Double> {
        @Override
        public Double apply(LabelDescriptor t, LabelDescriptor u) {
            // If the number of labels is not the same, the descriptors are 
            // assumed to be different
            if(t.size() != u.size()){
                return 1.0;
            }            
            // We are assuming that there are not repeated labels in the same 
            // descriptor
            int equal ;
            String label_i;
            for(int i=0; i<t.size(); i++){
                label_i = t.getLabel(i);
                equal = 1;
                // We search the nearest one
                for(int j=0; j<u.size() && equal!=0 ; j++){
                    equal = label_i.compareToIgnoreCase(u.getLabel(j)); // 0 if equals
                }
                if(equal!=0) return 1.0; //Same label not found
            }
            return 0.0;
        }
    }
    
    /**
     * Functional (inner) class implementing a default classifier. This
     * implementation labels the media with only one label equals to the
     * (simple) name of its class.
     */
    static class DefaultClassifier<T> implements Classifier<T, LabeledClassification> {
        @Override
        public LabeledClassification apply(T t) {
            ArrayList<String> list = new ArrayList();
            if(t!=null) list.add(t.getClass().getSimpleName());
            return new LabeledClassification(){
                public List<String> getLabels() {return list;}
                public boolean isWeighted() {return false;}
                public List<Double> getWeights() {return null;}              
            };
        }
        
        public void aaa(){
            
        }
    }
}
