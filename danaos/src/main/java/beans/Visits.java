package beans;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity(name="visits")
@Table(name="visits")
//@JsonIdentityInfo(scope=Visits.class, generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Visits implements Serializable {

	
	

	@Id
	@Column(name = "id", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
	@Type(type="org.hibernate.type.PostgresUUIDType")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	
	@ManyToOne//(cascade = CascadeType.ALL)
    @JoinColumn(name = "vessel", referencedColumnName = "id")
	private Vessel vessel;
	
	@ManyToOne//(cascade = CascadeType.ALL)
    @JoinColumn(name = "port", referencedColumnName = "id")
	private Port port;

	@Column(name="visittime")
	private Date visittime;
	
	
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	

    

	public Vessel getVessel() {
		return vessel;
	}
	public void setVessel(Vessel vessel) {
		this.vessel = vessel;
	}
	
	
	public Port getPort() {
		return port;
	}
	public void setPort(Port port) {
		this.port = port;
	}
	
	
	public Date getVisittime() {
		return visittime;
	}
	public void setVisittime(Date visittime) {
		this.visittime = visittime;
	}
	
	
	
}

