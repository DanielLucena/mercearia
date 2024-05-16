# Mercearia
### Software de mercearia utilizado pelos funcionários da loja

Diferentes produtos serão fornecidos por um fornecedor. Estamos usando um cenário onde um fornecedor fornece vários produtos únicos, onde um fornecedor não oferece o mesmo produto que outro.

O funcionário vai perguntar pelo CPF do cliente e vai registar um pedido em seu nome, onde não é necessário um cadastro prévio, apenas vinculação com CPF. O funcionário poderá fazer o cadastro do cliente incluindo o cpf aos dados do cliente em outro momento.  Ele receberá os produtos do cliente e será contabilizado no total do pedido de acordo com a quantidade (produto_pedido) e o valor de cada produto, descontando suas quantidades de estoque.

Ao um cliente finalizar um pedido, ele receberá um desconto para sua próxima compra que será armazenado em Beneficio_Cliente. Na sua próxima compra o funcionário deve informar que o cliente tem esse desconto, e caso o cliente opte por usar, será descontado do valor total da compra atual.

![plot](./modelagem2.jpg)

**Alunos: Daniel Lucena, Gabriel Vinicius, Francelmo Farias**

## Requisitos do sistema

### Transição para REST
- [x] Rotas de fornecedor
    - [x] list
    - [x] post
    - [x] get
    - [x] put
    - [x] delete
- [ ] Rotas de produto
    - [ ] list
    - [x] post
    - [x] get
    - [x] put
    - [ ] delete
- [ ] Rotas de pedido
    - [ ] list
    - [ ] post
- [ ] Rotas de cliente
    - [ ] list
    - [ ] post
    - [ ] get
    - [ ] put
    - [ ] delete
    - [ ] getPedidosFromCliente

### Novas rotas
- [ ] Rotas de pedido
    - [ ] get
    - [ ] put
    - [ ] delete
    - [ ] listaPorFuncionario


### Entidades Novas
- [ ] Pagamento
    - [ ] criar entidade: tipoPagamento(ENUM), valor, troco
    - [ ] list
    - [ ] post
    - [ ] get
    - [ ] put
    - [ ] delete
- [ ] Usuario
    - [ ] criar entidade: login, senha, role(cliente, caixa, repositor)
    - [ ] list
    - [ ] post
    - [ ] get
    - [ ] put
    - [ ] delete
    - [ ] atenticar
- [ ] Funcionario
    - [ ] criar entidade: nome, usuario_id
    - [ ] list
    - [ ] post
    - [ ] get
    - [ ] put
    - [ ] delete
- [ ] Remessa
    - [ ] criar entidade: fornecedor_id, itens, funcionario_id
    - [ ] list
    - [ ] post
    - [ ] get
    - [ ] put
    - [ ] delete 
- [ ] ItemRemessa (entidade de ligação)
    - [ ] criar entidade: remessa_id, produto_id, quantidade