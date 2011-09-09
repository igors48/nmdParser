package app.iui.flow.custom;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;

/**
 * @author Igor Usenko
 *         Date: 03.10.2010
 */
public class CheckBoxJList extends JList implements ListSelectionListener {

    static Color listForeground, listBackground;

    static {
        UIDefaults uid = UIManager.getLookAndFeel().getDefaults();
        listForeground = uid.getColor("List.foreground");
        listBackground = uid.getColor("List.background");
    }

    HashSet selectionCache = new HashSet();

    int toggleIndex = -1;
    boolean toggleWasSelected;

    public CheckBoxJList() {
        super();
        setCellRenderer(new CheckBoxListCellRenderer());
        addListSelectionListener(this);
    }

    public void clearSelection() {
        selectionCache.clear();
        super.clearSelection();
    }

    public void valueChanged(ListSelectionEvent lse) {

        if (!lse.getValueIsAdjusting()) {
            removeListSelectionListener(this);

            // determine if this selection has added or removed items
            HashSet newSelections = new HashSet();
            int size = getModel().getSize();

            for (int i = 0; i < size; i++) {
                if (getSelectionModel().isSelectedIndex(i)) {
                    newSelections.add(new Integer(i));
                }
            }

            // turn on everything that was selected previously
            Iterator it = selectionCache.iterator();

            while (it.hasNext()) {
                int index = ((Integer) it.next()).intValue();
                getSelectionModel().addSelectionInterval(index, index);
            }

            // add or remove the delta
            it = newSelections.iterator();

            while (it.hasNext()) {
                Integer nextInt = (Integer) it.next();
                int index = nextInt.intValue();

                if (selectionCache.contains(nextInt))
                    getSelectionModel().removeSelectionInterval(index, index);
                else
                    getSelectionModel().addSelectionInterval(index, index);
            }

            // save selections for next time
            selectionCache.clear();

            for (int i = 0; i < size; i++) {

                if (getSelectionModel().isSelectedIndex(i)) {
                    selectionCache.add(new Integer(i));
                }
            }

            addListSelectionListener(this);
        }
    }
    /*
     public class CheckBoxListCellRenderer extends JPanel implements ListCellRenderer{
     private ListCellRenderer delegate;
     private JCheckBox checkBox = new JCheckBox();

     public CheckBoxListCellRenderer(){
         this.delegate = new DefaultListCellRenderer();
         setLayout(new BorderLayout());
         setOpaque(true);
         checkBox.setOpaque(true);
     }

     public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
         Component renderer = delegate.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
         checkBox.setSelected(isSelected);

         removeAll();

         add(checkBox, BorderLayout.WEST);
         add(renderer, BorderLayout.CENTER);

         return this;
     }
 }   */
    /*
    class CheckBoxListCellRenderer extends JCheckBox implements ListCellRenderer {

        Color background;
        Color foreground;

        public CheckBoxListCellRenderer() {
            super();
            setOpaque(true);
        }


        public Component getListCellRendererComponent(JList list,
                                                      Object value,
                                                      int index,
                                                      boolean isSelected,
                                                      boolean cellHasFocus) {

            //setPreferredSize(new Dimension(getPreferredSize().width, 0));
         //setComponentOrientation(list.getComponentOrientation());           
            //JCheckBox result = new JCheckBox();
            JCheckBox result = this;

            result.setText(value.toString());
            result.setSelected(isSelected);

            if (isSelected) {
                background = listForeground;
                foreground = listBackground;
            } else {
                background = listBackground;
                foreground = listForeground;
            }

            result.setBackground(background);
            result.setForeground(foreground);

            return result;
        }
    }

    */
    /*
       class CheckBoxListCellRenderer extends JLabel implements ListCellRenderer {
         public CheckBoxListCellRenderer() {
             setOpaque(true);
         }
         public Component getListCellRendererComponent(
             JList list,
             Object value,
             int index,
             boolean isSelected,
             boolean cellHasFocus)
         {
             setText(value.toString());
             setBackground(isSelected ? Color.red : Color.white);
             setForeground(isSelected ? Color.white : Color.black);
             return this;
         }
     }
    */

    class CheckBoxListCellRenderer extends JComponent
            implements ListCellRenderer {
        DefaultListCellRenderer defaultComp;
        JCheckBox checkbox;

        public CheckBoxListCellRenderer() {
            super();
            setLayout(new BorderLayout());
            defaultComp = new DefaultListCellRenderer();
            checkbox = new JCheckBox();
            add(checkbox, BorderLayout.WEST);
            add(defaultComp, BorderLayout.CENTER);
        }


        public Component getListCellRendererComponent(JList list,
                                                      Object value,
                                                      int index,
                                                      boolean isSelected,
                                                      boolean cellHasFocus) {
            defaultComp = (DefaultListCellRenderer) defaultComp.getListCellRendererComponent(list, value, index,
                    isSelected, cellHasFocus);
            checkbox.setSelected(isSelected);
            Component[] comps = getComponents();

            for (int i = 0; i < comps.length; i++) {
                comps[i].setForeground(listForeground);
                comps[i].setBackground(listBackground);
            }

            return this;
        }
    }


}



