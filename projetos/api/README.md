# 🔌 API REST - Sistema Integrado

## 📝 Descrição

API REST desenvolvida em Java + Spring Boot para integração com diversos sistemas. Fornece endpoints para gerenciamento de recursos.

## 🛠️ Stack Tecnológico

- **Linguagem:** Java 22
- **Framework:** Spring Boot 3.x
- **Build Tool:** Maven
- **Banco de Dados:** MySQL
- **Documentação:** Swagger/OpenAPI

## 📋 Pré-requisitos

- Java 22+
- Maven 3.9+
- MySQL 5.7+

## 🚀 Setup e Instalação

### 1. Configure o Banco de Dados
```bash
mysql -u root -p < src/main/resources/database/schema.sql
```

### 2. Configure credenciais
Edite `src/main/resources/application.properties`

### 3. Execute
```bash
mvn clean install
mvn spring-boot:run
```

## 📚 Documentação

Acesse: `http://localhost:8080/swagger-ui.html`

---

**Status:** 🔄 Em desenvolvimento | **Última atualização:** Junho de 2026
