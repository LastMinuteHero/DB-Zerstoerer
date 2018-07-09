
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.*;
import javax.swing.event.DocumentListener;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;


public class SearchBar {
	private JFrame f;   
	private JComboBox cb;
	private JTextArea text;
	private JTextField editor;
	public SearchBarController control;
	
	public void setSearchBarElements(JFrame frame) {
		
        JPanel comboBoxPane = new JPanel();
        JPanel card1 = new JPanel(new CardLayout());
        JPanel card2 = new JPanel(new CardLayout());
        JPanel card3 = new JPanel(new CardLayout());
        JButton button1 = new JButton("search Attributes");
	    
	    this.cb = new JComboBox();  
	    this.editor = (JTextField) cb.getEditor().getEditorComponent();
	    
	    comboBoxPane.add(cb);
	    cb.setEditable(true);
	    cb.setBounds(50, 50,300,30);		
	    	    
	    this.text = new JTextArea();
	    text.setText("");
        text.setLineWrap(true);
        text.setEditable(false);
        text.setVisible(true);        
        text.setWrapStyleWord(true);
        JScrollPane scrollpane = new JScrollPane(text); 
        
        button1.setVisible(true);
        
        card1.add(comboBoxPane);
        card3.add(button1);
        card2.add(scrollpane);
	    card2.setVisible(true);
        
	    
        frame.add(card1, BorderLayout.PAGE_START);
        frame.add(card3);
        frame.add(card2, BorderLayout.CENTER);    
	    frame.setSize(400,500);    
	    frame.setVisible(true);  
	    
	}
	
	//das ist der Listener, der dann auf Eingaben hört
	private void addEditorListener() {
		
	    this.editor.addKeyListener(new KeyListener() {
	    	public void keyTyped(KeyEvent arg0) {
	    	}
			@Override
			public void keyPressed(KeyEvent arg0) {
				//wenn enter gedrückt wird, dann namen suchen und in die combobox legen
	            if (arg0.getKeyCode()==KeyEvent.VK_ENTER){
	            	SearchBar.this.findNames();
	            }
	            //wenn richtungstaste rechts gedrückt wird, dann attribute suchen
            	if(arg0.getKeyCode()==39) {
            		SearchBar.this.searchAttributes();
				}
			}
			@Override
			public void keyReleased(KeyEvent arg0) {

			}
	    });
	}
	
	protected void searchAttributes() {
		control.searchAttributes();
	}

	protected void findNames() {
		control.findNames();
	}

	//das erstellt dann die GUI mit der connection
	public void createGUI(Jedis connection) {
		JFrame frame = new JFrame("Business Searcher");
		SearchBar searchbar = new SearchBar();
		searchbar.setSearchBarElements(frame);
		searchbar.control = new SearchBarController(connection);
		
		searchbar.control.setComboBox(searchbar.cb);
		searchbar.control.setTextArea(searchbar.text);
		searchbar.control.setEditor(searchbar.editor);
		
		
		searchbar.addEditorListener();
	}
	
	

}