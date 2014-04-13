import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ButtonGroup;
import java.lang.Object;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import oracle.spatial.geometry.JGeometry;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.SQLException;
import javax.swing.SwingUtilities;
import oracle.sql.STRUCT;

public class NewJFrame extends javax.swing.JFrame {
    populate pop;
    Connection con;
    int count = 1;
    Statement s;
    ResultSet rs;
    int x;
    int y;
    int r;
    String rstring="";
    int highx;
    int highy;
    int highr;
    int[] polyx=new int[50];
    int[] polyy=new int[50];
    int rcount=0;
    String Broken;
    String str;
    int px,py;
    int pointx;
    int pointy;
    String[] query=new String[50];
    String mainq=new String();
    
    
    
    public NewJFrame() {
        this.pop = new populate();

       initComponents();
   
       groupButton();
       con=pop.connect();
       //jTextField1.setText("");
       if(con==null)
       {
           System.out.println("OOPS Something went wrong");
       }
       count=0;
       
    }
    
     //------------------------------------WHOLE STUDENTS:---------------------------------------------------------
    public void allstudents(){
    try{
            s=con.createStatement();
            //rs =  s.executeQuery("select * from students");//------------------returns true/false
            rs = s.executeQuery("SELECT scoordinates from STUDENTS");
            query[count]="SELECT scoordinates from STUDENTS";
            count++;
            mainq = mainq + "\nQuery: "+ count+ " "+ query[--count] + " ";
           Graphics2D gd = (Graphics2D) jLabel2.getGraphics();
           gd.setColor(Color.yellow);
           while(rs.next()){
            STRUCT st1;
            
              st1 = (oracle.sql.STRUCT) rs.getObject(1);
              JGeometry jgeom = JGeometry.load(st1);
              double[] point=jgeom.getPoint();
               x=(int) point[0];
               y=(int) point[1];
              gd.fillRect(x, y, 10, 10);
               }
    }
        catch(SQLException e)
        {
            System.out.print("Statement problem");
        }
    }
    
        //WHOLE ASYSTEM
        public void allasystem()
        {
        try{
            s=con.createStatement();
            ResultSet rs1 = s.executeQuery("SELECT position,radius, as_id from ASYSTEM");
            query[count]="SELECT position,radius from ASYSTEM";
            count++;
            mainq = mainq + "\nQuery: " + count + " "+query[--count] + " ";
            
             STRUCT st2;
             Graphics2D gd=(Graphics2D) jLabel2.getGraphics();
             gd.setColor(Color.red);
             while(rs1.next()){
              st2 = (oracle.sql.STRUCT) rs1.getObject(1);
              r = rs1.getInt(2);
              JGeometry jgeom = JGeometry.load(st2);
              double[] d = jgeom.getPoint();
             str = rs1.getString(3);
             Color c;
             c=getcolor(str);
             gd.setColor(c);
               x=(int) d[0];
               y=(int) d[1];
              gd.fillRect(x, y, 15, 15);
              gd.drawOval(x-r, y-r,r*2, r*2);
              }
    }
        catch(Exception e)
        {
                 System.out.print("Statement problem");
            e.printStackTrace();
       
        }        
    }
    
    //WHOLE BUILDINGS
    public void allbuildings()
    {
      int a[]= new int[50];
      int b[]=new int[50];
      int vertices;
      double[] d=new double[50];
        try{
            s=con.createStatement();
            ResultSet rs2 = s.executeQuery("SELECT coordinates,vertices from BUILDINGS");
            query[count]="SELECT coordinates,vertices from BUILDINGS";
            count++;
            mainq = mainq + "\nQuery: " + count + " " + query[--count] + " ";
             STRUCT st3;
             Graphics2D gd=(Graphics2D) jLabel2.getGraphics();
             gd.setColor(Color.yellow);
             while(rs2.next()){
              st3 = (oracle.sql.STRUCT) rs2.getObject(1);
              vertices = rs2.getInt(2);
              JGeometry jgeom = JGeometry.load(st3);
               d = jgeom.getOrdinatesArray();
               int j = 0;
               int k = 0;
               for(int i=0;i<d.length;i++)
               {
                   if(i%2==0)
                   {
                       a[j]=(int)d[i];
                       j++;
                   }
               }
                for(int i=0;i<d.length;i++)
               {
                   if(i%2!=0)
                   {
                       b[k]=(int)d[i];
                       k++;
                   }
               }
               gd.drawPolyline(a, b, vertices);
                }  
               }
          catch(Exception e)
        {
            e.printStackTrace();
            System.out.print("Statement problem");
        }
    }
    
