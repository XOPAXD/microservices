package com.jhone.auth.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="user")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User implements UserDetails, Serializable {
    private static  final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255,unique = true)
    private String userName;

    private String password;

    private Boolean AccountNonExpired;

    private Boolean AccountNonLocked;

    private Boolean CredentialsNonExpired;

    private Boolean isEnabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_permission", joinColumns = {@JoinColumn(name = "id_user")},inverseJoinColumns = {@JoinColumn(name = "id_permissions")})
    private List<Permission> permissions;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.permissions;
    }
    public List<String> getRoles(){
        List<String> roles = new ArrayList<>();
        this.permissions.stream()
                .forEach(p -> {
                    roles.add(p.getDescription());
                        }

                );
        return null;
    }
    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.AccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.AccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.CredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
