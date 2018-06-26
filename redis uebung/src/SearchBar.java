
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
	
	public void setSearchBarElements(JFrame frame) {
		
        JPanel comboBoxPane = new JPanel();
        JPanel card1 = new JPanel(new CardLayout());
        JPanel card2 = new JPanel(new CardLayout());
        JPanel card3 = new JPanel(new CardLayout());
        JButton button1 = new JButton("search Attributes");
	    
	    this.cb = new JComboBox();  
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
	
	public void addEditorListener(Jedis connection) {
		SearchBarController sbControl = new SearchBarController(connection, cb, text);
		
		JTextField editor = (JTextField) cb.getEditor().getEditorComponent();
	    editor.addKeyListener(new KeyListener() {
	    	public void keyTyped(KeyEvent arg0) {
	    	}
			@Override
			public void keyPressed(KeyEvent arg0) {
	            if (arg0.getKeyCode()==KeyEvent.VK_ENTER){
	            	sbControl.findNames();
	            }
			}
			@Override
			public void keyReleased(KeyEvent arg0) {
			}
	    });
	}
	
	public void createGUI(Jedis connection) {
		JFrame frame = new JFrame("Business Searcher");
		SearchBar searchbar = new SearchBar();
		
		searchbar.setSearchBarElements(frame);
		searchbar.addEditorListener(connection);
	}
	
	

}