package com.adhocsolucoes.sacola.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.adhocsolucoes.sacola.enums.FormaPagamento;
import com.adhocsolucoes.sacola.model.Item;
import com.adhocsolucoes.sacola.model.Restaurante;
import com.adhocsolucoes.sacola.model.Sacola;
import com.adhocsolucoes.sacola.repositories.ProdutoRepository;
import com.adhocsolucoes.sacola.repositories.SacolaRepository;
import com.adhocsolucoes.sacola.resources.dto.ItemDTO;
import com.adhocsolucoes.sacola.services.SacolaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SacolaServiceImpl implements SacolaService {
	private final SacolaRepository sacolaRepository;
	private final ProdutoRepository produtoRepository;

	@Override
	public Sacola verSacola(Long id) {
		return sacolaRepository.findById(id).orElseThrow(() -> {
			throw new RuntimeException("Sacola não existe.");
		});
	}

	@Override
	public Sacola fecharSacola(Long id, int numFormaPagamento) {
		Sacola sacola = verSacola(id);
		if (sacola.getItens().isEmpty()) {
			throw new RuntimeException("Inclua itens na sacola!");
		}

		FormaPagamento formaPagamento = numFormaPagamento == 0 ? FormaPagamento.DINHEIRO : FormaPagamento.MAQUINETA;

		sacola.setFormaPagamento(formaPagamento);
		sacola.setFechada(true);
		return sacolaRepository.save(sacola);

	}

	@Override
	public Item incluirItemSacola(ItemDTO itemDTO) {
		Sacola sacola = verSacola(itemDTO.getIdSacola());
		
		if (sacola.isFechada()) {
			throw new RuntimeException("Sacola fechada !!!");
		}

		Item itemSerInserido = Item.builder().
				quantidade(itemDTO.getQuantidade())
				.sacola(sacola)
				.produto(produtoRepository.findById(itemDTO.getIdProduto()).orElseThrow(
						() -> {
					throw new RuntimeException("Produto não existe.");
				}
						))
				.build();

		List<Item> itensSacola = sacola.getItens();
		if (itensSacola.isEmpty()) {
			itensSacola.add(itemSerInserido);
		} else {
			Restaurante restauranteAtual = itensSacola.get(0).getProduto().getRestaurante();
			Restaurante restauranteNovoItem = itemSerInserido.getProduto().getRestaurante();
			if (restauranteAtual.equals(restauranteNovoItem)) {
				itensSacola.add(itemSerInserido);
			} else {
				throw new RuntimeException("Não é possível add produtos de diferentes restaurantes.");
			}
		}
		
		List<Double> valorItens = new ArrayList<>();
		
		for(Item itemSacola : itensSacola) {
			double valorTotalItem = itemSacola.getProduto().getValorUnitario() * itemSacola.getQuantidade();
			valorItens.add(valorTotalItem);
		}
		
		Double vlrTotalSacola = 0.0;
		for(Double vlrItem : valorItens) {
			vlrTotalSacola += vlrItem;
		}
		
		sacola.setValorTotal(vlrTotalSacola);
		
		sacolaRepository.save(sacola);

		return itemSerInserido;
	}

}
