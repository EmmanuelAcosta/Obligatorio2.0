package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dto.FeedObjects;

public class Project {
	public ArrayList<FeedObjects> GetFeeds(Connection connection) throws Exception {
		ArrayList<FeedObjects> feedData = new ArrayList<FeedObjects>();
		try {
			PreparedStatement ps = connection
					.prepareStatement("SELECT * FROM Usuario");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				FeedObjects feedObject = new FeedObjects();
				feedObject.setTitle("Hello");
				feedObject.setDescription("Hello");
				feedObject.setUrl("Hello");
				feedData.add(feedObject);
			}
			return feedData;
		} catch (Exception e) {
			throw e;
		}
	}

}