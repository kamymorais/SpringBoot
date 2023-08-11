package br.com.senai.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.senai.model.Produto;
import br.com.senai.repository.ProdutoRepository;

@Controller
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@GetMapping("/")
	public String paginaPrincipal() {
		return "index";
	}
	
	@GetMapping("/produto")
	public String listarProdutos(Model model) {
		List<Produto> produtos = produtoRepository.findAll();
		model.addAttribute("produtos", produtos);
		return "produtos";
	}

	@GetMapping("/cadastrar-produto")
	public String paginaAdicionarProduto(Produto produto) {
		return "adicionar_produto";
	}
	
	@PostMapping("/adicionarProduto")
	public String adicionaProduto(@Valid Produto produto, Errors erros, BindingResult result, Model model) {
		
		if(result.hasErrors() || (null != erros && erros.getErrorCount()> 0)) {
			return "adicionar_produto";
		}
		produtoRepository.save(produto);
		return "redirect:/produto";
	}
	
	@GetMapping ("/editar/{id}")
	public String paginaAtualizarProduto (@PathVariable("id") long id, Model model) {
		Produto produto = produtoRepository.findById(id).orElseThrow (() -> new IllegalArgumentException("Identidicador do produto é inválido!" + id));
		model.addAttribute("produto", produto);
		return "editar_produto";
	
	}
	
	@PostMapping ("/atualizar/{id}")
	public String atualizarProduto (@PathVariable("id") long id, @Valid Produto produto, BindingResult result, Model model) {
		if (result.hasErrors()) {
			produto.setId(id);
			return "editar_produto";
			}
		
		produtoRepository.save(produto);
		return "redirect:/produto";
		
	}
	
	@GetMapping("/delete/{id}")
	public String deletarProduto(@PathVariable("id") long id, Model model) {
		Produto produto = produtoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Identificador do produto é inválido!" + id));
		produtoRepository.delete(produto);
		
		return "redirect:/produto";

		
	}
}
