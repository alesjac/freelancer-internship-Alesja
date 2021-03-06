package al.ikubinfo.internship.freelancer.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

// TODO @GEtter + @Setter + @ToString + @EqualsAndHashCode = @Data. Easier right?
@Data
@NoArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(name = "user_email_unique", columnNames = "email") })
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "surname")
	private String surname;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "role")
	private Role role;

	@Column(name = "locked")
	private Boolean locked = false;

	@Column(name = "enabled")
	private Boolean enabled = false;

	
	@ManyToMany
	@JoinTable(name = "application", joinColumns = @JoinColumn(name = "job_post_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<JobPost> jobPostsApplication;


	public User(String name, String surname, String email, String passw, Role role) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = passw;
		this.role = role;

	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());

		return Collections.singletonList(authority);
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {

		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}


}
