package jmr.initial.ui;

import javax.swing.JPanel;
import jmr.query.Query;

/**
 * <p>Title: VisualPanel</p>
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
public abstract class QueryPanel extends JPanel {
    public abstract Query createQuery();
}
