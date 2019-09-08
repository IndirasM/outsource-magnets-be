package com.psk.demo.Entity;

import javax.persistence.*;

@Entity
@Table(name = "checklist")
public class Checklist {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "checklist_id")
	private Long id;

	@Column(name = "need_ticket")
	private boolean needTicket;

	@Column(name = "need_accommodation")
	private boolean needAccommodation;

	@Column(name = "need_transport")
	private boolean needTransport;

	//region getters

	public Long getId() {
		return this.id;
	}

	public boolean getNeedTicket() {
		return this.needTicket;
	}

	public boolean getNeedAccommodation() {
		return this.needAccommodation;
	}

	public boolean getNeedTransport() {
		return this.needTransport;
	}


	//endregion

	//region setters

	public void setId(Long id) {
		this.id = id;
	}


	public void setNeedTicket(boolean needTicket) {
		this.needTicket = needTicket;
	}

	public void setNeedAccommodation(boolean needAccommodation) {
		this.needAccommodation = needAccommodation;
	}

	public void setNeedTransport(boolean needTransport) {
		this.needTransport = needTransport;
	}

	//endregion
}
