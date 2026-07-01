# 📚 3º Semestre - Aplicações com Banco de Dados

## 🎯 Objetivo

Integrar aplicações Java com bancos de dados relacionais, implementando persistência de dados e APIs.

## 📖 Tópicos Cobertos

### Banco de Dados Relacional
- ✅ Design Relacional (Normalização)
- ✅ SQL Básico e Avançado
- ✅ Joins, Subqueries, Aggregates
- ✅ Índices e Performance
- ✅ Triggers e Stored Procedures

### Integração com Java
- ✅ JDBC - Java Database Connectivity
- ✅ ORM com Hibernate/JPA
- ✅ Connection Pooling
- ✅ Transações e ACID

### APIs e Serviços
- ✅ REST API básica
- ✅ HTTP Methods (GET, POST, PUT, DELETE)
- ✅ JSON e Serialização
- ✅ Integração com Frontend

### Spring Boot (Introdução)
- ✅ Spring Framework basics
- ✅ Spring Data JPA
- ✅ Spring Web MVC
- ✅ Anotações e Configuração

## 💻 Linguagens e Tecnologias

- **Java** - 80%
- **SQL** - 15%
- **JavaScript** - 5%
- **Spring Boot** - Framework principal
- **MySQL** - Banco de dados

## 📁 Estrutura

```
03-terceiro-semestre/
├── exercicios/              # Exercícios SQL e JDBC
├── projetos/                # Projetos completos
│   ├── sistema-vendas/      # CRUD de vendas com BD
│   ├── gerenciador-eventos/ # API de eventos
│   └── api-tarefas/         # REST API completa
├── database/                # Scripts SQL
│   └── *.sql
└── README.md
```

## 🚀 Como Executar

### Preparar o Banco de Dados
```bash
mysql -u root -p < database/01-criacao-banco.sql
```

### Executar Projeto Spring Boot
```bash
cd projetos/sistema-vendas/
mvn spring-boot:run
```

### Acessar a API
```bash
curl http://localhost:8080/api/vendas
```

## 📚 Materiais de Referência

- [SQL Tutorial](https://www.w3schools.com/sql/)
- [JDBC Official Documentation](https://docs.oracle.com/javase/tutorial/jdbc/)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Hibernate ORM Guide](https://hibernate.org/orm/documentation/)

## 💡 Projetos Principais

### 1. Sistema de Vendas
**Stack:** Java + Spring Boot + MySQL
**Descrição:** CRUD completo de produtos, clientes e vendas com relatórios

### 2. Gerenciador de Eventos
**Stack:** Java + Spring Boot + MySQL
**Descrição:** API REST para gerenciar eventos e inscrições

### 3. API de Tarefas
**Stack:** Java + Spring Boot + MySQL + JavaScript
**Descrição:** Full-stack com backend REST e frontend web

## 🗄️ Banco de Dados

Ver documentação em `docs/banco-dados/` para:
- Scripts de criação
- Diagramas entidade-relacionamento
- Modelo lógico

## ✅ Competências Adquiridas

- [ ] Entender design relacional
- [ ] Escrever SQL complexo
- [ ] Integrar Java com BD (JDBC)
- [ ] Usar ORMs (Hibernate)
- [ ] Implementar REST APIs
- [ ] Usar Spring Boot
- [ ] Trabalhar com transações
- [ ] Otimizar queries

---

**Status:** ✅ Completo | **Última atualização:** Junho de 2026
