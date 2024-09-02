package com.example.demo.service;

import com.example.demo.dto.ProdutoDTO;
import com.example.demo.model.Produto;
import com.example.demo.repository.ProdutoRepository;
import com.example.demo.tool.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<ProdutoDTO> listarProdutos() {
        List<Produto> produtos = produtoRepository.findAll();
        return produtos.stream()
                .map(produto -> new ModelMapper().map(produto, ProdutoDTO.class))  //ModelMaper ta convertendo uma lista de produtos para uma lista de produtodto
                .collect(Collectors.toList());
    }

    public Optional<ProdutoDTO> getProdutoById(UUID id) {
        Optional<Produto> produto = produtoRepository.findById(id);

        if (produto.isEmpty()) {
            throw new ResourceNotFoundException("Produto não encontrado");
        }
        ProdutoDTO produtoDTO = new ModelMapper().map(produto.get(), ProdutoDTO.class);
        return Optional.of(produtoDTO);
    }

    public ProdutoDTO addProduto(ProdutoDTO produtoDTO) {
        //Removendo o ID para conseguir fazer o cadastro
        produtoDTO.setId(null);
        //Criar o objeto para fazer o mapeamento (conversão)
        ModelMapper modelMapper = new ModelMapper();
        //Convertendo o objeto DTO para o objeto Produto do banco de dados
        Produto produto = modelMapper.map(produtoDTO, Produto.class);
        //Salvar o produto do banco
        produto = produtoRepository.save(produto);
        produtoDTO.setId(produto.getId());
        //retornar o objeto Dto atualizado
        return produtoDTO;
    }


    public void deletarProduto(UUID id) {
        Optional<Produto> produto = produtoRepository.findById(id);

        if (produto.isEmpty()) {
            throw new ResourceNotFoundException("Produto não encontrado");
        }

        produtoRepository.deleteById(id);
    }

    public ProdutoDTO editProduto(UUID id, ProdutoDTO produtoDTO) {
        //Passar o ID para o produto DTO
        produtoDTO.setId(id);
        //Criar um objeto de mapeamento
        ModelMapper modelMapper = new ModelMapper();
        //Converter o Dto em um produto
        Produto produto = modelMapper.map(produtoDTO, Produto.class);
        //Atualizar o produto no banco
        produtoRepository.save(produto);
        //Retornar o produto Dto atualizado
        return produtoDTO;
    }


}