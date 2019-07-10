package database;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripperByArea;
public class ExtractByArea {
	
 	public ExtractByArea() {
		  
	}

	public void extract() {
		PDDocument document ;
        File f=new File("D:\\sample1.pdf");
        Connection conn=null;
        try {
			document=PDDocument.load(f);
			PDPage page=document.getPage(0);
	      //  PDFTextStripper t=new PDFTextStripper();
	        PDFTextStripperByArea stripper = new PDFTextStripperByArea();
	        stripper.setSortByPosition( true );
	        Rectangle rect1 = new Rectangle(52,136,38,4);
	        stripper.addRegion( "class1", rect1 );
	        stripper.extractRegions( page );
	        String invoiceNo = stripper.getTextForRegion( "class1" ); 
	        System.out.println("Invoice no "+invoiceNo);
	        Rectangle rect2 = new Rectangle(180,136,30,4);
	        stripper.addRegion( "class1", rect2 );
	        stripper.extractRegions( page );
	        String invoiceDate = stripper.getTextForRegion( "class1" );
	        try {
				Date date=new SimpleDateFormat("dd/MM/yyyy").parse(invoiceDate);
				System.out.println(date);
			} catch (ParseException e) {
 				e.printStackTrace();
			}  
	        System.out.println("Invoice date "+invoiceDate);
	        Rectangle rect3 = new Rectangle(290,154,38,4);
	        stripper.addRegion( "class1", rect3 );
	        stripper.extractRegions( page );
	        String Po = stripper.getTextForRegion( "class1" );
 	        System.out.println("PO "+Po);
	        Rectangle rect4 = new Rectangle(52,181,140,25);
	        stripper.addRegion( "class1", rect4 );
	        stripper.extractRegions( page );
	        String address = stripper.getTextForRegion( "class1" );
	        System.out.println("Address "+address);
	        Rectangle rect5 = new Rectangle(563,408,40,25);
	        stripper.addRegion( "class1", rect5 );
	        stripper.extractRegions( page );
	        String amount = stripper.getTextForRegion( "class1" );
	        System.out.println("Amount "+amount);
	       
			try
	        {
	           Class.forName("com.mysql.jdbc.Driver"); //creating class object
				conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice2","root","root"); 
				System.out.println(conn);
				String query = "insert into invoicedetails2(invoiceNo,invoiceDate,Po,address,amount,status)"+ " values (?, ?, ?, ?, ?, ?)";
	          PreparedStatement preparedStmt = conn.prepareStatement(query);
	          preparedStmt.setString(1,invoiceNo);
	          preparedStmt.setString(2,invoiceDate);
	          preparedStmt.setString(3,Po);
	          preparedStmt.setString(4,address);
	          preparedStmt.setString(5,amount);
	          preparedStmt.setString(6,"not approved");
	          
	          preparedStmt.execute();
	          System.out.println("inserted");
	          System.out.println("Does the invoice has to be approved?");
	          Scanner sc=new Scanner(System.in);
	          String approval=sc.nextLine();
	          if(approval.equals("yes")){
	        	  String updatequery = "update invoicedetails2 set status ='approved'";
	        	  PreparedStatement preparedStmt1 = conn.prepareStatement(updatequery);
		          preparedStmt1.executeUpdate();
	          }
	        
	        }
	        catch (Exception e)
	        {
	          System.err.println("Got an exception!");
	          System.err.println(e.getMessage());
	        }
		} catch (InvalidPasswordException e) {
			 e.printStackTrace();
		} catch (IOException e) {
			 e.printStackTrace();
		}finally{
			
			  try {
				conn.close();
			} catch (SQLException e) {
			 	e.printStackTrace();
			}
			
		}
              
	}

}
