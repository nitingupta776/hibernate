import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "customer")
public class Customer {
   @Id 
   @Column(name = "id")
   private int id;

   @Column(name = "name")
   private String name;

   @Column(name = "address")
   private String address;

   @Column(name = "birthdate")
   private Date birthdate;

   @Column(name = "picture")
   private byte[] picture;


 @OneToMany(fetch = FetchType.LAZY, mappedBy = "theCustomer")
   private Set<Service> services = new HashSet<Service>(0);

   public Customer() {}
   
   public int getId() {
      return id;
   }
   
   public void setId( int id ) {
      this.id = id;
   }
   
   public String getName() {
      return name;
   }
   
   public void setName( String name ) {
      this.name = name;
   }
   
   public String getAddress() {
      return address;
   }
   
   public void setAddress( String address ) {
      this.address = address;
   }
   
   public Date getBirthDate() {
      return birthdate;
   }
   
   public void setBirthDate( Date birthdate ) {
      this.birthdate = birthdate;
   }

   public byte[] getPicture() {
      return picture;
   }
   
   public void setPicture( byte[] picture ) {
      this.picture = picture;
   }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "theCustomer")
   public Set<Service> getServices() {
      return services;
   }
   
 @OneToMany(fetch = FetchType.LAZY, mappedBy = "theCustomer")
   public void setCerts( Set<Service> services ) {
      this.services = services;
   }
}