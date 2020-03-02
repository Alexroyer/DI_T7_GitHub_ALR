
package components;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.MalformedURLException;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.KeyStroke;
import javax.swing.ImageIcon;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.help.*;
import java.net.URL; 
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.help.HelpSet;
import javax.swing.JOptionPane;

/* MenuDemo.java requires images/middle.gif. */

/*

 */




public class LanzaAyuda implements ActionListener, ItemListener {
    JTextArea output;
    JScrollPane scrollPane;
    String newline = "\n";
    
    

    public JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem botonAyuda;
        JMenuItem menuContenidoAyuda;
        
        
        
        HelpSet hs = obtenFicAyuda(); 
        HelpBroker hb = hs.createHelpBroker();
         
        
        //Create the menu bar.
        menuBar = new JMenuBar();

        //Build the first menu.
        menu = new JMenu("Ayuda");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "Contenido de la ayuda");
        menuBar.add(menu);

        //a group of JMenuItems
        botonAyuda = new JMenuItem("BOTON AYUDA",
                                 KeyEvent.VK_T);
        //menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
        botonAyuda.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_F1, ActionEvent.ALT_MASK));
        botonAyuda.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        menu.add(botonAyuda);
        menuContenidoAyuda = new JMenuItem("MENU CONTENIDO",
                                 KeyEvent.VK_T);
        //menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
        botonAyuda.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
        botonAyuda.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        menu.add(botonAyuda);
        
        hb.enableHelpOnButton(botonAyuda,"top",hs);
        hb.enableHelpOnButton(menuContenidoAyuda, "introduccion", hs);

        return menuBar;
    }
    
   
    
    /*
        IMPORTANTÍSIMO EL FUNCIONAMIENTO EN ESTE CÓDIGO PARA CARGAR
            DENTRO DEL PAQUETE .jar
        LOS RECURSOS NECESARIOS
        
        SE LE ESPECIFÍCA LA url con ESTA NOMENCLATURA:
        "jar:file:LanzaAyuda.jar!/help/helpset.hs"
    
        esto es porque se le 
        índica que -> parta desde el paquete LanzaAyuda que se acaba de crear. jar:file:LanzaAyuda.jar
        LUEGO UN SÍMBOLO DE EXCLAMACIÓN !
        y luego /help/helpset.hs porque es lo que hay en la raíz del LanzaAyuda.jar, que al final es un paquete
    
    LanzaAyuda.jar/
        
            /components
                LanzaAyuda.class
            /help
                /fuentes -> todos los HTML
                /images  -> todas las imagenes
                /JavaHelpSearch -> ficheros DOCS, DOCS.TAB, OFFSETS, POSITIONS, SCHEMA, TMAP
                ayuda.jar
--->  ---> -->  helpset.hs
                index.xml
                map.jhm
                toc.xml
    
            META-INF
                MANIFEST.MF
          
    */
    public HelpSet obtenFicAyuda() {
        try {
            
            URL url = new URL("jar:file:LanzaAyuda.jar!/help/helpset.hs");
            HelpSet hs = new HelpSet(null, url);
            return hs;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Fichero HelpSet no encontrado");
            return null;
        }
    }

    public Container createContentPane() {
        //Create the content-pane-to-be.
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setOpaque(true);

        //Create a scrolled text area.
        output = new JTextArea(5, 30);
        output.setEditable(false);
        scrollPane = new JScrollPane(output);

        //Add the text area to the content pane.
        contentPane.add(scrollPane, BorderLayout.CENTER);

        return contentPane;
    }

    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem)(e.getSource());
        String s = "Action event detected."
                   + newline
                   + "    Event source: " + source.getText()
                   + " (an instance of " + getClassName(source) + ")";
        output.append(s + newline);
        output.setCaretPosition(output.getDocument().getLength());
    }

    public void itemStateChanged(ItemEvent e) {
        JMenuItem source = (JMenuItem)(e.getSource());
        String s = "Item event detected."
                   + newline
                   + "    Event source: " + source.getText()
                   + " (an instance of " + getClassName(source) + ")"
                   + newline
                   + "    New state: "
                   + ((e.getStateChange() == ItemEvent.SELECTED) ?
                     "selected":"unselected");
        output.append(s + newline);
        output.setCaretPosition(output.getDocument().getLength());
    }

    // Returns just the class name -- no package info.
    protected String getClassName(Object o) {
        String classString = o.getClass().getName();
        int dotIndex = classString.lastIndexOf(".");
        return classString.substring(dotIndex+1);
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = LanzaAyuda.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() throws MalformedURLException {
        //Create and set up the window.
        JFrame frame = new JFrame("MenuDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        LanzaAyuda demo = new LanzaAyuda();
        frame.setJMenuBar(demo.createMenuBar());
        frame.setContentPane(demo.createContentPane());

        //Display the window.
        frame.setSize(450, 260);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createAndShowGUI();
                } catch (MalformedURLException ex) {
                    Logger.getLogger(LanzaAyuda.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
