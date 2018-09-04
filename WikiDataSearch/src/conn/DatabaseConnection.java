package conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import entity.Factor;
import entity.Property;

public class DatabaseConnection {
	Connection connection;
	String url = "jdbc:mysql://localhost/wikidata?user=root&password=zxc123&useUnicode=true&Encoding=utf8";
	public LinkedList<String> matchingEntityIDs = new LinkedList<String>();
	public LinkedList<Factor> Labels = new LinkedList<Factor>();
	public LinkedList<Factor> Aliases = new LinkedList<Factor>();
	public LinkedList<Factor> Descriptions = new LinkedList<Factor>();
	public LinkedList<Property> Property = new LinkedList<Property>();

	public void getConn() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public boolean searchByString(String searchText) {

		try {
			matchingEntityIDs.clear();
			String sql_labels = "select entity_id from labels where content=?";
			java.sql.PreparedStatement psLabels = connection.prepareStatement(sql_labels);
			psLabels.setString(1, searchText);
			ResultSet rs = psLabels.executeQuery();
			while (rs.next()) {
				matchingEntityIDs.add(rs.getString(1));// catch entity labels
			}

			String sql_aliases = "select entity_id from aliases where content = ?";
			java.sql.PreparedStatement psAliases = connection.prepareStatement(sql_aliases);
			psAliases.setString(1, searchText);
			ResultSet rs_aliases = psAliases.executeQuery();
			while (rs_aliases.next()) {
				matchingEntityIDs.add(rs_aliases.getString(1));
			}

			if (matchingEntityIDs.size() == 0) {
				return false;
			} else {
				return true;
			}

		} catch (SQLException e) {

			e.printStackTrace();
			return false;
		}
	}

	public boolean searchInLabels(String entityID) {
		try {
			Labels.clear();
			String sql = "select entity_id ,language,content from labels where entity_id = ?";
			java.sql.PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, entityID);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Factor tempFactor = new Factor();
				tempFactor.entity_id = rs.getString(1);
				tempFactor.language = rs.getString(2);
				tempFactor.content = rs.getString(3);
				Labels.add(tempFactor);
			}
			if (Labels.size() == 0) {
				return false;
			} else {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean searchInAliases(String entityID) {

		try {
			Aliases.clear();
			String sql = "select entity_id ,language,content from aliases where entity_id = ?";
			java.sql.PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, entityID);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Factor tempFactor = new Factor();
				tempFactor.entity_id = rs.getString(1);
				tempFactor.language = rs.getString(2);
				tempFactor.content = rs.getString(3);
				Aliases.add(tempFactor);
			}
			if (Aliases.size() == 0) {
				return false;
			} else {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean searchInDescriptions(String entityID) {

		try {
			Descriptions.clear();
			String sql = "select entity_id ,language,content from descriptions where entity_id = ?";
			java.sql.PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, entityID);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Factor tempFactor = new Factor();
				tempFactor.entity_id = rs.getString(1);
				tempFactor.language = rs.getString(2);
				tempFactor.content = rs.getString(3);
				Descriptions.add(tempFactor);
			}
			if (Descriptions.size() == 0) {
				return false;
			} else {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean searchInProperty(String entityID) {
		try {
			Property.clear();
			String sql = "select property_id ,entity_id,valueType,value from property where entity_id = ?";
			java.sql.PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, entityID);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Property tempProperty = new Property();
				tempProperty.property_id = rs.getString(1);
				tempProperty.entity_id = rs.getString(2);
				tempProperty.valueType = rs.getString(3);
				tempProperty.value = rs.getString(4);
				Property.add(tempProperty);
			}
			if (Property.size() == 0) {
				return false;
			} else {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
