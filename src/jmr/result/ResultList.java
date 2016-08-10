package jmr.result;

import java.util.LinkedList;

/**
 * A list of <code>JMRResult</code> object.
 * 
 * @author Jesús Chamorro Martínez (jesus@decsai.ugr.es)
 */
public class ResultList extends LinkedList<JMRResult>{
    /**
     * Sorts this list into ascending order.
     */
    public void sort() {
        this.sort(null);
    }
}

//NOTA: Realmente es un alias para List<JMRResult>. Analizar si realmente es 
//      útil o si es preferible usar List donde se precise