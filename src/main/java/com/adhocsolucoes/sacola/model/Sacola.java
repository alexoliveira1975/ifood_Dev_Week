package com.adhocsolucoes.sacola.model;

import java.util.List;

import com.adhocsolucoes.sacola.enums.FormaPagamento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Sacola {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JsonIgnore
	private Cliente cliente;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Item> itens;
	private Double valorTotal;

	@Enumerated
	private FormaPagamento FormaPagamento;
	private boolean fechada;

}
