import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;

public class ManageCustomerServices {
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
      Transaction tx = null;
      try {
         Session session = factory.openSession();    
         Integer customerID = null; 
         tx = session.beginTransaction();
         Customer customer = new Customer();
         customer.setId(1);
         customer.setName("John");
         customer.setAddress("Vancouver");
         customer.setBirthDate(new Date());
         String photoFilePath = "/Users/bhavukgupta/Desktop/car.jpg";
         byte[] photoBytes = readBytesFromFile(photoFilePath);
         customer.setPicture(photoBytes);
         customerID = (Integer) session.save(customer);
         
         Service service1 = new Service();
         UUID uuid=UUID.randomUUID();
         service1.setUUId(uuid);
         service1.setServiceName("Massage");
         service1.setRate(100);
         service1.setCustomer(customer);
         session.save(service1);
         
         Service service2 = new Service();
         UUID uuid2=UUID.randomUUID();
         service2.setUUId(uuid2);
         service2.setServiceName("Meal");
         service2.setRate(50);
         service2.setCustomer(customer);
         session.save(service2);
         
         tx.commit();
         session.close(); 
      } catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      }
      try {
         Session session = factory.openSession();
         tx = session.beginTransaction();
         
         /* List of all the Customers and services offered to them */
         List customers = session.createQuery("FROM Customer").list(); 
         for (Iterator iterator = customers.iterator(); iterator.hasNext();){
            Customer customer_ = (Customer) iterator.next(); 
            System.out.println("Name: " + customer_.getName()); 
            System.out.println("Address: " + customer_.getAddress()); 
            System.out.println("Birth Date: " + customer_.getBirthDate().toString()); 
            System.out.println("Picture: " + customer_.getPicture()); 
            byte[] photoBytes = customer_.getPicture();
            String photoFilePath = "/Users/bhavukgupta/Desktop/test.png";
            saveBytesToFile(photoFilePath, photoBytes);

            Set<Service> services = customer_.getServices(); 
            System.out.println(" Size: " + services.size()); 
            for (Iterator<Service> it = services.iterator(); it.hasNext();) {
                Service service_ = (Service) it.next();
                System.out.println("  Name: " + service_.getServiceName()); 
            }
         }
         tx.commit();
         session.close(); 
      } catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      }
      
   }
   
   private static byte[] readBytesFromFile(String filePath) throws IOException {
       File inputFile = new File(filePath);
       FileInputStream inputStream = new FileInputStream(inputFile);
        
       byte[] fileBytes = new byte[(int) inputFile.length()];
       inputStream.read(fileBytes);
       inputStream.close();
        
       return fileBytes;
   }
   
   private static void saveBytesToFile(String filePath, byte[] fileBytes) throws IOException {
       FileOutputStream outputStream = new FileOutputStream(filePath);
       outputStream.write(fileBytes);
       outputStream.close();
   }
   
}