    //--------------------------------------------POINT QUERY------------------------------------------------
    public void pointquery(int x,int y)
    {
        px=x;
        py=y;
        Graphics2D gd= (Graphics2D) jLabel2.getGraphics();
        gd.setColor(Color.red);
        gd.fillRect(px, py, 5, 5);
        gd.drawOval(px-50, py-50, 100, 100);
        r=50;
    }
    public void afterpoint()
    {
           Graphics2D gd=(Graphics2D) jLabel2.getGraphics();
           STRUCT st1; 
           
       //students
        try{
        s=con.createStatement();
        ResultSet rs2 = s.executeQuery("SELECT c.person_id ,c.scoordinates FROM students c WHERE SDO_INSIDE(c.scoordinates, SDO_GEOMETRY(2003, NULL, NULL,SDO_ELEM_INFO_ARRAY(1,1003,4),SDO_ORDINATE_ARRAY("+px+","+(py+r)+","+(px+r)+","+py+","+px+","+(py-r)+"))) = 'TRUE'");
        query[count]="SELECT c.person_id ,c.scoordinates FROM students c WHERE SDO_INSIDE(c.scoordinates, SDO_GEOMETRY(2003, NULL, NULL,SDO_ELEM_INFO_ARRAY(1,1003,4),SDO_ORDINATE_ARRAY("+px+","+(py+r)+","+(px+r)+","+py+","+px+","+(py-r)+"))) = 'TRUE'";
            count++;
            mainq = mainq+"\nQuery: "+count+" "+query[--count];
            // rs2.next();
            gd.setColor(Color.green);
              while(rs2.next()){
           
              st1 = (oracle.sql.STRUCT) rs2.getObject(2);
              JGeometry jgeom = JGeometry.load(st1);
              double[] d=jgeom.getPoint();
              x=(int) d[0];
              y=(int) d[1];
              gd.fillRect(x, y, 10, 10);
            }
        }
        catch(Exception e)
        {
            System.out.println("Statement Problem");
            e.printStackTrace();
        }
 
        //asystem
        try{
            s=con.createStatement();
            ResultSet rs1 = s.executeQuery("SELECT c.position,c.as_id FROM asystem c WHERE SDO_INSIDE(c.position, SDO_GEOMETRY(2003, NULL, NULL,SDO_ELEM_INFO_ARRAY(1,1003,4),SDO_ORDINATE_ARRAY("+px+","+(py+r)+","+(px+r)+","+py+","+px+","+(py-r)+"))) = 'TRUE'");
            query[count]="SELECT c.position,c.as_id FROM asystem c WHERE SDO_INSIDE(c.position, SDO_GEOMETRY(2003, NULL, NULL,SDO_ELEM_INFO_ARRAY(1,1003,4),SDO_ORDINATE_ARRAY("+px+","+(py+r)+","+(px+r)+","+py+","+px+","+(py-r)+"))) = 'TRUE'";
            count++;
            mainq = mainq+"\nQuery: "+count+" "+query[--count];
            
             STRUCT st2;
             gd.setColor(Color.magenta);
             while(rs1.next()){
             st2 = (oracle.sql.STRUCT) rs1.getObject(1);
              JGeometry jgeom = JGeometry.load(st2);
              double[] d=jgeom.getPoint();
             // System.out.println(d[0]+" "+d[1]);
               x=(int) d[0];
               y=(int) d[1];
              gd.fillRect(x, y, 15, 15);
              }
       }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.print("Statement problem");
        }
        
