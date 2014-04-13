import java.sql.DriverManager;
import java.sql.*;
import java.sql.SQLException;
import java.util.*;
import java.io.*;

 
public class populate  {
    
    public static Connection con;
    public static Statement stat;
    public static String s0,s1,s2;
    public Connection connect()
    {
		System.out.println("-------- Oracle JDBC Connection Testing ------");
		try {
 
			Class.forName("oracle.jdbc.driver.OracleDriver");
 
		} catch (ClassNotFoundException e) {
 
			System.out.println("Where is your Oracle JDBC Driver?");
			e.printStackTrace();
			return null;
		}
 
		System.out.println("Oracle JDBC Driver Registered!");
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:ORCL", "SYSTEM",
					"Dadun1234");
 
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
                        return null;
                    }
 
		if (connection != null) {
			System.out.println("You made it, take control your database now!");
                        return connection;
		} else {
			System.out.println("Failed to make connection!");
                        return null;
		}
    }
 
	public static void main(String[] args) {
           populate p=new populate();
           con = p.connect();
           p.truncate();
        }
        
        
        public void truncate()
        {
            try{
            stat= con.createStatement();
            stat.execute("truncate table buildings");
            }
            catch(Exception e)
            {
                System.out.println("could not truncate buildings");
            }
            
            
            try{
            stat= con.createStatement();
            stat.execute("truncate table students");
            }
            catch(Exception e)
            {
                System.out.println("could not truncate students");
            }
            
            
            try{
            stat= con.createStatement();
            stat.execute("truncate table asystem");
            }
            catch(Exception e)
            {
                System.out.println("could not truncate asystem");
            }
            
           
            //BUILDINGS
              try{
              BufferedReader inputStream1 = new BufferedReader(new FileReader("//C:\\\\Users\\\\Sinchan\\\\Documents\\\\NetBeansProjects\\\\Database\\\\src\\\\buildings.xy"));  
              String building;
              int i;
            
              while ((building = inputStream1.readLine()) != null) {
                String data[] = building.split(", ");
                try{
                  stat= con.createStatement();
                  String sql= "";
                  String ordinate="";
                  sql="insert into buildings values"+"('"+data[0]+"','"+data[1]+"',"+data[2]+",SDO_GEOMETRY(2003,NULL,NULL,SDO_ELEM_INFO_ARRAY(1,1003,1),SDO_ORDINATE_ARRAY(";
                   for(int k=3;k<2*Integer.parseInt(data[2])+3;k++)
                      ordinate = ordinate + data[k]+",";
                   ordinate=ordinate.substring(0,ordinate.length()-1);
                  // System.out.println(sql+ordinate+")))");
                   sql=sql+ordinate+")))";
                   stat.execute(sql);
           }
                catch(Exception e)
                {
                   System.out.println("query not executed");
                }
                for( i=0;i<data.length;i++)
                {
                    System.out.print(data[i]+" ");
                }
                
               System.out.println();
                
                
        }
                }
                catch(IOException e)
                {
                    System.out.println("buildings problem");
                }
            
            //students
            try{
              BufferedReader inputStream2 = new BufferedReader(new FileReader("C:\\Users\\Sinchan\\Documents\\NetBeansProjects\\Database\\src\\students.xy"));  
              String m;
             while( (m = inputStream2.readLine())!=null)
             {
              String data1[]=m.split(", ");
              System.out.println(data1[0]+" "+data1[1]);
              String sql1="insert into students values('"+data1[0]+"',SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE("+Integer.parseInt(data1[1])+","+Integer.parseInt(data1[2])+",NULL),NULL,NULL))";
              
              stat= con.createStatement();
              stat.execute(sql1);
             }
        }
             catch(Exception e)
             {
             System.out.println("students");
             }
             
            //announcement
            try{
             BufferedReader inputStream3 = new BufferedReader(new FileReader("C:\\Users\\Sinchan\\Documents\\NetBeansProjects\\Database\\src\\announcementSystems.xy"));  
             String n;
             while((n=inputStream3.readLine())!=null)
             {
            // System.out.println(n);
             String[] data2=n.split(", ");
             System.out.print(data2[0] + " " + data2[1] + " " + data2[2] + " " + data2[3]);
             System.out.println();
             String sql2="insert into asystem values('"+data2[0]+"',SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE("+Integer.parseInt(data2[1])+","+Integer.parseInt(data2[2])+",NULL),NULL,NULL),"+Integer.parseInt(data2[3])+",SDO_GEOMETRY(2003,NULL,NULL,SDO_ELEM_INFO_ARRAY(1,1003,4),SDO_ORDINATE_ARRAY("+Integer.parseInt(data2[1])+","+(Integer.parseInt(data2[2])-Integer.parseInt(data2[3]))+","+(Integer.parseInt(data2[1])+Integer.parseInt(data2[3]))+","+Integer.parseInt(data2[2])+","+ Integer.parseInt(data2[1])+","+(Integer.parseInt(data2[2])+Integer.parseInt(data2[3]))+")))";
             //System.out.println(sql2);
             stat= con.createStatement();
             stat.execute(sql2);
             }

             }
             catch(Exception e)
             {System.out.println("announcement problem");
             }
        }
}