package org.atm.db.model;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.List;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bank_user")
public class User implements UserDetails {
    @Id
    @Column(name = "user_id")
    private long id;

    @Column(name = "user_pass")
    private String password;

    @Column(name = "user_login")
    private String login;

    @Column(name = "user_role_id")
    private int roleId;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "acc_user_id")
    private List<Account> accountList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(Role.getRole(roleId).name()));
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
