package com.adhocsolucoes.sacola.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adhocsolucoes.sacola.model.Item;
import com.adhocsolucoes.sacola.model.Sacola;
import com.adhocsolucoes.sacola.resources.dto.ItemDTO;
import com.adhocsolucoes.sacola.services.SacolaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ifood-devweek/sacolas")
@RequiredArgsConstructor
public class SacolaResource {

	private final SacolaService sacolaService;
	

	@PostMapping
	public Item incluirItemSacola(@RequestBody ItemDTO itemDTO) {
		return sacolaService.incluirItemSacola(itemDTO);
	}
	
	@GetMapping("/{id}")
	public Sacola verSacola(@PathVariable("id") Long id) {
		return sacolaService.verSacola(id);
	}
	
	@PatchMapping("/fecharSacola/{sacolaId}")
	public Sacola fecharSacola(@PathVariable("sacolaId") Long sacolaId, @RequestParam("formaPagamento") int formaPagamento) {
		return sacolaService.fecharSacola(sacolaId, formaPagamento);
	}
}
