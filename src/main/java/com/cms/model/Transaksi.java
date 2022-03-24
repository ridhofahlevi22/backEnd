package com.cms.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transaksi", schema = "public")
public class Transaksi implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private UUID id;
	private String hp;
	private String provider;

	public Transaksi() {
	}
	
	public Transaksi(UUID id) {
		this.id = id;
	}

	public Transaksi(UUID id, String hp, String provider) {
		this.id = id;
		this.hp = hp;
		this.provider = provider;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	public UUID getId() {
		return this.id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	@Column(name = "hp", length = 15)
	public String getHp() {
		return this.hp;
	}

	public void setHp(String hp) {
		this.hp = hp;
	}

	@Column(name = "provider", length = 50)
	public String getProvider() {
		return this.provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

}
