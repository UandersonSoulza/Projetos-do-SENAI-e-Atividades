# 🌐 Portal Primeiro Passo

## 📝 Descrição

Portal web responsivo e moderno para onboarding de usuários. Desenvolvido com tecnologias frontend puras (HTML5, CSS3, JavaScript vanilla).

## 🛠️ Stack Tecnológico

- **HTML5** - Estrutura semântica
- **CSS3** - Estilos com Flexbox e Grid
- **JavaScript ES6+** - Interatividade
- **Vercel** - Hospedagem

## 📋 Pré-requisitos

- Navegador moderno (Chrome, Firefox, Safari, Edge)
- Editor de texto (VS Code, Sublime, etc)
- Git (opcional)

## 🚀 Setup e Instalação

### 1. Clone ou baixe o projeto
```bash
git clone https://github.com/UandersonSoulza/Projetos-do-SENAI-e-Atividades.git
cd projetos/portal-primeiro-passo
```

### 2. Abra no navegador
```bash
# Opção 1: Abrir direto
open index.html

# Opção 2: Servir localmente com Python
python -m http.server 8000
# Acesse: http://localhost:8000

# Opção 3: Usar Live Server (VS Code)
# Instale a extensão "Live Server" e clique em "Go Live"
```

## 📁 Estrutura do Projeto

```
portal-primeiro-passo/
├── index.html           # Página principal
├── style.css            # Estilos globais
├── script.js            # Scripts JavaScript
├── assets/
│   ├── images/          # Imagens e ícones
│   ├── fonts/           # Fontes customizadas
│   └── icons/           # Ícones SVG
├── pages/               # Páginas adicionais (opcional)
│   ├── about.html
│   ├── contact.html
│   └── services.html
├── vercel.json          # Configuração Vercel
└── README.md
```

## 🎯 Funcionalidades

- ✅ **Design Responsivo** - Funciona em mobile, tablet e desktop
- ✅ **Animações Fluidas** - CSS3 animations e transitions
- ✅ **Formulários Validados** - Validação JavaScript do lado do cliente
- ✅ **Menu Interativo** - Navegação intuitiva
- ✅ **Hero Section** - Call-to-action destacado
- ✅ **Seções Informativas** - Conteúdo bem organizado
- ✅ **Footer Completo** - Links e informações de contato
- ✅ **Performance Otimizada** - Carregamento rápido

## 🎨 Design

### Cores Principais
- Primary: `#007AFF` (Azul)
- Secondary: `#5AC8FA` (Azul claro)
- Accent: `#FF9500` (Laranja)
- Text: `#333333` (Cinza escuro)
- Background: `#FFFFFF` (Branco)

### Tipografia
- Font: Inter, sans-serif
- Tamanhos: 16px (body), 32px (h1), 24px (h2), 20px (h3)

## 📱 Responsividade

```css
/* Mobile: < 640px */
/* Tablet: 640px - 1024px */
/* Desktop: > 1024px */
```

## 🔧 Funcionalidades JavaScript

### Menu Toggle
```javascript
// Abre/fecha menu mobile
document.querySelector('.menu-toggle').addEventListener('click', () => {
  document.querySelector('nav').classList.toggle('active');
});
```

### Validação de Formulário
```javascript
// Valida email antes de enviar
function validateEmail(email) {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}
```

### Scroll Suave
```javascript
// Scroll suave para âncoras
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
  anchor.addEventListener('click', (e) => {
    e.preventDefault();
    const target = document.querySelector(anchor.getAttribute('href'));
    target.scrollIntoView({ behavior: 'smooth' });
  });
});
```

## 🌐 Deploy com Vercel

### Método 1: Via CLI
```bash
# Instalar Vercel CLI
npm i -g vercel

# Deploy
vercel

# Deploy em produção
vercel --prod
```

### Método 2: Via GitHub
1. Push para GitHub
2. Conecte repositório no Vercel
3. Vercel faz deploy automático

## 🚀 URL em Produção

```
https://portal-primeiro-passo.vercel.app
```

## 📊 Performance

### Lighthouse Scores
- Performance: 95+
- Accessibility: 90+
- Best Practices: 90+
- SEO: 95+

### Otimizações
- Minificação de CSS/JS
- Compressão de imagens
- Lazy loading
- Cache de assets

## 🔒 Segurança

- ✅ HTTPS habilitado
- ✅ Proteção contra XSS
- ✅ CORS configurado
- ✅ Headers de segurança

## 🌍 SEO

```html
<!-- Meta tags configurados -->
<meta name="description" content="...">
<meta name="keywords" content="...">
<meta name="og:title" content="...">
<meta name="og:image" content="...">
```

## 📞 Contato

- **Email:** suporte@portalprimeiropasso.com
- **Telefone:** (XX) XXXX-XXXX
- **Redes Sociais:** [Links]

## 🤝 Contribuições

Ver [CONTRIBUTING.md](../../CONTRIBUTING.md)

## 📄 Licença

Projeto educacional - Todos os direitos reservados

---

**Status:** ✅ Completo e Deployed | **Última atualização:** Junho de 2026
