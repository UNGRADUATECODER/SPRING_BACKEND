package com.example.demo.Entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.micrometer.jakarta9.instrument.mail.DefaultMailSendObservationConvention;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.IdGeneratorType;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
   private String username;
   private String email;
   private String password;
   private String role;
    @JsonManagedReference
   @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
   private List<Order> orders;



}
