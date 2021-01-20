package io.github.curso.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table( name = "cliente" )
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "{cliente.nome}")
    @Column(name = "nome", length = 100)
    private String nome;

    @CPF(message = "{cliente.cpf.validate}")
    @NotBlank(message =  "{cliente.cpf}")
    @Column(name = "cpf", length = 12)
    private String cpf;

    @NotBlank(message =  "{cliente.email}")
    @Column(name = "email")
    private String email;

    @JsonIgnore
    @OneToMany( mappedBy = "cliente" , fetch = FetchType.LAZY )
    private Set<Pedido> pedidos;

}
