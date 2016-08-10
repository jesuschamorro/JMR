package jmr.query;

/**
 * Class representing a query of type <code>T</code>
 * 
 * @author Jesús Chamorro Martínez (jesus@decsai.ugr.es)
 * @param <T> type of the query
 */
public class Query<T> {
    protected T query;
    
    public Query(T query){
        this.query = query;
    }
    
    public void setQuery(T query){
        this.query = query;
    }
    
    public T getQuery(){
        return query;
    }
    
    public Class queryClass(){
        return query==null?null:query.getClass();
    }
}
