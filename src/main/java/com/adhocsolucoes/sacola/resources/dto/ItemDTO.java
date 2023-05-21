package com.adhocsolucoes.sacola.resources.dto;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@Embeddable
@NoArgsConstructor
public class ItemDTO {
	
	private Long idProduto;
	private int quantidade;
	private Long idSacola;

}
