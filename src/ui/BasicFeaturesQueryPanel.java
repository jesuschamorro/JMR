package jmr.ui;

import java.util.Collection;
import jmr.descriptor.VisualLowLevelDescriptor;

/**
 * <p>Title: VisualQueryPanel</p>
 *
 * <p>Description: Visual Panel for JMR</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public abstract class BasicFeaturesQueryPanel extends VisualQueryPanel {

   protected Collection selectedDescriptors;

   public abstract boolean addToSelected(VisualLowLevelDescriptor c);
   public abstract boolean removeFromSelected(VisualLowLevelDescriptor c);
   public abstract VisualLowLevelDescriptor getSelected(int pos);

   public Collection getSelected(){
       return selectedDescriptors;
   }

}
