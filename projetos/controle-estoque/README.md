# 📦 Controle de Estoque - Sistema Java

## 📝 Descrição

Sistema completo de gerenciamento de estoque com interface web (Spring Boot) e banco de dados relacional. Implementa funcionalidades de CRUD de produtos, controle de quantidade e relatórios.

## 🛠️ Stack Tecnológico

- **Backend:** Java 22 + Spring Boot 3.x
- **Frontend:** HTML5, CSS3, JavaScript (Thymeleaf)
- **Banco de Dados:** MySQL 5.7+
- **Build Tool:** Maven 3.9+
- **Hospedagem:** Local/Cloud (AWS/Azure)

## 📋 Pré-requisitos

- Java 22 ou superior
- Maven 3.9+
- MySQL 5.7+
- Git

## 🚀 Setup e Instalação

### 1. Clone o repositório
```bash
git clone https://github.com/UandersonSoulza/Projetos-do-SENAI-e-Atividades.git
cd projetos/controle-estoque
```

### 2. Configure o Banco de Dados
```bash
# Criar banco de dados
mysql -u root -p < src/main/resources/database/schema.sql

# Importar dados de exemplo (opcional)
mysql -u root -p < src/main/resources/database/seed.sql
```

### 3. Configure as credenciais

Edite `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/controle_estoque
spring.datasource.username=root
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```

### 4. Execute a aplicação
```bash
mvn clean install
mvn spring-boot:run
```

### 5. Acesse a aplicação
```
http://localhost:8080
```

## 📚 Estrutura do Projeto

```
controle-estoque/
├── src/
│   ├── main/
│   │   ├── java/com/example/
│   │   │   ├── controller/      # Controllers REST
│   │   │   ├── service/         # Lógica de negócio
│   │   │   ├── repository/      # Acesso a dados
│   │   │   ├── model/           # Entidades JPA
│   │   │   └── Application.java # Main
│   │   └── resources/
│   │       ├── templates/       # HTML Thymeleaf
│   │       ├── static/          # CSS, JS, imagens
│   │       ├── database/        # Scripts SQL
│   │       └── application.properties
│   └── test/                    # Testes unitários
├── pom.xml                      # Dependências Maven
└── README.md
```

## 🔌 API Endpoints

### Produtos
```bash
# Listar todos
GET /api/produtos

# Buscar por ID
GET /api/produtos/{id}

# Criar novo
POST /api/produtos
Body: { "nome": "Produto", "preco": 100.00, "quantidade": 50 }

# Atualizar
PUT /api/produtos/{id}
Body: { "nome": "Produto", "preco": 110.00, "quantidade": 45 }

# Deletar
DELETE /api/produtos/{id}
```

## 🎯 Funcionalidades

- ✅ **CRUD Completo** - Create, Read, Update, Delete de produtos
- ✅ **Controle de Quantidade** - Atualização em tempo real
- ✅ **Categorização** - Organizar produtos por categoria
- ✅ **Histórico** - Log de todas as transações
- ✅ **Relatórios** - Gerar relatórios em PDF/Excel
- ✅ **Busca Avançada** - Filtrar por nome, categoria, preço
- ✅ **Autenticação** - Controle de acesso (user/admin)
- ✅ **API REST** - Integração com sistemas externos

## 🧪 Testes

```bash
# Executar todos os testes
mvn test

# Testes de integração
mvn verify

# Cobertura de testes
mvn jacoco:report
```

## 📊 Banco de Dados

Diagrama ER disponível em `../../docs/banco-dados/`

### Tabelas Principais
- `produtos` - Produtos cadastrados
- `categorias` - Categorias de produtos
- `movimentacoes` - Histórico de entradas/saídas
- `usuarios` - Usuários do sistema

## 🚀 Deploy

### Usando Docker
```bash
docker build -t controle-estoque .
docker run -p 8080:8080 --name estoque controle-estoque
```

### AWS
```bash
# Configure AWS CLI
aws configure

# Deploy Elastic Beanstalk
eb init
eb create
eb deploy
```

## 📚 Documentação Adicional

- [Swagger/OpenAPI](http://localhost:8080/swagger-ui.html)
- [JavaDoc](./javadoc/)
- [Arquitetura](./docs/ARCHITECTURE.md)

## 🐛 Troubleshooting

### Erro de conexão com MySQL
```bash
# Verificar se MySQL está rodando
mysql -u root -p

# Criar banco se não existir
CREATE DATABASE controle_estoque;
```

### Porta 8080 já em uso
```bash
# Usar outra porta
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
```

## 🤝 Contribuições

Ver [CONTRIBUTING.md](../../CONTRIBUTING.md)

## 📄 Licença

Projeto educacional - Todos os direitos reservados

---

**Status:** ✅ Completo | **Última atualização:** Junho de 2026
