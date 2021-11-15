import java.util.UUID;

import javax.persistence.*;

@Entity
@Table(name = "service")
public class Service {
   @Id 
   @Column(name = "uuid")
   private UUID uuid;

   @Column(name = "servicename")
   private String serviceName;

   @Column(name = "rate")
   private int rate;

   @ManyToOne(targetEntity = Customer.class)
    @JoinColumn(name="Customer_ID")
   private Customer theCustomer;

   public Service() {}
   
   public UUID getUUId() {
      return uuid;
   }
   
   public void setUUId( UUID uuid ) {
      this.uuid = uuid;
   }
   
   public String getServiceName() {
      return serviceName;
   }
   
   public void setServiceName( String servicename ) {
      this.serviceName = servicename;
   }

   public int getRate() {
      return rate;
   }
   
   public void setRate( int rate ) {
      this.rate = rate;
   }

   public Customer getCustomer() {
      return theCustomer;
   }
   
   public void setCustomer(Customer customer) {
      this.theCustomer = customer;
   }
}