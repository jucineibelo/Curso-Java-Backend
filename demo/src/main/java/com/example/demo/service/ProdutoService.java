package com.example.demo.service;

import com.example.demo.entity.Produto;
import com.example.demo.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> listarProdutos() {
        return produtoRepository.obterTodos();
    }

    public Optional<Produto> getProdutoById(UUID id) {
        return produtoRepository.getProdutoById(id);
    }

    public Produto addProduto(Produto produto) {
        return produtoRepository.adiconarProduto(produto);
    }

    public Produto editProduto(UUID id, Produto produto) {
        produto.setId(id);
        return produtoRepository.atualizarProduto(produto);
    }

    public void deletarProduto(UUID id) {
        produtoRepository.deleteProduto(id);
    }


}