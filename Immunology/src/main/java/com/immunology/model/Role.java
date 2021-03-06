package com.immunology.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.immunology.logic.utils.enums.UserRoles;

@Entity
@Table(name = "roles")
public class Role {

	@Id
	@GeneratedValue
	private long id;

	@Column(name = "role_name")
	@Enumerated(EnumType.STRING)
	private UserRoles roleName;

	@ManyToMany(mappedBy = "roles")
	private List<User> users;
	
	public long getId() {
		return id;
	}

	public Role setId(long id) {
		this.id = id;
		return this;
	}
	
	public UserRoles getRoleName() {
		return roleName;
	}

	public void setRoleName(UserRoles roleName) {
		this.roleName = roleName;
	}
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
