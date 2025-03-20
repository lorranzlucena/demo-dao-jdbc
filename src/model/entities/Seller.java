package model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Seller implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String email;
	private Date birth;
	private Double baseSalary;

	private Department derpartment;

	public Seller(Integer id, String name, String email, Date birth, Double baseSalary, Department derpartment) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.birth = birth;
		this.baseSalary = baseSalary;
		this.derpartment = derpartment;
	}

	public Seller() {

	};

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public Double getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(Double baseSalary) {
		this.baseSalary = baseSalary;
	}

	public Department getDerpartment() {
		return derpartment;
	}

	public void setDerpartment(Department derpartment) {
		this.derpartment = derpartment;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Seller other = (Seller) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Seller id = " + id 
				+ " | name = " + name 
				+ " | email = " + email 
				+ " | birth = " + birth 
				+ " | baseSalary = "	+ baseSalary 
				+ "\n "
				+ " ---- DEPARTMENT ---- "
				+  derpartment
				+ "\n ";
		
	}

}
