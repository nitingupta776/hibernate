import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator; 
 
import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;

public class ManageCustomer {
   private static SessionFactory factory; 
   public static void main(String[] args) throws IOException {
      
      try {
         factory = new AnnotationConfiguration().
                   configure().
                   //addPackage("com.xyz") //add package if used.
                   addAnnotatedClass(Customer.class).
                   buildSessionFactory();
      } catch (Throwable ex) { 
         System.err.println("Failed to create sessionFactory object." + ex);
         throw new ExceptionInInitializerError(ex); 
      }
      
      ManageCustomer MC = new ManageCustomer();

      String photoFilePath = "/Users/bhavukgupta/Desktop/car.jpg";
      byte[] photoBytes = readBytesFromFile(photoFilePath);
      /* Add few customer records in database */
      Integer customerID1 = MC.addCustomer(1, "Zara", "Vancouver", new Date(), photoBytes);
      Integer customerID2 = MC.addCustomer(2, "Tara", "Burnaby", new Date(), photoBytes);
      Integer customerID3 = MC.addCustomer(3, "Sam", "Victoria", new Date(), photoBytes);

      /* List down all the employees */
      MC.listCustomers();

      /* Update customer's records */
      MC.updateCustomer(customerID1, "Surrey");
      MC.updateCustomer(customerID3, "Richmond");

      /* Delete an customer from the database */
      MC.deleteCustomer(customerID2);

      /* List down new list of the customers */
      MC.listCustomers();
   }
 
    
   private static byte[] readBytesFromFile(String filePath) throws IOException {
       File inputFile = new File(filePath);
       FileInputStream inputStream = new FileInputStream(inputFile);
        
       byte[] fileBytes = new byte[(int) inputFile.length()];
       inputStream.read(fileBytes);
       inputStream.close();
        
       return fileBytes;
   }
    
   /* Method to CREATE an customer in the database */
   public Integer addCustomer(int id, String name, String address, Date birthdate, byte[] picture){
      Session session = factory.openSession();
      Transaction tx = null;
      Integer customerID = null;
      
      try {
         tx = session.beginTransaction();
         Customer customer = new Customer();
         customer.setId(id);
         customer.setName(name);
         customer.setAddress(address);
         customer.setBirthDate(birthdate);
         customer.setPicture(picture);
         customerID = (Integer) session.save(customer); 
         tx.commit();
      } catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      }
      return customerID;
   }
   
   /* Method to  READ all the customers */
   public void listCustomers( ) throws IOException{
      Session session = factory.openSession();
      Transaction tx = null;
      
      try {
         tx = session.beginTransaction();
         List customers = session.createQuery("FROM Customer").list(); 
         for (Iterator iterator = customers.iterator(); iterator.hasNext();){
            Customer customer = (Customer) iterator.next(); 
            System.out.println("Name: " + customer.getName()); 
            System.out.println("Address: " + customer.getAddress()); 
            System.out.println("Birth Date: " + customer.getBirthDate().toString()); 
            System.out.println("Picture: " + customer.getPicture()); 
            byte[] photoBytes = customer.getPicture();
            String photoFilePath = "/Users/bhavukgupta/Desktop/test.png";
            saveBytesToFile(photoFilePath, photoBytes);
         }
         tx.commit();
      } catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      }
   }
   
   /* Method to UPDATE address for a customer */
   public void updateCustomer(Integer CustomerID, String address ){
      Session session = factory.openSession();
      Transaction tx = null;
      
      try {
         tx = session.beginTransaction();
         Customer customer = (Customer)session.get(Customer.class, CustomerID); 
         customer.setAddress( address );
		 session.update(customer); 
         tx.commit();
      } catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      }
   }
   
   /* Method to DELETE an customer from the records */
   public void deleteCustomer(Integer CustomerID){
      Session session = factory.openSession();
      Transaction tx = null;
      
      try {
         tx = session.beginTransaction();
         Customer customer = (Customer)session.get(Customer.class, CustomerID); 
         session.delete(customer); 
         tx.commit();
      } catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      }
   }
   
   private static void saveBytesToFile(String filePath, byte[] fileBytes) throws IOException {
       FileOutputStream outputStream = new FileOutputStream(filePath);
       outputStream.write(fileBytes);
       outputStream.close();
   }
}