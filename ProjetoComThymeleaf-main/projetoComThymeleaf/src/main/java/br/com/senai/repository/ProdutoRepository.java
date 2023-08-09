package br.com.senai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.senai.model.Produto;

@Repository
public interface ProdutoRepository  extends JpaRepository<Produto, Long>{

}
