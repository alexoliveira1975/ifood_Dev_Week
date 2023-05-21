package com.adhocsolucoes.sacola.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adhocsolucoes.sacola.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

}
