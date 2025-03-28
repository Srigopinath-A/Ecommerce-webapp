package com.newapp.Webapp.Entity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.convert.Jsr310Converters.LocalTimeToDateConverter;

import com.newapp.Webapp.enums.UserRole;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Data
@Table(name ="user")
@NoArgsConstructor
@Builder
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank(message = "name required")
	private String name;
	
	@Column(unique = true)
	@NotBlank(message = "mail id required")
	private String email;
	
	@NotBlank(message = "password required")
	private String password; // we dont need to return the password
	
	@OneToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	private Address Address;
	private UserRole role;
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY,cascade = CascadeType.ALL )
	private List<OrderItem> orderItemList;
	
	@Column(name = "created_at")
	private final LocalDateTime createdAt  =  LocalDateTime.now();
}
