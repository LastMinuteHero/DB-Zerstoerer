import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

public class JsonReader {
	
	//hier ist die business filepath
	private static final String filePath = "C:\\data\\dataset\\business.json";
	
	
	public static void main(String[] args) {
		

		try {
			
			// reading the json file line by line
			BufferedReader breader = new BufferedReader(new FileReader(filePath));
			// Parsing and creating a JsonObject
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = null;
			
			//hier connecte ich auf redis ohne cluster
			Jedis jedis = new Jedis("localhost");
			
			//hier werden die redis cluster angesprochen beachtet, dass aufm cluster die autovervollständigung nicht läuft
	        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
	        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 30001));
	        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 30002));
	        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 30003));
	        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 30004));
	        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 30005));
	        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 30006));
			//JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes);
			
			//Wenn das ganze als Cluster laufen soll, muss man als eingabe für die folgenden zwei konstruktoren
			//als eingabe eine JedisCluster geben
			//dann aber auch die Eingabe in der klasse Json2Redis und AutoComp abändern, ich weiß ist nicht schön
			//aber es funktioniert ;)
			
			
			//die folgenden zwei braucht man nur um die Daten einzuladen, ihr könnt auch die mehtode in autocomp in json2redis hauen
			Json2Redis j2r = new Json2Redis(jedis);
			AutoComp autocomp = new AutoComp(jedis);
			
			//hier wird die Searchbar classe generiert
			SearchBar searchBar = new SearchBar();
			
			//hier wird die methode aufgerufen, die dann die GUI erstellt, die GUI braucht auch die connection, 
			//da dort suchanfragen gestellt werden für die autovervolständigung
			searchBar.createGUI(jedis);
			
			//hier werden die daten in die Datenbank eingeladen, ist bisl unschön evtl das als mehtode darstellen und dann
			//die methode nur bei bedarf aufrufen
			
			//loads the Business in line for line
//			String newLine=breader.readLine();
//			while(newLine != null) {
//				jsonObject = (JSONObject) jsonParser.parse(newLine);
//				j2r.loadBusiness(jsonObject);
//				autocomp.loadAutoCompName(jsonObject);
//				
//				newLine = breader.readLine();	
//			}
			
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
//		} catch (ParseException ex) {
//			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

	}

}