package com.mycompany.entities;

import java.util.ArrayList;

public class Mercado {

    public ArrayList<Produto> listEstoque;
    
    public Mercado() {
        this.listEstoque = new ArrayList<>();
    }

    public void InserirEstoque(Produto produto) {
        this.listEstoque.add(produto);
    }

    public void EditarEstoque(Produto produto, String novoNome, double novoPreco, int novaQuantidade) {
        for (Produto p : listEstoque) {
            if (p.equals(produto)) {
                p.setNome(novoNome);
                p.setValor(novoPreco);
                p.setQuantidade(novaQuantidade);
                break;
            }
        }
    }

    public void ExcluirDoEstoque(int id) {
        for (int i = 0; i < listEstoque.size(); i++) {
            if (listEstoque.get(i).getId() == id) {
                listEstoque.remove(i);
                return;
            }
        }
        System.out.println("Produto não encontrado");
    }

    public Produto getProdutoPorId(int id) {
        for (Produto produto : listEstoque) {
            if (produto.getId() == id) {
                return produto;
            }
        }
        return null;
    }
}