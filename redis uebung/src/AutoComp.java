import org.json.simple.JSONObject;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

public class AutoComp {
	Jedis connection = null;
	
	AutoComp(Jedis jedis){
		this.connection = jedis;
	}
	//hier werden die alle namen als keys hinzugefügt dessen values die business ids sind die dessen namen tragen
	//zB gibt es mehrere Starbucks
	public void loadAutoCompName(JSONObject jsonObject) {
		String name = jsonObject.get("name").toString();
		String businessid = jsonObject.get("business_id").toString();
		
		
		if(connection.smembers(name).size()==1) {
			System.out.println(name);
		}
		//das ganze wird als set eingeladen
		connection.sadd(name, businessid);
		
	}
	
}
