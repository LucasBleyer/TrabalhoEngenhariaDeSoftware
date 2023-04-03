
package com.mycompany.entities;

import java.util.ArrayList;


public class Pedido {
    
    private ArrayList<Produto> listProdutoPedido;
    public ArrayList<Pedido> listPedidosAbertos;
    
    private double valorTotal;
    private FormaPagamento formaPagamento;
    private int parcelas;
    private String estado;

    public Pedido() {
        this.listProdutoPedido = new ArrayList<>();
        this.listPedidosAbertos = new ArrayList<>();
        
        this.valorTotal = 0.0;
        this.formaPagamento = null;
        this.parcelas = 0;
        this.estado = "Aberto";
    }
    
    public void cadastrarPedido(Pedido pedido) {
        this.listPedidosAbertos.add(pedido);
    }

    public void adicionarProdutoNoPedido(Produto produto, int quantProdutos, int idPedido) {
        var existe = false;
        var nome = produto.getNome();
        var valor = produto.getValor();
        var quantidade = produto.getQuantidade();
        var valorTotal = listPedidosAbertos.get(idPedido).getValorTotal();
        if (quantProdutos <= quantidade) {
            for (Produto p : listPedidosAbertos.get(idPedido).getProdutoPedido()) {
                if (p.getNome().equals(nome)) {
                    p.setQuantidade(p.getQuantidade() + quantProdutos);
                    produto.setQuantidade(quantidade - quantProdutos);
                    existe = true;
                }
            }
            if (existe == false) {
                Produto produtoPedido = new Produto(nome, valor, quantProdutos);
                listPedidosAbertos.get(idPedido).getProdutoPedido().add(produtoPedido);
                listPedidosAbertos.get(idPedido).setValorTotal(valorTotal + (produto.getValor() * quantProdutos));
                produto.setQuantidade(quantidade - quantProdutos);
            }
        }
    }

    public void removerProduto(Produto produto, int quantProdutos, int idPedido) {
        var quantidade = produto.getQuantidade();
        var nome = produto.getNome();
        var valor = produto.getValor();
        Pedido pedido = listPedidosAbertos.get(idPedido);
        var valorTotal = pedido.getValorTotal();
        ArrayList<Produto> produtoDoPedido = pedido.getProdutoPedido();

        for (Produto p : produtoDoPedido) {
            if (p.getNome().equals(nome)) {
                if (p.getQuantidade() == quantProdutos) {
                    produtoDoPedido.remove(p);
                }
                listPedidosAbertos.get(idPedido).setValorTotal(valorTotal - (produto.getValor() * quantProdutos));
                produto.setQuantidade(quantidade + quantProdutos);
                break;
            }
        }
    }

    public String concluirVenda(int idPedido, FormaPagamento formaPagamento, Integer numeroParcelas) {
        Pedido pedido = listPedidosAbertos.get(idPedido);
        String retorno = "";
        if (!pedido.getProdutoPedido().isEmpty() && pedido.getEstado() == "Aberto") {
            switch (formaPagamento) {
                case DINHEIRO:
                    retorno = "O total da compra foi de R$" + pedido.getValorTotal() + ", a forma de pagamento escolhida foi dinheiro";
                    pedido.setEstado("Fechado");
                    listPedidosAbertos.remove(pedido);
                    break;
                case CARTAO_CREDITO:
                    var valorParcela = pedido.getValorTotal() / numeroParcelas;
                    if (valorParcela > 20) {
                        retorno = "O total da compra foi de R$" + pedido.getValorTotal() + ", a forma de pagamento escolhida foi no cartão de crédito, e o valor da parcela ficou: R$" + valorParcela;
                        pedido.setEstado("Fechado");
                        listPedidosAbertos.remove(pedido);
                    } else {
                        retorno = "Valor de parcelas inferior a 20 reais.";
                    }

                    break;
                case CARTAO_DEBITO:
                    retorno = "O total da compra foi de R$" + pedido.getValorTotal() + ", a forma de pagamento escolhida foi no cartão de débito";
                    pedido.setEstado("Fechado");
                    listPedidosAbertos.remove(pedido);
                    break;
            }
            return retorno;

        } else {
            return "Não há itens em seu pedido!";
        } 
    }


    public ArrayList<Produto> getProdutoPedido() {
        return listProdutoPedido;
    }

    public void setProdutoPedido(ArrayList<Produto> produtoPedido) {
        this.listProdutoPedido = produtoPedido;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public int getParcelas() {
        return parcelas;
    }

    public void setParcelas(int parcelas) {
        this.parcelas = parcelas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
