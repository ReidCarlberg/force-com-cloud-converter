package com.modelmetrics.cloudconverter.sandbox.derbydb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

import com.modelmetrics.common.util.TestCaseWithLog;

/**
 * creates the sample data distributed as part of the application.
 * 
 * @author reidcarlberg
 *
 */
public class SampleDbBuilderTest extends TestCaseWithLog {

	Random rand = new Random();
	
	String largeSampleTable = "Create table mytable (myid SMALLINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1000, INCREMENT BY 3), myname varchar(20), mynumber int, mypicklist varchar(25), mydate date, mytimestamp timestamp, mylongtext long varchar, mylookup varchar(20))";
	
	String[] names = new String[] {"Reid", "Bob", "Jerry", "Adam", "John", "Susan", "Amisha", "Libby", "Alta", "Lauren", "Phineas", "Mike", "Lydia", "Arnold", "Betty","Veronica","Alec","Billy"};
	
	String[] colors = new String[] {"red","yellow","beige","green","black"};
	
	String[] words = new String[] {"lorem", "wonderful", "lake", "house", "ruse", "model", "game", "dearth", "rabid", "chilly", "surprising", "enjoyable", "summer", "ipsum", "cute", "monolithic", "abnormal", "rock on", "airplane", "paragraph"};
	
	String[] lookups = new String[] {"CC001", "CC002"};
	
	String[] smallSampleBuilder = new String[] {
			"Create table mytable (myid SMALLINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), myname varchar(10), mynumber int, mypicklist varchar(25), mydate date, mytimestamp timestamp, mylongtext long varchar)",
			"INSERT INTO mytable (myname, mynumber, mypicklist, mydate, mytimestamp, mylongtext) VALUES ('Reid',5,'red','2008-11-10','2008-12-30 10:15:00.0', 'this is a long var char')",
			"INSERT INTO mytable (myname, mynumber, mypicklist, mydate, mytimestamp, mylongtext) VALUES ('Bob',10,'green','2008-11-11','2008-12-31 10:25:00.0', 'this is another long var char')",
			"INSERT INTO mytable (myname, mynumber, mypicklist, mydate, mytimestamp, mylongtext) VALUES ('Jerry',10500,'blue','2008-11-12','2009-01-01 10:35:00.0', 'this is yet another long var char')" };

	//TODO 2009-01-21 need to do this so we have  more meaningful sample
	
	public int getRandom(int length) {
		return rand.nextInt(length);
	}
	
	public String getNonZeroRandomAsZeroPaddedString(int maxvalue, int left) {
		String ret = ""+(1+this.getRandom(maxvalue));
		if (ret.length() < left) {
			ret = "0" + ret;
		}
		return ret;
	}
	
	public void testBuildLargerTableSample() throws Exception {

		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

		// Connection c =
		// DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb", "sa",
		// "");

		Connection c = DriverManager.getConnection(
				"jdbc:derby:./src/sampledbs/derby/sample2;create=true", "sa",
				"");

		Statement s = c.createStatement();

		s.execute(largeSampleTable);
		
		for (int i = 0; i < 100; i++) {
			
			String sql = "INSERT INTO mytable (myname, mynumber, mypicklist, mydate, mytimestamp, mylongtext, mylookup) VALUES (";
			
			//name
			sql += "'" + names[this.getRandom(names.length)] + i + "', ";
			
			//number
			sql += this.getRandom(10000) + ", ";
			
			//picklist
			sql += "'" + colors[this.getRandom(colors.length)] + "', ";
			
			//date
			sql += "'" + (1900 + getRandom(125)) + "-" + (getNonZeroRandomAsZeroPaddedString(12,2)) + "-" + (getNonZeroRandomAsZeroPaddedString(25,2)) + "', ";
			
			//timestamp
			sql += "'" + (1900 + getRandom(125)) + "-" + (getNonZeroRandomAsZeroPaddedString(12,2)) + "-" + (getNonZeroRandomAsZeroPaddedString(25,2)) + " " + getNonZeroRandomAsZeroPaddedString(23,2) + ":" + getNonZeroRandomAsZeroPaddedString(59,2) + ":00.0', ";
			
			//longtext
			int maxWords = this.getRandom(100);
			String longText = "";
			for (int j = 0; j < maxWords; j++) {
				longText += words[this.getRandom(words.length)] + " ";
			}			
			
			
			sql += "'" + longText + "', ";
			
			//lookup
			sql += "'" + lookups[this.getRandom(lookups.length)] + "') ";

			log.info(sql);
			
			try {
				s.execute(sql);
			} catch (Exception e) {
				log.debug(sql);
				throw e;
			}
		}

		ResultSet rs = s.executeQuery("SELECT * FROM MyTable");

		while (rs.next()) {
			System.out.println(rs.getString("myid") + ", " + rs.getString("myname") +", " + rs.getString("mypicklist") + ", " + rs.getString("mynumber") + ", " + rs.getString("mylongtext"));
		}

		rs.close();

		s.close();

		c.close();

		
	}
	public void testPreviousData() throws Exception {

		// new SampleHsqldbBuilder().execute();

		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

		// Connection c =
		// DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb", "sa",
		// "");

		Connection c = DriverManager.getConnection(
				"jdbc:derby:./src/sampledbs/derby/sample2;create=true", "sa",
				"");

		Statement s = c.createStatement();

		for (int i = 0; i < smallSampleBuilder.length; i++) {
			try {
				s.execute(smallSampleBuilder[i]);
			} catch (Exception e) {
				log.debug(smallSampleBuilder[i]);
				throw e;
			}
		}

		ResultSet rs = s.executeQuery("SELECT * FROM MyTable");

		while (rs.next()) {
			System.out.println(rs.getString("myname"));
		}

		rs.close();

		s.close();

		c.close();
	}
}
