package org.atm.db.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bank_user")
public class User {
    @Id
    @Column(name ="user_id")
    private long id;
    @Column(name ="user_pass")
    private String password;
    @Column(name ="user_login")
    private String login;



}
