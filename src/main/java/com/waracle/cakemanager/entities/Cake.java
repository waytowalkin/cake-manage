package com.waracle.cakemanager.entities;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Cake {
	
	private @Id @GeneratedValue Long id;
	private String firstName;
	private String lastName;
	private String description;
	private @Version @JsonIgnore Long version;
	private @ManyToOne CakeManager manager;

	

	private Cake() {
		
	}

	public Cake(String firstName, String lastName, String description, CakeManager manager) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.description = description;
		this.manager = manager;

	}
	
	
		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Cake Cake = (Cake) o;
			return Objects.equals(id, Cake.id) &&
				Objects.equals(firstName, Cake.firstName) &&
				Objects.equals(lastName, Cake.lastName) &&
				Objects.equals(description, Cake.description) &&
				Objects.equals(version, Cake.version) &&
			    Objects.equals(manager, Cake.manager);
		}

		@Override
		public int hashCode() {

			return Objects.hash(id, firstName, lastName, description, manager);
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
		
		public CakeManager getManager() {
			return manager;
		}

		public void setManager(CakeManager manager) {
			this.manager = manager;
		}
		
		public Long getVersion() {
			return version;
		}

		public void setVersion(Long version) {
			this.version = version;
		}


		@Override
		public String toString() {
			return "Cake{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", description='" + description + '\'' +
				", manager=" + manager +
      			'}';
		}
	

}
