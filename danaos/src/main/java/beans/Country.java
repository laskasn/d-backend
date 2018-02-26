package beans;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="country")
//@JsonIdentityInfo(scope=Country.class, generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Country implements Serializable {

	
	
	
	@Id
	@Column(name = "id", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
	@Type(type="org.hibernate.type.PostgresUUIDType")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	@Column(name="name")
	private String name;
	@Column(name="icon")
	private byte[] icon;

	@OneToMany(mappedBy = "country", /*cascade = CascadeType.ALL , */ fetch = FetchType.LAZY)
	private Set<Vessel> vessels;
	
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public byte[] getIcon() {
		return icon;
	}
	public void setIcon(byte[] icon) {
		this.icon = icon;
	}
	public Set<Vessel> getVessels() {
		return vessels;
	}
	public void setVessels(Set<Vessel> vessels) {
		this.vessels = vessels;
	}
	
	
	
	
	
}

