package uz.pdp.appemailsend.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private UUID id; // id unrepeatable

    @Size(min = 3, max = 30)
    @Column(nullable = false, length = 50)
    private String firstName;

    @Size(min = 3, max = 30)
    @Column(nullable = false)
    private String lastName;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt; //created date

    @UpdateTimestamp
    private Timestamp updateAt;  //update

    @ManyToMany
    private List<Role> roles;

    private boolean accountNonExpired = true; // account amal qlish muddati otmagan
    private boolean accountNonLocked = true; // user bloklanmaganligi
    private boolean credentialsNonExpired = true; //
    private boolean enabled;

    // -------------------User Details implement methods------------------//
    @Override // privileges // role
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override  //
    public boolean isAccountNonExpired() { //
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
