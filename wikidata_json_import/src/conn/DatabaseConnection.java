package conn;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import material.Aliases;
import material.Descriptions;
import material.Entity;
import material.Labels;
import material.Properties;
import util.ImportContentDispose;
import util.JsonFileRead;


public class DatabaseConnection {

	Connection conn;
	PreparedStatement psEntity;
	PreparedStatement psLabels;
	PreparedStatement psAliases;
	PreparedStatement psDescriptions;
	PreparedStatement psProperty;
	//===================================================
	String entity_id ;
	String entity_type;
	ArrayList<Labels> labelsArrayList ;
	ArrayList<Aliases> aliasesArrayList ;
	ArrayList<Descriptions> descriptionsArrayList;
	ArrayList<Properties> propertiesArrayList ;
	//===================================================================================
	
	public void getConn(){
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String url="jdbc:mysql://localhost/wikidata?user=root&password=zxc123"+
					"&useUnicode=true&characterEncoding=utf8";
			conn = DriverManager.getConnection(url);
			conn.setAutoCommit(false);
			//conn.setAutoCommit(true);
			String sqlEntity = "insert into entity(id,type) "+"values(?,?)";
			String sqlLabels ="insert into labels(entity_id,language,content) "+"values(?,?,?)";
			String sqlAliases = "insert into aliases(entity_id,language,content) "+"values(?,?,?)";
			String sqlDescriptions = "insert into descriptions(entity_id,language,content) "+"values(?,?,?)";
			String sqlProperty = "insert into property(property_id,entity_id,valueType,value) "+"values(?,?,?,?)";
			psEntity= conn.prepareStatement(sqlEntity);
			psLabels= conn.prepareStatement(sqlLabels);
			psAliases= conn.prepareStatement(sqlAliases);
			psDescriptions= conn.prepareStatement(sqlDescriptions);
			psProperty= conn.prepareStatement(sqlProperty);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public  boolean recieveEntity(Entity transEntity){	
		try{
			if(transEntity==null || transEntity.entity_id==null){
				return false;
			}
				entity_id = transEntity.entity_id;
				entity_type=transEntity.entity_type;
				labelsArrayList = transEntity.labelsArrayList;
				aliasesArrayList = transEntity.aliasesArrayList;
				descriptionsArrayList = transEntity.descriptionsArrayList;
				propertiesArrayList = transEntity.propertiesArrayList;
				psEntity.setString(1, entity_id);
				psEntity.setString(2, entity_type);
				psEntity.addBatch();
				for(int i=0;i<labelsArrayList.size();i++){
					String language = labelsArrayList.get(i).language;
					String content = labelsArrayList.get(i).value;
					content = content.replace("'", "*");
					if(ImportContentDispose.containsEmoji(content)){
						content="Emoji";
					}
					psLabels.setString(1, entity_id);
					psLabels.setString(2, language);
					psLabels.setString(3, content);
					psLabels.addBatch();
				}
				
				for(int i=0;i<aliasesArrayList.size();i++){
					String language = aliasesArrayList.get(i).language;
					String content = aliasesArrayList.get(i).value;
					content = content.replace("'", "*");
					if(ImportContentDispose.containsEmoji(content)){
						content="Emoji";
					}
					psAliases.setString(1, entity_id);
					psAliases.setString(2, language);
					psAliases.setString(3, content);
					psAliases.addBatch();
				}//============================
				for(int i = 0;i<descriptionsArrayList.size();i++){
					String language =descriptionsArrayList.get(i).language;
					String content = descriptionsArrayList.get(i).value;
					if(content!=null){
						content = content.replace("'", "*");
					}
					
					psDescriptions.setString(1, entity_id);
					psDescriptions.setString(2, language);
					psDescriptions.setString(3, content);
					psDescriptions.addBatch();
				}
				for(int i=0;i<propertiesArrayList.size();i++){
					String property_id = propertiesArrayList.get(i).id;
					//String propertyType = propertiesArrayList.get(i).propertiesType;
					String valueType = propertiesArrayList.get(i).valueType;
					String value = propertiesArrayList.get(i).value;
					value = value.replace("'", "*");
					if(ImportContentDispose.containsEmoji(value)){
						value ="Emoji";
					}
					psProperty.setString(1, property_id);
					psProperty.setString(2, entity_id);
					psProperty.setString(3, valueType);
					psProperty.setString(4, value);
					psProperty.addBatch();
				}
				return true;
			
			
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	public void insertIntoTable(){

		try{
				psAliases.executeBatch();
				conn.commit(); 
				psDescriptions.executeBatch();
				conn.commit(); 
				psEntity.executeBatch();
				conn.commit(); 
				psLabels.executeBatch();
				conn.commit(); 
				psProperty.executeBatch();
				conn.commit();  
				psAliases.clearBatch();
				psDescriptions.clearBatch();
				psEntity.clearBatch();
				psLabels.clearBatch();
				psProperty.clearBatch();
				//conn.setAutoCommit(true);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	

}
