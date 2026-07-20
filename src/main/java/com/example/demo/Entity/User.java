package com.example.demo.Entity;


import com.example.demo.Enum.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @Column(unique = true)
   private String email;
   private String password;
   @Enumerated(EnumType.STRING)
   private Role role;
    @JsonManagedReference
   @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
   private List<Order> orders;



}
