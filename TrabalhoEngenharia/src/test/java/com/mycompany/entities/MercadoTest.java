
package com.mycompany.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class MercadoTest {
    
    private Mercado mercado;
    
    public MercadoTest() {
    }
    
    @BeforeEach
    public void inicializando(){
        this.mercado = new Mercado();
    }
    
    @Test
    public void testInserir() {
        Produto produto = new Produto("Copo", 12, 5);
        mercado.InserirEstoque(produto);
        assertEquals(1, mercado.listEstoque.size());
    }

    @Test
    public void testEditar() {
        Produto produto = new Produto("Copo", 12, 5);
        mercado.InserirEstoque(produto);
        assertEquals(1, mercado.listEstoque.size());
        String newNome = "Taça";
        double newPreco = 20;
        int newQuantidade = 5;
        
        mercado.EditarEstoque(produto, newNome, newPreco, newQuantidade);
        assertEquals(1, mercado.listEstoque.size());
        assertEquals("Taça", mercado.listEstoque.get(0).getNome());
        assertEquals(20, mercado.listEstoque.get(0).getValor());
        assertEquals(5, mercado.listEstoque.get(0).getQuantidade());
    }
   
    @Test
    public void testExcluir() {
        Produto produto = new Produto("Copo", 12, 5);
        mercado.InserirEstoque(produto);
        assertEquals(1, mercado.listEstoque.size());
        mercado.ExcluirDoEstoque(1);
        assertEquals(0, mercado.listEstoque.size());
    }
    
    @Test
    public void deveCadastrarPedido() {
        Pedido pedido = new Pedido();
        pedido.cadastrarPedido(pedido);
        
        Pedido pedido2 = new Pedido();
        pedido.cadastrarPedido(pedido2);
        
        assertEquals(2, pedido.listPedidosAbertos.size());
    }
    
    @Test
    public void adicionarProdutoNoPedido() {
       Produto produto = new Produto("Copo", 12, 5);
       mercado.InserirEstoque(produto);
       
       Pedido pedido = new Pedido();
       pedido.cadastrarPedido(pedido);
       
       pedido.adicionarProdutoNoPedido(produto, 1, 0);
       assertEquals(4, produto.getQuantidade());
    }
    
    @Test
    public void naoDeveAdicionarDoisProdutosRepetidosDeveAcrescerAQuantidade() {
       Produto produto = new Produto("Copo", 12, 5);
       mercado.InserirEstoque(produto);
       
       Pedido pedido = new Pedido();
       pedido.cadastrarPedido(pedido);
       pedido.adicionarProdutoNoPedido(produto, 1, 0);
       pedido.adicionarProdutoNoPedido(produto, 1, 0);
       
       assertEquals(3, produto.getQuantidade());
       assertEquals(2, pedido.getProdutoPedido().get(0).getQuantidade());
    }
    
    @Test
    public void deveExcluirProdutoDaLista() {
       Produto produto = new Produto("Copo", 12, 5);
       mercado.InserirEstoque(produto);
       
       Pedido pedido = new Pedido();
       pedido.cadastrarPedido(pedido);
       pedido.adicionarProdutoNoPedido(produto, 1, 0);
       pedido.removerProduto(produto, 1, 0);
       
       assertEquals(5, produto.getQuantidade());
       assertEquals(0, pedido.getValorTotal());
       assertEquals(0, pedido.getProdutoPedido().size());
    }
    
    @Test
    public void naoDeveExcluirProdutoDaListaApenasDecrescerQuantidade() {
       Produto produto = new Produto("Copo", 12, 5);
       mercado.InserirEstoque(produto);
       
       Pedido pedido = new Pedido();
       pedido.cadastrarPedido(pedido);
       pedido.adicionarProdutoNoPedido(produto, 2, 0);
       pedido.removerProduto(produto, 1, 0);
       
       assertEquals(4, produto.getQuantidade());
       assertEquals(12, pedido.getValorTotal());
       assertEquals(1, pedido.getProdutoPedido().size());
    }
    
    @Test
    public void naoDeveConcluirUmPedidoSemProdutos() {
       Pedido pedido = new Pedido();
       pedido.cadastrarPedido(pedido);
       
       var resposta = pedido.concluirVenda(0, FormaPagamento.DINHEIRO, Integer.MIN_VALUE);
       var respostaEsperada = "Não há itens em seu pedido!";
       
       assertEquals(respostaEsperada, resposta);
    }
    
    @Test
    public void deveConcluirOPedidoPagandoComDinheiro() {
       Produto produto = new Produto("Copo", 12, 5);
       mercado.InserirEstoque(produto);
       
       Pedido pedido = new Pedido();
       pedido.cadastrarPedido(pedido);
       pedido.adicionarProdutoNoPedido(produto, 2, 0);
       
       var resposta = pedido.concluirVenda(0, FormaPagamento.DINHEIRO, Integer.MIN_VALUE);
       var respostaEsperada = "O total da compra foi de R$24.0, a forma de pagamento escolhida foi dinheiro";
       
       assertEquals(respostaEsperada, resposta);
       assertEquals(0, pedido.listPedidosAbertos.size());
    }
    
    @Test
    public void deveConcluirOPedidoPagandoComDebito() {
       Produto produto = new Produto("Copo", 12, 5);
       mercado.InserirEstoque(produto);
       
       Pedido pedido = new Pedido();
       pedido.cadastrarPedido(pedido);
       pedido.adicionarProdutoNoPedido(produto, 2, 0);
       
       var resposta = pedido.concluirVenda(0, FormaPagamento.CARTAO_DEBITO, Integer.MIN_VALUE);
       var respostaEsperada = "O total da compra foi de R$24.0, a forma de pagamento escolhida foi no cartão de débito";
       
       assertEquals(respostaEsperada, resposta);
       assertEquals(0, pedido.listPedidosAbertos.size());
    }
    
    @Test
    public void deveConcluirOPedidoPagandoComCredito() {
       Produto produto = new Produto("Copo", 12, 5);
       mercado.InserirEstoque(produto);
       
       Pedido pedido = new Pedido();
       pedido.cadastrarPedido(pedido);
       pedido.adicionarProdutoNoPedido(produto, 4, 0);
       
       var resposta = pedido.concluirVenda(0, FormaPagamento.CARTAO_CREDITO, 2);
       var respostaEsperada = "O total da compra foi de R$48.0, a forma de pagamento escolhida foi no cartão de crédito, e o valor da parcela ficou: R$24.0";
       
       assertEquals(respostaEsperada, resposta);
       assertEquals(0, pedido.listPedidosAbertos.size());
    }
    
    @Test
    public void naoDeveConcluirOPedidoPagandoComCreditoComParcelasInferioresA20Reais() {
       Produto produto = new Produto("Copo", 12, 5);
       mercado.InserirEstoque(produto);
       
       Pedido pedido = new Pedido();
       pedido.cadastrarPedido(pedido);
       pedido.adicionarProdutoNoPedido(produto, 2, 0);
       
       var resposta = pedido.concluirVenda(0, FormaPagamento.CARTAO_CREDITO, 2);
       var respostaEsperada = "Valor de parcelas inferior a 20 reais.";
       
       assertEquals(respostaEsperada, resposta);
       assertEquals(1, pedido.listPedidosAbertos.size());
    }
}
