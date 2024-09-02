package com.example.demo.controller;

import com.example.demo.dto.ProdutoDTO;
import com.example.demo.model.ProdutoRequest;
import com.example.demo.model.ProdutoResponse;
import com.example.demo.service.ProdutoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> getAll() {
        List<ProdutoDTO> produtoDTOList = produtoService.listarProdutos();
        ModelMapper modelMapper = new ModelMapper();
        List<ProdutoResponse> resposta = produtoDTOList.stream()
                .map(produtoDTO -> modelMapper.map(produtoDTO, ProdutoResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(resposta, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> getById(@PathVariable UUID id) {
        Optional<ProdutoDTO> produtoDTO = produtoService.getProdutoById(id);
        ModelMapper modelMapper = new ModelMapper();
        return new ResponseEntity<>(modelMapper.map(produtoDTO, ProdutoResponse.class), HttpStatus.OK);
    }


    @PostMapping
    ResponseEntity<ProdutoResponse> post(@RequestBody ProdutoRequest produtoRequest) {
        ModelMapper modelMapper = new ModelMapper();
        ProdutoDTO produtoDTO = modelMapper.map(produtoRequest, ProdutoDTO.class);
        produtoDTO = produtoService.addProduto(produtoDTO);
        return new ResponseEntity<>(modelMapper.map(produtoDTO, ProdutoResponse.class), HttpStatusCode.valueOf(201)); //201 CREATED
    }

    @PutMapping("/{id}")
    ResponseEntity<ProdutoResponse> put(@PathVariable UUID id, @RequestBody ProdutoRequest produtoRequest) {
        ModelMapper modelMapper = new ModelMapper();
        ProdutoDTO produtoDTO = modelMapper.map(produtoRequest, ProdutoDTO.class);
        produtoDTO = produtoService.editProduto(id, produtoDTO);
        return new ResponseEntity<>(modelMapper.map(produtoDTO, ProdutoResponse.class), HttpStatusCode.valueOf(200));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable UUID id) {
        produtoService.deletarProduto(id);
        return ResponseEntity.status(HttpStatusCode.valueOf(204)).build();  //204 NO CONTET
    }

}
