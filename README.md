# Trabalho Engenharia de Software

# Funcionalidades:
- O sistema permite ao usuário gerenciar um conjunto de produtos, que incluem nome e valor, possibilitando a inserção, alteração e remoção dos mesmos.
- Ao escolher a forma de pagamento como crédito, o sistema permite o registro da quantidade de parcelas, desde que cada parcela seja superior a R$ 20,00.
- O sistema possui um mecanismo de venda através de um Pedido, o qual é composto por uma lista de produtos, o valor total e a forma de pagamento. É importante destacar que o registro da forma de pagamento só é permitido caso haja produtos inseridos no pedido.

# Divisão do trabalho:
 - Pesquisa e implementação do JaCoCo: Lucas.
- Definições de requisitos: João Pedro e Lucas.
- Implementação dos testes: João Pedro e Lucas.
- Desenvolvimento do software: João Pedro e Lucas.
- ReadMe: João Pedro

# Cálculo de Complexidade Ciclomática
- Pedido : adicionarProdutoNoPedido = 4 CCS
- Pedido : removerProduto = 3 CCS 
- Pedido : concluirVenda = 5 CCS
- Mercado : editarEstoque = 1 CCS
