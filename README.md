# Testes Automatizados - API Petstore (Swagger)

Este projeto contém uma suíte de testes automatizados desenvolvidos com **Java**, **Rest Assured** e **TestNG**, com foco na validação de endpoints da [Swagger Petstore API](https://petstore.swagger.io/).

---

## 🔧 Tecnologias utilizadas

* Java 11+
* Rest Assured
* TestNG
* Maven (ou Gradle)
* IntelliJ IDEA

---

## 🚀 Como executar

1. Clone este repositório:
   git clone https://github.com/seu-usuario/petstore-api-tests.git
2. Abra o projeto em sua IDE.
3. Execute a classe `Petstore.java` como uma suíte de testes TestNG.
4. Certifique-se de ter conexão com a internet (os testes são executados diretamente contra a API pública).

---

## ✅ Resumo dos Testes Automatizados

| Teste                                      | Método HTTP | Endpoint            | Esperado      |
| ------------------------------------------ | ----------- | ------------------- | ------------- |
| Cadastrar novo pedido de pet               | POST        | `/store/order`      | 200 OK        |
| Buscar pet inexistente                     | GET         | `/pet/{petId}`      | 404 Not Found |
| Atualizar pet existente                    | PUT         | `/pet`              | 200 OK        |
| Buscar pet com ID inválido                 | GET         | `/pet/{petId}`      | 404 + erro    |
| Buscar pets com status "pending"           | GET         | `/pet/findByStatus` | 200 OK        |
| Requisição com método inválido no endpoint | DELETE      | `/pet`              | 405 Error     |

---

## 📋 Casos de Teste Detalhados

### 1. Cadastrar novo pedido de pet com sucesso

* **Cenário:** Um novo pedido de pet é enviado com dados válidos.
* **Ação:** Envia requisição `POST` com JSON contendo `petId`, `quantity`, `status`, `complete`, `shipDate`.
* **Validação:** Status `200`, resposta contém os mesmos dados enviados, e ID corresponde ao enviado.

---

### 2. Pesquisar por um pet inexistente

* **Cenário:** Um pet com ID inexistente é consultado.
* **Ação:** Envia requisição `GET /pet/{petId}` com ID inexistente.
* **Validação:** Status `404`, resposta contém `"message": "Pet not found"`.

---

### 3. Atualizar dados de um pet existente

* **Cenário:** Pet existente é atualizado com novo nome, categoria e status.
* **Ação:** Requisição `PUT /pet` com objeto JSON atualizado.
* **Validação:** Status `200`, resposta confirma as mudanças aplicadas (ex.: nome, categoria, status).

---

### 4. Pesquisar pet informando ID com formato inválido

* **Cenário:** É feita uma busca usando um ID malformado (ex: `123!asd`).
* **Ação:** Envia requisição `GET /pet/{petId}` com valor inválido.
* **Validação:** Status `404`, resposta com mensagem de exceção (`NumberFormatException`).

---

### 5. Pesquisar por pets com status “pending”

* **Cenário:** Consulta todos os pets com status "pending".
* **Ação:** Requisição `GET /pet/findByStatus?status=pending`.
* **Validação:** Status `200`, lista de pets com `status: "pending"`, IDs e nomes válidos.

---

### 6. Realizar requisição com método inválido no endpoint `/pet`

* **Cenário:** Uma tentativa de apagar pets diretamente via endpoint `/pet`.
* **Ação:** Envia requisição `DELETE /pet`.
* **Validação:** Status `405 Method Not Allowed`.

---

## 📁 Estrutura do Projeto

```
src
└── test
    └── java
        └── org.example
            ├── Petstore.java          // Classe com os testes automatizados
            ├── model
            │   ├── orderPet.java      // Classe de modelo para pedidos
            │   ├── Pet.java           // Classe de modelo para pets
            │   ├── Category.java      // Classe de modelo para categoria
            │   └── Tag.java           // Classe de modelo para tags
```

---

## 📌 Observações

* Os testes interagem com a **API pública da Swagger**, portanto os dados podem mudar entre execuções.
* Em ambientes reais, recomenda-se criar dados isolados ou utilizar mocks/stubs.
