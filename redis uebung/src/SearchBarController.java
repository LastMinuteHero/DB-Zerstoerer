import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import redis.clients.jedis.Jedis;

public class SearchBarController {
	Jedis connection;
	
	String[] names = new String[0];
	String[] businesses;
	JComboBox cb;
	JTextArea text;
	
	//konstruktor für den Controller, hier werden die Teile der UI mitgegeben, die kontrolliert werden sollen
	//und auch die connection, da hier anfragen gestellt werden
	SearchBarController(Jedis connector, JComboBox comboBox, JTextArea textArea){
		this.connection = connector;
		this.cb = comboBox;
		this.text = textArea;

	}
	
	//diese methode sucht nach den keys, daher wurden in AutoComp die namen als keys abgespeichert
	//dann werden die Namen die gefunden werden in die combobox gestellt
	public void findNames() {
		//hier wird der editor extrahiert
		JTextField editor = (JTextField) cb.getEditor().getEditorComponent();
		//hier setzt er die Suche mit dem Stern am ende für die patternsuche
		String searchTerm = editor.getText()+"*";
		//hier sucht man in redis nach den keys zB keys Starbu*
		names = connection.keys(searchTerm).toArray(new String[connection.keys(searchTerm).size()]);
		//die Ergebnisse werden in die combobox gesetzt
		DefaultComboBoxModel model = new DefaultComboBoxModel( names );
		//das wurde eingefügt sonst hat er einfach immer was schon ausgewählt von der Liste
	    model.setSelectedItem(editor.getText());
	    //hier wird die Combobox gesetzt
	    cb.setModel( model );		
	}

	//hier werden Attribute gesucht und eingespeichert
	public void searchAttributes() {
		//hier wird wieder der editor extrahiert
		JTextField editor = (JTextField) cb.getEditor().getEditorComponent();
		//hier holt er sich den text
    	String searchTerm = editor.getText();
    	//hier werden alle businessids zu dem namen geladen
    	businesses = connection.smembers(searchTerm).toArray(new String[connection.smembers(searchTerm).size()]);
    	String[] attributesSearchTerm = new String[businesses.length];
    	
    	//hier werden die keys für die suchen erstellt
    	for(int i = 0; i<businesses.length; i++) {
    		attributesSearchTerm[i] = "business:"+businesses[i]+":categories";
    	}
    	
    	//hier holt man sich dann alle attributes und haut sie zusammen mittels SUNION 
    	Set<String> output = connection.sunion(attributesSearchTerm);
    	System.out.println(output);
    	text.setText(output.toString());
	}
}
