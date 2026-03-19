/**
 * Graphical User Interface for KIT107 Lab 4.
 * 
 * @author Julian Dermoudy and <<Meharaj Hosaine Sami>>
 * @version 19/03/2026
 * 
 */


import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;


public class Lab4Frame extends Frame implements ActionListener
{
    private final int SIZE=10; // number of pages in note pad
    
    private StickyNotePad snp; // the note pad
    private TextArea text;  // area to display current note
    private Button prevNote; // navigation button for previous note
    private Button newNote;  // navigation button for next note
    private Button delNote;  // request deletion of current note
    private Button nextNote; // request addition of new note
    
    private boolean started; // flag to avoid race-condition between constructor and paint()
    
    /**
     * The constructor.
     */  
    public Lab4Frame()
    {
        started = false;

        // create a note pad object with capacity for 10 notes (using SIZE)
        snp=new StickyNotePad(SIZE);
        
        // set up GUI window
        setBackground(Color.darkGray);
        setTitle("Lab4");
        setSize(400,250);
        setLayout(new FlowLayout());
        
        // set up text area widget
        text=new TextArea(10,50);
        text.setBackground(new Color(243, 122, 72));
        text.setEditable(false);
        add(text);
        
        // set up button widgets
        prevNote=new Button("<- Previous");
        prevNote.addActionListener(this);
        add(prevNote);
        newNote=new Button("  New ");
        newNote.addActionListener(this);
        add(newNote);
        delNote=new Button("Delete");
        delNote.addActionListener(this);
        add(delNote);
        nextNote=new Button("  Next ->  ");
        nextNote.addActionListener(this);      
        add(nextNote);
        
        // configure close box on window's title bar
        this.addWindowListener
            (
             new WindowAdapter()
                 {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        }
        );  

        started = true;
    }
    
    /**
     * paint() method for GUI update.
     */  
    public void paint(Graphics g)
    {
        if (started)
        {
            // place content of current note in the text area
            text.setText(snp.getNote());
        }
    }
    
    /**
     * actionPerformed() method to handle events.
     */  
    public void actionPerformed(ActionEvent e)
    {
        String text; // text for note
        
        // handle new note requests
        if ((e.getSource() == newNote) && (! snp.isFull()))
        {
            // get note contents from the user and add a new note to the note pad
            text=JOptionPane.showInputDialog("Enter description of note:");
            if ((text!=null) && (text.length()!=0))
            {
                snp.addNote(text);
            }
        }
        
        // if pad is not empty, handle navigation and deletion
        if (! snp.isEmpty())
        {
            // handle delete note requests
            if (e.getSource() == delNote)
            {
                // request deletion of the current note from the note pad
                snp.deleteNote();
            }
            
            // handle navigation requests to the previous note
            if (e.getSource() == prevNote)
            {
                // request move to previous note in the note pad
                snp.previousNote();
            }
            
            // handle navigation requests to the next note
            if (e.getSource() == nextNote)
            {
                // request move to next note in the note pad
                snp.nextNote();
            }   
        }
        //update GUI with contents of current note  
        repaint();
    }
}
