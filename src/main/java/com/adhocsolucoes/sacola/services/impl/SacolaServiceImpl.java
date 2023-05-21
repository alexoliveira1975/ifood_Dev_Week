package com.adhocsolucoes.sacola.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adhocsolucoes.sacola.enums.FormaPagamento;
import com.adhocsolucoes.sacola.model.Item;
import com.adhocsolucoes.sacola.model.Restaurante;
import com.adhocsolucoes.sacola.model.Sacola;
import com.adhocsolucoes.sacola.repositories.ItemRepository;
import com.adhocsolucoes.sacola.repositories.ProdutoRepository;
import com.adhocsolucoes.sacola.repositories.SacolaRepository;
import com.adhocsolucoes.sacola.resources.dto.ItemDTO;
import com.adhocsolucoes.sacola.services.SacolaService;

@Service
public class SacolaServiceImpl implements SacolaService {


	@Autowired
	private SacolaRepository sacolaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ItemRepository itemRepository;

	@Override
	public Sacola verSacola(Long id) {
		return sacolaRepository.findById(id).orElseThrow(
				() -> {
					throw new RuntimeException("Sacola não existe.");
				}
				);
	}

	@Override
	public Sacola fecharSacola(Long id, int numFormaPagamento) {
		Sacola sacola = verSacola(id);
		if(sacola.getItens().isEmpty()) {
			throw new RuntimeException("Inclua itens na sacola!");
		}
		
		FormaPagamento formaPagamento = numFormaPagamento == 0 ? FormaPagamento.DINHEIRO : FormaPagamento.MAQUINETA;
		
		sacola.setFormaPagamento(formaPagamento);
		sacola.setFechada(true);
		return sacolaRepository.save(sacola);
		
	}

	@Override
	public Item incluirItemSacola(ItemDTO itemDto) {
		Sacola sacola = verSacola(itemDto.getIdSacola());
		if(sacola.isFechada()) {
			throw new RuntimeException("Sacola fechada !!!");
		}
		
		Item itemSerInserido = Item.builder()
		.quantidade(itemDto.getQuantidade())
		.sacola(sacola)
		.produto(produtoRepository.findById(itemDto.getIdProduto()).orElseThrow(
				() -> {
					throw new RuntimeException("Produto não existe.");
				}))
		.build();
		
		List<Item> itensSacola = sacola.getItens();
		if(itensSacola.isEmpty()) {
			itensSacola.add(itemSerInserido);
		} else {
			Restaurante restauranteAtual = itensSacola.get(0).getProduto().getRestaurante();
			Restaurante restauranteNovoItem = itemSerInserido.getProduto().getRestaurante();
			if(restauranteAtual.equals(restauranteNovoItem)) {
				itensSacola.add(itemSerInserido);
			} else {
				throw new RuntimeException("Não é possível add produtos de diferentes restaurantes.");
			}
		}
		
		sacolaRepository.save(sacola);
		
		return itemRepository.save(itemSerInserido);
	}

}
