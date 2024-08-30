package com.example.demo.repository;

import com.example.demo.entity.Produto;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ProdutoRepository {

    private List<Produto> produtos = new ArrayList<>();

    /**
     * Metodo para retornar lista de produtos
     * @return Lista de Produtos
     */
    public List<Produto> obterTodos() {
        return produtos;
    }

    /**
     * Metodo para pesquisar um produto pelo ID
     * @param id do produto que sera localizado
     * @return um Produto
     * */
    public Optional<Produto> getProdutoById(UUID id) {
        return produtos
                .stream()
                .filter(produto -> produto.getId().equals(id))
                .findFirst();
    }

    /**
     *Metodo para adicionar um produto
     * @param produto que será adicionado
     * @return o produto que será adicionado na lista
     */

    public Produto adiconarProduto(Produto produto){
        UUID novoId = UUID.randomUUID();
        produtos.add(produto.setId(novoId));
        return produto;
    }

    /**
     * Remover um produto pelo Id
     * @param id do produto a ser removido
     */
    public void deleteProduto(UUID id) {
        produtos.removeIf(produto -> produto.getId().equals(id));
    }

    /**
     * Metodo para atualizar um produto
     * @param produto a ser atulizado
     * @return o produto atualizado
     */
    public Produto atualizarProduto(Produto produto){
        Optional<Produto> encontrarProduto = getProdutoById(produto.getId());

        if (encontrarProduto.isEmpty()){
            throw new ResourceNotFoundException("Produto não encontrado");
        }

        produtos.add(produto);
        return produto;
    }

}
