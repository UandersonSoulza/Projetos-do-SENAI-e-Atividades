# 🤝 Guia de Contribuição

Obrigado por se interessar em contribuir com este repositório!

## 📋 Como Contribuir

### 1. Reportar Bugs
Se você encontrou um bug, abra uma **issue** com:
- Descrição clara do problema
- Passos para reproduzir
- Comportamento esperado vs. observado
- Screenshots (se aplicável)
- Ambiente (SO, versão Java, etc)

### 2. Sugerir Melhorias
Para sugerir novas funcionalidades:
- Descreva a funcionalidade desejada
- Explique o caso de uso
- Apresente exemplos de como seria usado

### 3. Enviar Pull Requests

1. **Fork** o repositório
2. Crie uma **branch** para sua feature
   ```bash
   git checkout -b feature/descricao-feature
   ```
3. **Commit** suas mudanças
   ```bash
   git commit -m "tipo: descrição clara da mudança"
   ```
4. **Push** para a branch
   ```bash
   git push origin feature/descricao-feature
   ```
5. Abra um **Pull Request**

---

## 📝 Padrões de Código

### Nomes e Convenções
- **Variáveis e funções:** camelCase em inglês
- **Classes:** PascalCase em inglês
- **Constantes:** UPPER_SNAKE_CASE
- **Comentários:** Português explicativo

### Formatação
- **Indentação:** 2-4 espaços (sem tabs)
- **Máximo de caracteres por linha:** 80-100
- **Quebra de linhas:** Use com moderação
- **Espaço em branco:** Limpo e consistente

### Exemplo de Código Bem Formatado
```java
public class UserService {
    // Busca usuário por ID
    public User findUserById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID deve ser positivo");
        }
        return userRepository.findById(id);
    }
}
```

---

## 📚 Padrão de Commits

Usamos o padrão **Conventional Commits**:

```
tipo(escopo): descrição breve

Descrição mais detalhada se necessário.
Pode ser múltiplas linhas.

[BREAKING CHANGE: descrição]
[Closes #123]
```

### Tipos de Commit
- **feat:** Nova funcionalidade
- **fix:** Correção de bug
- **docs:** Mudanças em documentação
- **style:** Formatação de código
- **refactor:** Refatoração sem mudança de funcionalidade
- **perf:** Melhorias de performance
- **test:** Adição ou atualização de testes
- **chore:** Mudanças em build, dependências, etc

### Exemplos
```bash
# Boa prática
git commit -m "feat(controle-estoque): adicionar função de relatório"
git commit -m "fix(auth): corrigir validação de senha"
git commit -m "docs: atualizar README com instruções de setup"

# Evite
git commit -m "update"
git commit -m "fix bug"
git commit -m "changes"
```

---

## ✅ Checklist Antes de Enviar

Antes de fazer um Pull Request, verifique:

- [ ] **Código testado** - Funcionando localmente
- [ ] **Sem erros** - Sem console.log ou debug statements
- [ ] **Sem imports inúteis** - Remove unused imports
- [ ] **Formatação** - Código bem indentado e formatado
- [ ] **Comentários** - Código bem documentado
- [ ] **Mensagem de commit** - Clara e descritiva
- [ ] **Sem arquivos desnecessários** - Apenas mudanças relevantes
- [ ] **README atualizado** - Se aplicável
- [ ] **Nenhuma dependência quebrada** - Código compatível

---

## 🔄 Processo de Review

1. Você faz o PR
2. Revisão automática (linters, builds)
3. Revisão humana de código
4. Discussão de mudanças (se necessário)
5. Aprovação e merge

---

## 🎓 Recursos Úteis

- [Semantic Versioning](https://semver.org/)
- [Conventional Commits](https://www.conventionalcommits.org/)
- [GitHub Flow](https://guides.github.com/introduction/flow/)
- [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)

---

## ❓ Dúvidas?

Se tiver dúvidas sobre como contribuir:
1. Abra uma **issue** com tag `question`
2. Envie um email
3. Verifique as issues/discussions existentes

---

**Obrigado por contribuir! 🙏**