        //buildings
        int a[]= new int[50];
        int b[]=new int[50];
        int vertices;
        double[] d=new double[50];
        try{
            s=con.createStatement();
           ResultSet rs3 = s.executeQuery("SELECT c.coordinates,c.vertices FROM buildings c WHERE SDO_ANYINTERACT(c.coordinates, SDO_GEOMETRY(2003, NULL, NULL,SDO_ELEM_INFO_ARRAY(1,1003,4),SDO_ORDINATE_ARRAY("+px+","+(py+r)+","+(px+r)+","+py+","+px+","+(py-r)+"))) = 'TRUE'");
            query[count]="SELECT c.coordinates,c.vertices FROM buildings c WHERE SDO_ANYINTERACT(c.coordinates, SDO_GEOMETRY(2003, NULL, NULL,SDO_ELEM_INFO_ARRAY(1,1003,4),SDO_ORDINATE_ARRAY("+px+","+(py+r)+","+(px+r)+","+py+","+px+","+(py-r)+"))) = 'TRUE'";
            count++;
            mainq = mainq+"\nQuery: "+count+" "+query[--count];
            
             STRUCT st3;
             
             gd.setColor(Color.yellow);
             while(rs3.next()){
           
              st3 = (oracle.sql.STRUCT) rs3.getObject(1);
              vertices=rs3.getInt(2);
             
             // System.out.print("d");
              JGeometry jgeom = JGeometry.load(st3);
               d=jgeom.getOrdinatesArray();
               
               for(int j=0,i=0;i<d.length;i++)
               {
                   if(i%2==0)
                   {
                       a[j]=(int)d[i];
                       j++;
                   }
               }
                for(int k=0,i=0;i<d.length;i++)
               {
                   if(i%2!=0)
                   {
                       b[k]=(int)d[i];
                       k++;
                   }
               }
               gd.drawPolyline(a, b, vertices);
               }  
            
               }
          catch(Exception e)
        {
            e.printStackTrace();
            System.out.print("Statement problem");
        }
    } 
    
    //----------------------------------------------RANGE QUERY-----------------------------------------------
    //Draw Polygon for Range Query
     public void polygon(int irx,int iry)
         {
         Graphics2D gd=(Graphics2D) jLabel2.getGraphics();
         gd.setColor(Color.orange);
         gd.fillRect(irx, iry, 5, 5);
         polyx[rcount]=irx;
         polyy[rcount]=iry;
         System.out.println(polyx[rcount]+" "+polyy[rcount]);
         rstring+=polyx[rcount]+","+polyy[rcount]+",";
         if(rcount>0)
         {
             gd.drawLine(polyx[rcount-1], polyy[rcount-1], polyx[rcount], polyy[rcount]);
         }
         rcount++;
        
    }
        public void rquery()
          {
        //STUDENTS
        Graphics2D gd=(Graphics2D) jLabel2.getGraphics();
        try{
            s=con.createStatement();
            rstring=rstring + "," + polyx[0] + "," + polyy[0];
            ResultSet rs2 = s.executeQuery("SELECT c.person_id ,c.scoordinates FROM students c WHERE SDO_INSIDE(c.scoordinates,SDO_GEOMETRY(2003, NULL, NULL,SDO_ELEM_INFO_ARRAY(1,1003,1),SDO_ORDINATE_ARRAY("+rstring+"))) = 'TRUE'");
            query[count]="SELECT c.person_id ,c.scoordinates FROM students c WHERE SDO_INSIDE(c.scoordinates,SDO_GEOMETRY(2003, NULL, NULL,SDO_ELEM_INFO_ARRAY(1,1003,1),SDO_ORDINATE_ARRAY("+rstring+"))) = 'TRUE'";
            count++;
            STRUCT st1;
            gd.setColor(Color.green);
            while(rs2.next()){
              st1 = (oracle.sql.STRUCT) rs2.getObject(2);
              JGeometry jgeom = JGeometry.load(st1);
              double[] d=jgeom.getPoint();
               x=(int) d[0];
               y=(int) d[1];
              gd.fillRect(x, y, 10, 10);
            }
        }
        catch(Exception e)
        {
            System.out.println("Students' range query");
            e.printStackTrace();
        }
        //ASYSTEM
        try{
            s=con.createStatement();
            ResultSet rs1 = s.executeQuery("SELECT c.position ,c.radius FROM asystem c WHERE SDO_INSIDE(c.position,SDO_GEOMETRY(2003, NULL, NULL,SDO_ELEM_INFO_ARRAY(1,1003,1),SDO_ORDINATE_ARRAY("+rstring+"))) = 'TRUE'");
            query[count]="SELECT c.position ,c.radius FROM asystem c WHERE SDO_INSIDE(c.position,SDO_GEOMETRY(2003, NULL, NULL,SDO_ELEM_INFO_ARRAY(1,1003,1),SDO_ORDINATE_ARRAY("+rstring+"))) = 'TRUE'";
            count++;
            mainq = mainq+"\nQuery: "+count+" "+query[--count];
            
             STRUCT st2;
             gd.setColor(Color.red);
             while(rs1.next()){
           
              st2 = (oracle.sql.STRUCT) rs1.getObject(1);
              r=rs1.getInt(2);
              JGeometry jgeom = JGeometry.load(st2);
              double[] d=jgeom.getPoint();
              x=(int) d[0];
              y=(int) d[1];
              gd.fillRect(x, y, 15, 15);
              //gd.drawOval(x-r, y-r,r*2, r*2);
               }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("Statement problem in asystem");
        }
        //BUILDINGS
        int a[]= new int[50];
        int b[]=new int[50];
        int vertices;
        double[] d=new double[50];
        try{
            s=con.createStatement();
            ResultSet rs2 = s.executeQuery("SELECT c.coordinates ,c.vertices FROM buildings c WHERE SDO_ANYINTERACT(c.coordinates,SDO_GEOMETRY(2003, NULL, NULL,SDO_ELEM_INFO_ARRAY(1,1003,1),SDO_ORDINATE_ARRAY("+rstring+"))) = 'TRUE'");
            query[count]="SELECT c.coordinates ,c.vertices FROM buildings c WHERE SDO_ANYINTERACT(c.coordinates,SDO_GEOMETRY(2003, NULL, NULL,SDO_ELEM_INFO_ARRAY(1,1003,1),SDO_ORDINATE_ARRAY("+rstring+"))) = 'TRUE'";
            count++;
            mainq = mainq+"\nQuery: "+count+" "+query[--count];
            STRUCT st3;
            gd.setColor(Color.blue);
            while(rs2.next()){
            st3 = (oracle.sql.STRUCT) rs2.getObject(1);
            vertices=rs2.getInt(2);
            JGeometry jgeom = JGeometry.load(st3);
            d=jgeom.getOrdinatesArray();
            int j = 0;
            int k = 0;
            for(int i=0;i<d.length;i++)
               {
                   if(i%2==0)
                   {
                       a[j]=(int)d[i];
                       j++;
                   }
               }
                for(int i=0;i<d.length;i++)
               {
                   if(i%2!=0)
                   {
                       b[k]=(int)d[i];
                       k++;
                   }
               }
               gd.drawPolyline(a, b, vertices);
           }  
            
     }
          catch(Exception e)
        {
            e.printStackTrace();
            System.out.println(" Building Statement problem");
        }
     
        rcount=0;
        rstring="";
        for(int i=0;i<polyx.length;i++)
        {
            polyx[i]=0;
            polyy[i]=0;
        }
    }
    
     ///------------------------------------SURROUNDING STUDENTS'S QUERY---------------------------------------
    public void highlight(int hx,int hy)
    {
        Graphics2D gd=(Graphics2D) jLabel2.getGraphics();
        gd.setColor(Color.yellow);
        gd.fillRect(hx, hy, 5, 5);
         try{
             s=con.createStatement();
            ResultSet rs1 = s.executeQuery("SELECT c.position,c.radius  FROM asystem c  WHERE SDO_NN(c.position,sdo_geometry(2001, NULL, sdo_point_type("+hx+","+hy+",NULL), NULL,NULL),  'sdo_num_res=1') = 'TRUE'");
            query[count]="SELECT c.position,c.radius  FROM asystem c  WHERE SDO_NN(c.position,sdo_geometry(2001, NULL, sdo_point_type("+hx+","+hy+",NULL), NULL,NULL),  'sdo_num_res=1') = 'TRUE'";
            count++;
            mainq = mainq+"\nQuery: "+count+" "+query[--count];
            
             STRUCT st2;
           
             gd.setColor(Color.red);
             while(rs1.next()){
           
              st2 = (oracle.sql.STRUCT) rs1.getObject(1);
              r=rs1.getInt(2);
              JGeometry jgeom = JGeometry.load(st2);
              double[] d=jgeom.getPoint();
               x=(int) d[0];
               y=(int) d[1];
              gd.fillRect(x, y, 15, 15);
              gd.drawOval(x-r, y-r,r*2, r*2);
               highx=x;
               highy=y;
               highr=r;
               }
         }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.print("Statement problem");
        } 
      }
    
    public void surrounding(){
        Graphics2D gd= (Graphics2D) jLabel2.getGraphics();
        gd.setColor(Color.red);
       //students
        try{
        s=con.createStatement();
       ResultSet rs2 = s.executeQuery("SELECT c.person_id ,c.scoordinates FROM students c WHERE SDO_INSIDE(c.scoordinates, SDO_GEOMETRY(2003, NULL, NULL,SDO_ELEM_INFO_ARRAY(1,1003,4),SDO_ORDINATE_ARRAY("+highx+","+(highy+highr)+","+(highx+highr)+","+highy+","+highx+","+(highy-highr)+"))) = 'TRUE'");
        query[count]="SELECT c.person_id ,c.scoordinates FROM students c WHERE SDO_INSIDE(c.scoordinates, SDO_GEOMETRY(2003, NULL, NULL,SDO_ELEM_INFO_ARRAY(1,1003,4),SDO_ORDINATE_ARRAY("+highx+","+(highy+highr)+","+(highx+highr)+","+highy+","+highx+","+(highy-highr)+"))) = 'TRUE'";
            count++;
           mainq = mainq+"\nQuery: "+count+" "+query[--count];
         gd.setColor(new Color(1,255,1,255));
             
             STRUCT st1;
             while(rs2.next()){
           
              st1 = (oracle.sql.STRUCT) rs2.getObject(2);
              JGeometry jgeom = JGeometry.load(st1);
              double[] d=jgeom.getPoint();
               x=(int) d[0];
               y=(int) d[1];
              gd.fillRect(x, y, 10, 10);
            }
        }
        catch(Exception e)
        {
            System.out.println("Surrounding Students Statement error");
            e.printStackTrace();
        }
        
    }
    
    
   //-------------------------------------------EMERGENCY QUERY---------------------------------------------- 
    public void highlight2(int emx,int emy)
    {
        Graphics2D gd=(Graphics2D) jLabel2.getGraphics();
        gd.setColor(Color.yellow);
        gd.fillRect(emx, emy, 5, 5);
         try{
             s=con.createStatement();
            ResultSet rs1 = s.executeQuery("SELECT c.position, c.radius, c.as_id  FROM asystem c  WHERE SDO_NN(c.position,sdo_geometry(2001, NULL, sdo_point_type("+emx+","+emy+",NULL), NULL,NULL),  'sdo_num_res=1') = 'TRUE'");
            query[count]="SELECT c.position,c.radius, c.as_id  FROM asystem c  WHERE SDO_NN(c.position,sdo_geometry(2001, NULL, sdo_point_type("+emx+","+emy+",NULL), NULL,NULL),  'sdo_num_res=1') = 'TRUE'";
            count++;
            mainq = mainq+"\nQuery: "+count+" "+query[--count];
            
             STRUCT st2;
             gd.setColor(Color.red);
             while(rs1.next()){
              st2 = (oracle.sql.STRUCT) rs1.getObject(1);
              r=rs1.getInt(2);
              JGeometry jgeom = JGeometry.load(st2);
              double[] d=jgeom.getPoint();
              x=(int) d[0];
              y=(int) d[1];
              Broken =rs1.getString(3);
              gd.setColor(getcolor(Broken));
              System.out.println("ASYSTEM AT " + x + " " + y + " IS BROKEN and its name is : " + Broken);
              gd.fillRect(x, y, 15, 15);
              gd.drawOval(x-r, y-r,r*2, r*2);
              highx=x;
              highy=y;
              highr=r;
               }
         }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.print("Statement problem");
        } 
      }
    
    public Color getcolor(String s)
    {
        
    if(s.equals("a1psa"))
        return Color.LIGHT_GRAY;
    if(s.equals("a3sgm"))
        return Color.CYAN;
    if(s.equals("a4hnb"))
        return Color.MAGENTA;
    if(s.equals("a5vhe"))
        return Color.RED;
    if(s.equals("a6ssc"))
        return Color.PINK;
    if(s.equals("a7helen"))
        return Color.GREEN;
    if(s.equals("a2ohe"))
        return Color.BLUE;
    else
        return new Color(145,56,87,255);
    
    }
    public void emergency(){
        Graphics2D gd= (Graphics2D) jLabel2.getGraphics();
        gd.setColor(Color.red);
        try{
        s=con.createStatement();
       ResultSet rs2 = s.executeQuery("SELECT * FROM students c WHERE SDO_INSIDE(c.scoordinates, SDO_GEOMETRY(2003, NULL, NULL,SDO_ELEM_INFO_ARRAY(1,1003,4),SDO_ORDINATE_ARRAY("+highx+","+(highy+highr)+","+(highx+highr)+","+highy+","+highx+","+(highy-highr)+"))) = 'TRUE'");
       query[count]="SELECT * FROM students c WHERE SDO_INSIDE(c.scoordinates, SDO_GEOMETRY(2003, NULL, NULL,SDO_ELEM_INFO_ARRAY(1,1003,4),SDO_ORDINATE_ARRAY("+highx+","+(highy+highr)+","+(highx+highr)+","+highy+","+highx+","+(highy-highr)+"))) = 'TRUE'";
            count++;
           mainq = mainq+"\nQuery: "+count+" "+query[--count]; 
          int ex[]=new int[50];
          int ey[]=new int[50];
          int ecount=0;
             while(rs2.next())
             {
              STRUCT st1;   
              st1 = (oracle.sql.STRUCT) rs2.getObject(2);
              JGeometry jgeom = JGeometry.load(st1);
              double[] d=jgeom.getPoint();
              ex[ecount]=(int) d[0];
              ey[ecount]=(int) d[1];
              gd.fillRect(ex[ecount], ey[ecount], 10, 10);
              ecount++;
             }
             ecount=0;
            for(int i=0;i<ex.length;i++)
             {
             if(ex[i]==0 && ey[i]==0)
             {
             break;
             }
             else
             {
             rs = s.executeQuery("SELECT c.position,c.radius,c.as_id  FROM asystem c  WHERE SDO_NN(c.position,sdo_geometry(2001, NULL, sdo_point_type("+ex[i]+","+ey[i]+",NULL), NULL,NULL),  'sdo_num_res=2') = 'TRUE'");
             query[count]="SELECT c.position,c.radius,c.as_id  FROM asystem c  WHERE SDO_NN(c.position,sdo_geometry(2001, NULL, sdo_point_type("+ex[i]+","+ey[i]+",NULL), NULL,NULL),  'sdo_num_res=2') = 'TRUE'";
             count++;
             mainq = mainq+"\nQuery: "+count+" "+query[--count]; 
             int next=0;
             String newas="";
             rs.next();
             int previousint=rs.getInt(2);
             String previousString=rs.getString(3);
             //rs.next();
             while(rs.next())
             {
             next=rs.getInt(2); 
             newas=rs.getString(3);
             }
             if(Broken.equals(newas))
             {
                 newas=previousString;
             }
             Color c;
             c=getcolor(newas);
             System.out.println(newas+" "+next+" "+ previousint+" ");
             gd.setColor(c);
             gd.fillRect(ex[i],ey[i], 10, 10);
             int a[]= new int[50];
             int b[]=new int[50];
             int vertices;
             int m,n;
             double[] d=new double[50];
            String check="SELECT position,radius from asystem WHERE as_id='"+newas+"'";
            System.out.println(check);
             try{
             s=con.createStatement();
            // s.executeQuery("select * from students");
            newas=newas.trim();
            ResultSet rs1 = s.executeQuery("SELECT position,radius from asystem WHERE as_id='"+newas+"'");
            
            query[count]="SELECT position,radius from asystem WHERE as_id='"+newas+"'";
            count++;
            mainq = mainq+"\nQuery: "+count+" "+query[--count];
            
             STRUCT st2;
             gd.setColor(c);
             while(rs1.next()){
           
              st2 = (oracle.sql.STRUCT) rs1.getObject(1);
              r=rs1.getInt(2);
              JGeometry jgeom = JGeometry.load(st2);
               d=jgeom.getPoint();
             //System.out.println(d[0]+" "+d[1]);
               m=(int) d[0];
               n=(int) d[1];
              gd.fillRect(m, n, 15, 15);
               gd.drawOval(m-r, n-r,r*2, r*2);
             }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.print("Statement problem");
        }
       } 
     }
    }
        catch(Exception e)
        {
            System.out.println("Emergency problem");
            e.printStackTrace();
        }
    }
   
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jRadioButton3 = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        jRadioButton3.setText("jRadioButton3");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(jRadioButton3)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jRadioButton3)
                .addContainerGap(214, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jLabel2.setIcon(new javax.swing.ImageIcon("C:\\Users\\Sinchan\\Desktop\\hw2\\map.jpg")); // NOI18N
        jLabel2.setText("jLabel2");
        jLabel2.setMaximumSize(new java.awt.Dimension(820, 580));
        jLabel2.setMinimumSize(new java.awt.Dimension(820, 580));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel2MousePressed(evt);
            }
        });
        jLabel2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel2MouseMoved(evt);
            }
        });
        getContentPane().add(jLabel2);
        jLabel2.setBounds(0, 30, 820, 580);

        jCheckBox1.setText("AS");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        getContentPane().add(jCheckBox1);
        jCheckBox1.setBounds(840, 40, 40, 23);

        jCheckBox2.setText("Buildings");
        getContentPane().add(jCheckBox2);
        jCheckBox2.setBounds(870, 70, 90, 23);

        jCheckBox3.setText("Students");
        getContentPane().add(jCheckBox3);
        jCheckBox3.setBounds(890, 40, 90, 23);

        jButton1.setText("Submit Query");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(840, 480, 110, 23);

        jButton2.setText("Clear");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(860, 520, 70, 23);

        jLabel3.setText("Sinchan Bhattacharya");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(20, 10, 160, 14);

        jTextField1.setText("jTextField1");
        getContentPane().add(jTextField1);
        jTextField1.setBounds(19, 640, 800, 30);

        jRadioButton1.setText("Whole Region");
        getContentPane().add(jRadioButton1);
        jRadioButton1.setBounds(840, 130, 130, 23);

        jRadioButton2.setText("Point Query");
        getContentPane().add(jRadioButton2);
        jRadioButton2.setBounds(840, 160, 110, 23);

        jRadioButton4.setText("Range Query");
        jRadioButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jRadioButton4);
        jRadioButton4.setBounds(840, 190, 120, 23);

        jRadioButton5.setText("Surrounding Students");
        getContentPane().add(jRadioButton5);
        jRadioButton5.setBounds(840, 220, 150, 23);

        jRadioButton6.setText("Emergency");
        jRadioButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton6ActionPerformed(evt);
            }
        });
        getContentPane().add(jRadioButton6);
        jRadioButton6.setBounds(840, 250, 120, 23);

        jLabel4.setText("    Mouse Coordinates:");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(840, 330, 140, 14);

        jLabel5.setText("jLabel5");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(870, 350, 80, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
       
              
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jRadioButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton4ActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
                jTextField1.setText(mainq); 
     if(jRadioButton1.isSelected())
              {
                  if(jCheckBox3.isSelected())
                      allstudents();
                  if(jCheckBox1.isSelected())
                       allasystem();
                  if(jCheckBox2.isSelected())
                       allbuildings();
              }
      if(jRadioButton2.isSelected())
            afterpoint();
      if(jRadioButton4.isSelected())
            rquery();
      if(jRadioButton5.isSelected())
       surrounding();
      if(jRadioButton6.isSelected())
          emergency();
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        jLabel2.repaint();
        jTextField1.setText(" ");
        mainq=" ";
    }//GEN-LAST:event_jButton2MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        // TODO add your handling code here
         if(jRadioButton2.isSelected())
             pointquery(evt.getX(),evt.getY());
         if(jRadioButton5.isSelected())
             highlight(evt.getX(),evt.getY());
         if(jRadioButton6.isSelected())
             highlight2(evt.getX(),evt.getY());

    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MousePressed
        // TODO add your handling code here:
        if(SwingUtilities.isRightMouseButton(evt)&&(jRadioButton4.isSelected()))
                {
                    Graphics2D gd=(Graphics2D) jLabel2.getGraphics();
                    gd.setColor(new Color(0,255,255,255));
                    gd.drawLine(polyx[0], polyy[0], polyx[rcount-1], polyy[rcount-1]);
                    rstring=rstring.substring(0,rstring.length()-1);
                }
                if(SwingUtilities.isLeftMouseButton(evt)&&(jRadioButton4.isSelected()))
                {
                 polygon(evt.getX(),evt.getY());
                    
                }
    }//GEN-LAST:event_jLabel2MousePressed

    private void jRadioButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton6ActionPerformed

    private void jLabel2MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseMoved
        // TODO add your handling code here:
       jLabel5.setText(Integer.toString(evt.getX())+", "+Integer.toString(evt.getY()));
    }//GEN-LAST:event_jLabel2MouseMoved
private void groupButton(){
    ButtonGroup button = new ButtonGroup();
    button.add(jRadioButton1);
    button.add(jRadioButton2);
    button.add(jRadioButton6);
    button.add(jRadioButton4);
    button.add(jRadioButton5);
}

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}