package com.example.demo.controller;

import com.example.demo.entity.Produto;
import com.example.demo.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    ResponseEntity<?> getAll() {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(produtoService.listarProdutos());
    }

    @GetMapping("/{id}")
    ResponseEntity<Optional<Produto>> getById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(produtoService.getProdutoById(id));
    }

    @PostMapping
    ResponseEntity<Produto> post(@RequestBody Produto produto) {
        Produto p = produtoService.addProduto(produto);
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(p);
    }

    @PutMapping("/{id}")
    ResponseEntity<Produto> put(@PathVariable UUID id, @RequestBody Produto produto) {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(produtoService.editProduto(id, produto));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable UUID id) {
        produtoService.deletarProduto(id);
        return ResponseEntity.status(HttpStatusCode.valueOf(204)).build();
    }


}
