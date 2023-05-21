package com.adhocsolucoes.sacola.services;

import com.adhocsolucoes.sacola.model.Item;
import com.adhocsolucoes.sacola.model.Sacola;
import com.adhocsolucoes.sacola.resources.dto.ItemDTO;

public interface SacolaService {
	
	Sacola verSacola(Long id);
	
	Sacola fecharSacola(Long id, int formaPagamento);
	
	Item incluirItemSacola(ItemDTO itemDto);
	
}
	
