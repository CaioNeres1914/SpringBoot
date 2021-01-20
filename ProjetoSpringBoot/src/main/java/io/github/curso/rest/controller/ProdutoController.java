package io.github.curso.rest.controller;

import io.github.curso.domain.entity.Produto;
import io.github.curso.domain.repositorio.Produtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/produtos/")
public class ProdutoController {

    @Autowired
    Produtos produtoRepository;

    @GetMapping("{id}")
    public Produto getProduto( @PathVariable Integer id ){

        return produtoRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Produto não encontrado"));

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto criaProduto(@Valid @RequestBody Produto produto){

        return produtoRepository.save(produto);

    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduto(@PathVariable Integer id){

        produtoRepository.findById(id)
                         .map(produto -> {produtoRepository.delete(produto);
                         return produto;})
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado !"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void atualizaProduto(@Valid @PathVariable Integer id, @RequestBody Produto produto){

        produtoRepository.findById(id)
                         .map(produtoExistente -> {
                             produto.setId(produtoExistente.getId());
                             produtoRepository.save(produto);
                             return produtoExistente;
                         } ).orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));

    }

    @GetMapping
    public List<Produto> buscaProdutos (Produto filtro){

        ExampleMatcher matcher = ExampleMatcher
                                 .matching()
                                 .withIgnoreCase()
                                 .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro,matcher);

        return produtoRepository.findAll(example);

    }

}
