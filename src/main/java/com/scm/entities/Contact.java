package com.scm.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Contact {
	@Id
	@Column(name = "contact_id")
	private String id;
	private String name;
	private String email;
	private String phoneNumber;
	@Column(length = 1000)
	private String address;
	@Column(length = 1000)
	private String picture;
	@Column(length = 1000)
	private String description;
	private boolean favorite = false;
	private String websiteLink;
	private String linkedInLink;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<SocialLink> links = new ArrayList<>();

}
