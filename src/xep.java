import User.*;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.intersystems.xep.*;

public class xep {
    public static void main(String[] args) throws Exception {
        
	    try {
            // Connect to database using EventPersister, which is based on IRISDataSource
	        EventPersister xepPersister = PersisterFactory.createPersister();

	        // Connecting to database
	        xepPersister.connect("localhost", 51773, "user", "superuser", "SYS");
			System.out.println("Connected to InterSystems IRIS.");

	        xepPersister.deleteExtent("User.TEST2");
	        xepPersister.importSchema("User.TEST2");
	       
	        // Create Event
	        Event xepEvent = xepPersister.getEvent("User.TEST2");
	        TEST2 o = new TEST2();
	        o.id=BigDecimal.valueOf(123);
	        o.name="テスト";

	        xepEvent.store(o);
	        
	        PreparedStatement stmt = xepPersister.prepareStatement("select * from TEST2");
	        ResultSet rs = stmt.executeQuery();
			while (rs.next()) 
			{
				String n=rs.getString("name");
				System.out.println(rs.getBigDecimal("id") + " " + n);
	        }	        
	
	        xepEvent.close();
	        xepPersister.close();
		} catch (XEPException e) { 
			System.out.println("Interactive prompt failed:\n" + e); 
		}
	   } 

} 