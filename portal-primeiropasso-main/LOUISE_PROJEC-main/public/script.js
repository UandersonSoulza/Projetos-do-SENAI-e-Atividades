// 1. CONFIGURAÇÃO DINÂMICA E SEGURA DO SUPABASE
let SUPABASE_URL = '';
let SUPABASE_KEY = '';

// Verifica se existe o config.js carregado com dados reais
if (window.SUPABASE_CONFIG &&
    window.SUPABASE_CONFIG.URL &&
    window.SUPABASE_CONFIG.URL !== 'SUA_URL_DO_SUPABASE_AQUI' &&
    window.SUPABASE_CONFIG.KEY &&
    window.SUPABASE_CONFIG.KEY !== 'SUA_CHAVE_ANON_PUBLICA_AQUI') {

    SUPABASE_URL = window.SUPABASE_CONFIG.URL;
    SUPABASE_KEY = window.SUPABASE_CONFIG.KEY;
} else {
    // Tenta carregar do localStorage (Configuração de contingência/produção online)
    const localUrl = localStorage.getItem('supabase_config_url');
    const localKey = localStorage.getItem('supabase_config_key');

    if (localUrl && localKey) {
        SUPABASE_URL = localUrl;
        SUPABASE_KEY = localKey;
    }
}

let supabaseClient = null;

// Se as chaves estiverem configuradas, inicializa o cliente
if (SUPABASE_URL && SUPABASE_KEY) {
    try {
        supabaseClient = window.supabase.createClient(SUPABASE_URL, SUPABASE_KEY);
    } catch (err) {
        console.error("Erro ao inicializar cliente Supabase:", err);
    }
}

// Quando a página carrega, verifica se a configuração está faltando para exibir o assistente (wizard)
window.addEventListener('DOMContentLoaded', () => {
    if (!SUPABASE_URL || !SUPABASE_KEY || !supabaseClient) {
        const modalConfig = document.getElementById('modal-config');
        if (modalConfig) {
            modalConfig.style.display = 'flex';
        }
    }
});

// Salva as chaves inseridas pelo usuário na contingência online (seguro no navegador)
window.salvarConfigWizard = () => {
    const urlInput = document.getElementById('c-url');
    const keyInput = document.getElementById('c-key');
    if (!urlInput || !keyInput) return;

    const url = urlInput.value.trim();
    const key = keyInput.value.trim();

    if (!url || !key) {
        showToast("Preencha ambos os campos para conectar.", "error");
        return;
    }

    localStorage.setItem('supabase_config_url', url);
    localStorage.setItem('supabase_config_key', key);

    showToast("Configuração salva! Conectando...", "success");

    setTimeout(() => {
        window.location.reload();
    }, 1000);
};

// Variáveis de Estado Global
let perfilSelecionado = 'aluno'; // 'aluno' | 'prof'
let usuarioLogado = null;
let abaAtual = '';
let cacheAlunos = []; // Cache local de alunos para evitar requisições repetidas

// --- FUNÇÕES DE AUXÍLIO DE INTERFACE (UI) ---

// Exibe mensagem rápida de feedback (Toast)
function showToast(message, type = 'success') {
    const toast = document.getElementById('toast-feedback');
    const toastMsg = document.getElementById('toast-message');
    const toastIcon = document.getElementById('toast-icon');
    if (!toast || !toastMsg) return;

    toastMsg.innerText = message;
    toastIcon.innerText = type === 'success' ? '✨' : '⚠️';
    toast.className = `toast-feedback show ${type}`;

    setTimeout(() => {
        toast.classList.remove('show');
    }, 4000);
}

// Alterna entre Aluno e Instrutor no Login
window.setPerfil = (tipo) => {
    perfilSelecionado = tipo;
    const btnAluno = document.getElementById('btn-aluno');
    const btnProf = document.getElementById('btn-prof');

    if (btnAluno && btnProf) {
        btnAluno.classList.toggle('active', tipo === 'aluno');
        btnProf.classList.toggle('active', tipo === 'prof');
    }
};

// Alterna a exibição das Abas na plataforma
window.irParaAba = (abaId) => {
    // 1. Oculta todas as abas
    const abas = document.querySelectorAll('.tab-content');
    abas.forEach(aba => aba.classList.remove('active'));

    // 2. Exibe a aba alvo
    const abaAlvo = document.getElementById(abaId);
    if (abaAlvo) {
        abaAlvo.classList.add('active');
        abaAtual = abaId;
    }

    // 3. Atualiza os itens de menu ativos na sidebar
    const navItems = document.querySelectorAll('.nav-item');
    navItems.forEach(item => item.classList.remove('active'));

    let navItem = null;
    if (abaId === 'tab-dashboard-aluno') navItem = document.getElementById('nav-dash-aluno');
    else if (abaId === 'tab-dashboard-prof') navItem = document.getElementById('nav-dash-prof');
    else if (abaId === 'tab-minhas-notas') navItem = document.getElementById('nav-notas-aluno');
    else if (abaId === 'tab-modulos') navItem = document.getElementById('nav-modulos-aluno');
    else if (abaId === 'tab-lancar-notas') navItem = document.getElementById('nav-lancar-notas');
    else if (abaId === 'tab-documentos-alunos') navItem = document.getElementById('nav-docs-alunos');
    else if (abaId === 'tab-alterar-senha') navItem = document.getElementById('nav-alterar-senha');

    if (navItem) {
        navItem.classList.add('active');
    }

    // 4. Carrega dinamicamente os dados daquela aba
    carregarDadosDaAba(abaId);
};

// --- LOGICA DE BANCO DE DATOS (SUPABASE) ---

// Função de Login
window.executarLogin = async () => {
    if (!supabaseClient) {
        showToast("Supabase não inicializado. Configure as chaves.", "error");
        return;
    }

    const userField = document.getElementById('l-user');
    const passField = document.getElementById('l-pass');
    const erroMsg = document.getElementById('l-error');

    const userLogin = userField.value.toLowerCase().trim();
    const userPass = passField.value.trim();

    if (!userLogin || !userPass) {
        erroMsg.innerText = "Preencha todos os campos.";
        erroMsg.style.display = 'block';
        return;
    }

    try {
        erroMsg.style.display = 'none';

        // Busca na tabela 'usuarios'
        const { data: usuario, error } = await supabaseClient
            .from('usuarios')
            .select('*')
            .eq('login', userLogin)
            .eq('senha', userPass)
            .single();

        if (error || !usuario) {
            erroMsg.innerText = "Usuário ou senha incorretos.";
            erroMsg.style.display = 'block';
            return;
        }

        // Verifica o perfil selecionado
        if (usuario.tipo !== perfilSelecionado) {
            erroMsg.innerText = `Acesso negado: Perfil ${perfilSelecionado === 'aluno' ? 'Aluno' : 'Instrutor'} necessário.`;
            erroMsg.style.display = 'block';
            return;
        }

        // Login Bem Sucedido
        usuarioLogado = usuario;
        erroMsg.style.display = 'none';
        showToast(`Bem-vindo, ${usuarioLogado.nome}!`, "success");
        abrirPlataforma();

    } catch (err) {
        console.error("Erro inesperado no login:", err);
        erroMsg.innerText = "Erro ao conectar com o servidor.";
        erroMsg.style.display = 'block';
    }
};

// Abre a plataforma de estudos
async function abrirPlataforma() {
    const telaLogin = document.getElementById('tela-login');
    const plataforma = document.getElementById('plataforma');
    const userNameDisplay = document.getElementById('user-name');
    const sideUserName = document.getElementById('side-user-name');
    const sideUserRole = document.getElementById('side-user-role');
    const avatarInitial = document.getElementById('profile-avatar-initial');

    if (telaLogin) telaLogin.style.display = 'none';
    if (plataforma) plataforma.style.display = 'grid';

    // Atualiza cabeçalhos do perfil
    if (userNameDisplay) userNameDisplay.innerText = usuarioLogado.nome;
    if (sideUserName) sideUserName.innerText = usuarioLogado.nome;
    if (avatarInitial) avatarInitial.innerText = usuarioLogado.nome.charAt(0).toUpperCase();

    // Altera a classe do body para lidar com visibilidade condicional via CSS
    document.body.className = `role-${usuarioLogado.tipo}`;

    if (sideUserRole) {
        sideUserRole.innerText = usuarioLogado.tipo === 'aluno' ? 'Aluno' : 'Instrutor';
    }

    // Configura e exibe a aba e estatísticas iniciais
    const statsAluno = document.getElementById('stats-aluno');
    const statsProf = document.getElementById('stats-prof');

    if (usuarioLogado.tipo === 'aluno') {
        if (statsAluno) statsAluno.style.display = 'grid';
        if (statsProf) statsProf.style.display = 'none';
        irParaAba('tab-dashboard-aluno');
    } else {
        if (statsAluno) statsAluno.style.display = 'none';
        if (statsProf) statsProf.style.display = 'grid';
        irParaAba('tab-dashboard-prof');
    }
}

// Direciona o carregamento de dados da aba correspondente
function carregarDadosDaAba(abaId) {
    if (!supabaseClient) return;

    if (abaId === 'tab-dashboard-aluno') {
        carregarDashboardAluno();
    } else if (abaId === 'tab-dashboard-prof') {
        carregarDashboardProf();
    } else if (abaId === 'tab-minhas-notas') {
        carregarNotasDoAluno();
    } else if (abaId === 'tab-modulos') {
        carregarModulosDoAluno();
    } else if (abaId === 'tab-lancar-notas') {
        carregarAlunosParaSelect('select-aluno-nota');
        carregarUltimasNotasProfessor();
    } else if (abaId === 'tab-documentos-alunos') {
        carregarAlunosParaSelect('select-aluno-doc');
        carregarDocumentosDosAlunos();
    }
}

// --- MÓDULO: ALUNO ---

// Estatísticas Rápidas do Dashboard do Aluno
async function carregarDashboardAluno() {
    const statMedia = document.getElementById('stat-media-aluno');
    const statNotas = document.getElementById('stat-notas-count');
    const statProx = document.getElementById('stat-prox-modulo');

    try {
        const { data: registros, error } = await supabaseClient
            .from('notas')
            .select('valor')
            .eq('usuario_id', usuarioLogado.id);

        if (error) throw error;

        if (!registros || registros.length === 0) {
            if (statMedia) statMedia.innerText = '0.0';
            if (statNotas) statNotas.innerText = '0';
        } else {
            const total = registros.reduce((acc, curr) => acc + (parseFloat(curr.valor) || 0), 0);
            const media = (total / registros.length).toFixed(1);
            if (statMedia) statMedia.innerText = media;
            if (statNotas) statNotas.innerText = registros.length;
        }

        // Busca o primeiro módulo disponível
        const { data: modulos, errorMod } = await supabaseClient
            .from('modulos')
            .select('titulo')
            .order('ordem', { ascending: true })
            .limit(1);

        if (!errorMod && modulos && modulos.length > 0) {
            if (statProx) statProx.innerText = modulos[0].titulo;
        } else {
            if (statProx) statProx.innerText = 'Primeiro Passo';
        }
    } catch (err) {
        console.error("Erro no dashboard do aluno:", err);
    }
}

// Histórico de notas do aluno
async function carregarNotasDoAluno() {
    const listContainer = document.getElementById('lista-atividades');
    const badgeCount = document.getElementById('notas-count-badge');
    if (!listContainer) return;

    listContainer.innerHTML = `
        <tr>
            <td colspan="3">
                <div class="state-container state-loading">
                    <span class="state-icon-large">⌛</span>
                    <h4 class="state-title">Carregando notas...</h4>
                    <p class="state-subtitle">Buscando avaliações no servidor.</p>
                </div>
            </td>
        </tr>
    `;

    try {
        const { data: registros, error } = await supabaseClient
            .from('notas')
            .select('*')
            .eq('usuario_id', usuarioLogado.id)
            .order('created_at', { ascending: false });

        if (error) throw error;

        listContainer.innerHTML = '';

        if (!registros || registros.length === 0) {
            listContainer.innerHTML = `
                <tr>
                    <td colspan="3">
                        <div class="state-container state-empty">
                            <span class="state-icon-large">📭</span>
                            <h4 class="state-title">Nenhuma avaliação encontrada</h4>
                            <p class="state-subtitle">Você ainda não possui notas acadêmicas lançadas.</p>
                        </div>
                    </td>
                </tr>
            `;
            if (badgeCount) badgeCount.innerText = '0 avaliações';
            return;
        }

        if (badgeCount) badgeCount.innerText = `${registros.length} ${registros.length === 1 ? 'avaliação' : 'avaliações'}`;

        registros.forEach(item => {
            const dataFormatada = new Date(item.created_at).toLocaleDateString('pt-BR');
            const valorNota = parseFloat(item.valor);
            let badgeClass = 'badge-info';

            if (!isNaN(valorNota)) {
                if (valorNota >= 7) badgeClass = 'badge-success';
                else if (valorNota >= 5) badgeClass = 'badge-warning';
                else badgeClass = 'badge-error';
            }

            // Suporte direto para estilo customizado da badge vermelha (badge-error)
            const styleError = badgeClass === 'badge-error' ? 'background: rgba(255, 90, 95, 0.12); color: var(--error); border: 1px solid rgba(255, 90, 95, 0.2);' : '';

            listContainer.innerHTML += `
                <tr>
                    <td>
                        <div style="font-weight: 600; color: white;">${item.atividade || 'Sem título'}</div>
                        <div style="font-size: 0.8rem; color: var(--text-muted);">${item.disciplina || 'Geral'}</div>
                    </td>
                    <td>
                        <span class="badge ${badgeClass}" style="${styleError}">
                            ${isNaN(valorNota) ? 'Pendente' : valorNota.toFixed(1)}
                        </span>
                    </td>
                    <td>${dataFormatada}</td>
                </tr>
            `;
        });
    } catch (err) {
        console.error("Erro ao carregar notas acadêmicas:", err);
        listContainer.innerHTML = `
            <tr>
                <td colspan="3">
                    <div class="state-container state-error">
                        <span class="state-icon-large">❌</span>
                        <h4 class="state-title">Erro ao carregar dados</h4>
                        <p class="state-subtitle">Não foi possível buscar as notas. Verifique a tabela no banco.</p>
                    </div>
                </td>
            </tr>
        `;
    }
}

// Módulos e materiais de aula
async function carregarModulosDoAluno() {
    const container = document.getElementById('lista-modulos');
    if (!container) return;

    container.innerHTML = `
        <div class="state-container state-loading">
            <span class="state-icon-large">⌛</span>
            <h4 class="state-title">Carregando módulos...</h4>
            <p class="state-subtitle">Buscando aulas e materiais integrados.</p>
        </div>
    `;

    try {
        const { data: modulos, error: errorMod } = await supabaseClient
            .from('modulos')
            .select('*')
            .order('ordem', { ascending: true });

        if (errorMod) throw errorMod;

        if (!modulos || modulos.length === 0) {
            container.innerHTML = `
                <div class="state-container state-empty">
                    <span class="state-icon-large">📖</span>
                    <h4 class="state-title">Nenhum módulo disponível</h4>
                    <p class="state-subtitle">A grade de aulas está vazia no momento.</p>
                </div>
            `;
            return;
        }

        container.innerHTML = '';

        for (const mod of modulos) {
            const { data: atividades, error: errorAtiv } = await supabaseClient
                .from('atividades')
                .select('*')
                .eq('modulo_id', mod.id)
                .order('data_disponibilizacao', { ascending: true });

            const ativCount = atividades ? atividades.length : 0;

            const card = document.createElement('div');
            card.className = 'module-card';
            card.id = `module-${mod.id}`;

            card.innerHTML = `
                <div class="module-trigger" onclick="toggleModulo('${mod.id}')">
                    <div class="module-header-info">
                        <div class="module-icon-box">📓</div>
                        <div class="module-title-desc">
                            <h4>${mod.titulo}</h4>
                            <p>${mod.descricao || 'Sem descrição detalhada'}</p>
                        </div>
                    </div>
                    <div class="module-right">
                        <span class="badge badge-info">${ativCount} ${ativCount === 1 ? 'aula' : 'aulas'}</span>
                        <span class="module-arrow">▼</span>
                    </div>
                </div>
                <div class="module-content" id="content-${mod.id}">
                    <div class="activities-list-${mod.id}"></div>
                </div>
            `;

            container.appendChild(card);

            const ativListContainer = card.querySelector(`.activities-list-${mod.id}`);
            if (!atividades || atividades.length === 0) {
                ativListContainer.innerHTML = `<p style="font-size: 0.85rem; color: var(--text-muted); padding: 10px;">Nenhuma atividade ou material registrado para este módulo.</p>`;
            } else {
                atividades.forEach(ativ => {
                    const dateFormatted = new Date(ativ.data_disponibilizacao).toLocaleDateString('pt-BR');
                    let linkMarkup = '';
                    if (ativ.material_url) {
                        linkMarkup = `
                            <a href="${ativ.material_url}" target="_blank" class="btn btn-secondary" style="padding: 6px 12px; font-size: 0.8rem; text-decoration: none;">
                                📥 Baixar Material
                            </a>
                        `;
                    } else {
                        linkMarkup = `<span style="font-size: 0.75rem; color: var(--text-muted);">Sem material digital</span>`;
                    }

                    ativListContainer.innerHTML += `
                        <div class="activity-item">
                            <div class="activity-left">
                                <span class="activity-title">${ativ.titulo}</span>
                                <span class="activity-desc">${ativ.descricao || 'Sem descrição'} • Disponibilizado em ${dateFormatted}</span>
                            </div>
                            <div class="activity-right">
                                ${linkMarkup}
                            </div>
                        </div>
                    `;
                });
            }
        }
    } catch (err) {
        console.error("Erro ao carregar cronograma acadêmico:", err);
        container.innerHTML = `
            <div class="state-container state-error">
                <span class="state-icon-large">❌</span>
                <h4 class="state-title">Erro ao carregar conteúdo</h4>
                <p class="state-subtitle">Crie a tabela 'modulos' e 'atividades' no seu banco para ativar esta funcionalidade.</p>
            </div>
        `;
    }
}

window.toggleModulo = (modId) => {
    const card = document.getElementById(`module-${modId}`);
    if (card) {
        card.classList.toggle('expanded');
    }
};

// --- MÓDULO: PROFESSOR ---

// Dashboard do Professor
async function carregarDashboardProf() {
    const statAlunos = document.getElementById('stat-total-alunos');
    const statTotalNotas = document.getElementById('stat-total-notas-lancadas');
    const statTotalDocs = document.getElementById('stat-total-docs');

    try {
        // Alunos
        const { count: countAlunos, error: errAlunos } = await supabaseClient
            .from('usuarios')
            .select('*', { count: 'exact', head: true })
            .eq('tipo', 'aluno');

        if (!errAlunos && statAlunos) statAlunos.innerText = countAlunos || 0;

        // Notas
        const { count: countNotas, error: errNotas } = await supabaseClient
            .from('notas')
            .select('*', { count: 'exact', head: true });

        if (!errNotas && statTotalNotas) statTotalNotas.innerText = countNotas || 0;

        // Documentos
        const { count: countDocs, error: errDocs } = await supabaseClient
            .from('documentos_alunos')
            .select('*', { count: 'exact', head: true });

        if (!errDocs && statTotalDocs) {
            statTotalDocs.innerText = countDocs || 0;
        } else if (statTotalDocs) {
            statTotalDocs.innerText = '0';
        }
    } catch (err) {
        console.error("Erro no dashboard do professor:", err);
    }
}

// Popula selects com a lista de alunos cadastrados
async function carregarAlunosParaSelect(selectElementId) {
    const selectEl = document.getElementById(selectElementId);
    if (!selectEl) return;

    if (cacheAlunos.length === 0) {
        try {
            const { data, error } = await supabaseClient
                .from('usuarios')
                .select('id, nome')
                .eq('tipo', 'aluno')
                .order('nome');

            if (error) throw error;
            cacheAlunos = data || [];
        } catch (err) {
            console.error("Erro ao obter lista de alunos:", err);
            showToast("Erro ao obter alunos do servidor.", "error");
            return;
        }
    }

    const defaultOption = selectEl.options[0];
    selectEl.innerHTML = '';
    selectEl.appendChild(defaultOption);

    cacheAlunos.forEach(aluno => {
        const opt = document.createElement('option');
        opt.value = aluno.id;
        opt.innerText = aluno.nome;
        selectEl.appendChild(opt);
    });
}

// Grava a nota acadêmica do aluno
window.executarSalvarNota = async () => {
    const selectAluno = document.getElementById('select-aluno-nota');
    const inputAtiv = document.getElementById('input-atividade-nota');
    const inputVal = document.getElementById('input-valor-nota');
    const inputDisc = document.getElementById('input-disciplina-nota');
    const btn = document.getElementById('btn-salvar-nota');

    if (!selectAluno.value || !inputAtiv.value || !inputVal.value) {
        showToast("Preencha todos os campos obrigatórios.", "error");
        return;
    }

    const valorNota = parseFloat(inputVal.value);
    if (isNaN(valorNota) || valorNota < 0 || valorNota > 10) {
        showToast("A nota acadêmica deve ser entre 0 e 10.", "error");
        return;
    }

    btn.disabled = true;
    btn.innerHTML = '<span>Registrando Nota...</span>';

    try {
        const notaObj = {
            usuario_id: selectAluno.value,
            atividade: inputAtiv.value.trim(),
            valor: valorNota,
            disciplina: inputDisc.value.trim() || 'Geral'
        };

        if (usuarioLogado && usuarioLogado.id) {
            notaObj.professor_id = usuarioLogado.id;
        }

        const { error } = await supabaseClient
            .from('notas')
            .insert([notaObj]);

        if (error) throw error;

        showToast("Nota lançada com sucesso!", "success");

        // Reseta o form
        inputAtiv.value = '';
        inputVal.value = '';
        inputDisc.value = '';
        selectAluno.value = '';

        carregarUltimasNotasProfessor();
        carregarDashboardProf();

    } catch (err) {
        console.error("Erro ao cadastrar nota:", err);
        showToast("Falha ao salvar nota acadêmica.", "error");
    } finally {
        btn.disabled = false;
        btn.innerHTML = '<span>Salvar Nota Acadêmica</span>';
    }
};

// Histórico recente das notas lançadas no portal
async function carregarUltimasNotasProfessor() {
    const tbody = document.getElementById('lista-ultimas-notas-prof');
    if (!tbody) return;

    tbody.innerHTML = '<tr><td colspan="5" style="text-align:center">Buscando notas recentes...</td></tr>';

    try {
        const { data: notas, error } = await supabaseClient
            .from('notas')
            .select(`
                id,
                atividade,
                valor,
                disciplina,
                created_at,
                usuario_id,
                usuarios!usuario_id (
                    nome
                )
            `)
            .order('created_at', { ascending: false })
            .limit(10);

        if (error) throw error;

        tbody.innerHTML = '';

        if (!notas || notas.length === 0) {
            tbody.innerHTML = '<tr><td colspan="5" style="text-align:center; color: var(--text-muted);">Nenhuma nota cadastrada recentemente.</td></tr>';
            return;
        }

        notas.forEach(item => {
            const dataF = new Date(item.created_at).toLocaleDateString('pt-BR');
            const nomeAluno = item.usuarios ? item.usuarios.nome : 'Aluno Acadêmico';

            tbody.innerHTML += `
                <tr>
                    <td><strong>${nomeAluno}</strong></td>
                    <td>${item.atividade || 'Sem título'}</td>
                    <td>${item.disciplina || 'Geral'}</td>
                    <td><span class="badge badge-info">${parseFloat(item.valor).toFixed(1)}</span></td>
                    <td>${dataF}</td>
                </tr>
            `;
        });
    } catch (err) {
        console.error("Erro ao buscar registros de notas:", err);
        tbody.innerHTML = '<tr><td colspan="5" style="text-align:center; color:var(--error);">Erro ao processar histórico recente.</td></tr>';
    }
}

// Grava o documento acadêmico do aluno
window.executarCadastrarDocumento = async () => {
    const selectAluno = document.getElementById('select-aluno-doc');
    const selectTipo = document.getElementById('select-tipo-doc');
    const inputUrl = document.getElementById('input-url-doc');
    const btn = document.getElementById('btn-cadastrar-doc');

    if (!selectAluno.value || !selectTipo.value || !inputUrl.value) {
        showToast("Preencha todos os campos obrigatórios.", "error");
        return;
    }

    btn.disabled = true;
    btn.innerHTML = '<span>Salvando Documento...</span>';

    try {
        const docObj = {
            aluno_id: selectAluno.value,
            tipo_documento: selectTipo.value,
            url: inputUrl.value.trim(),
            professor_id: usuarioLogado.id
        };

        const { error } = await supabaseClient
            .from('documentos_alunos')
            .insert([docObj]);

        if (error) throw error;

        showToast("Documento acadêmico cadastrado com sucesso!", "success");

        // Reseta form
        selectAluno.value = '';
        selectTipo.value = '';
        inputUrl.value = '';

        carregarDocumentosDosAlunos();
        carregarDashboardProf();

    } catch (err) {
        console.error("Erro ao registrar documento:", err);
        showToast("Tabela 'documentos_alunos' não encontrada no Supabase.", "error");
    } finally {
        btn.disabled = false;
        btn.innerHTML = '<span>Anexar e Registrar Documento</span>';
    }
};

// Histórico de documentos enviados
async function carregarDocumentosDosAlunos() {
    const tbody = document.getElementById('lista-documentos-alunos');
    if (!tbody) return;

    tbody.innerHTML = '<tr><td colspan="4" style="text-align:center">Buscando documentos dos alunos...</td></tr>';

    try {
        const { data: docs, error } = await supabaseClient
            .from('documentos_alunos')
            .select(`
                id,
                tipo_documento,
                url,
                created_at,
                usuarios!aluno_id (
                    nome
                )
            `)
            .order('created_at', { ascending: false });

        if (error) throw error;

        tbody.innerHTML = '';

        if (!docs || docs.length === 0) {
            tbody.innerHTML = '<tr><td colspan="4" style="text-align:center; color: var(--text-muted);">Nenhum documento escolar anexado.</td></tr>';
            return;
        }

        docs.forEach(item => {
            const dataF = new Date(item.created_at).toLocaleDateString('pt-BR');
            const nomeAluno = item.usuarios ? item.usuarios.nome : 'Aluno Acadêmico';

            tbody.innerHTML += `
                <tr>
                    <td><strong>${nomeAluno}</strong></td>
                    <td>${item.tipo_documento}</td>
                    <td>${dataF}</td>
                    <td>
                        <a href="${item.url}" target="_blank" class="btn-link">
                            🔗 Abrir Documento Digital
                        </a>
                    </td>
                </tr>
            `;
        });
    } catch (err) {
        console.error("Erro ao carregar documentos:", err);
        tbody.innerHTML = '<tr><td colspan="4" style="text-align:center; color:var(--error);">Tabela de documentos indisponível no banco.</td></tr>';
    }
}

// --- SEÇÃO COMPARTILHADA: SEGURANÇA ---

// Alteração de Senha Segura
window.executarAlterarSenha = async () => {
    const inputAtual = document.getElementById('input-senha-atual');
    const inputNova = document.getElementById('input-senha-nova');
    const inputConfirmar = document.getElementById('input-senha-confirmar');
    const btnSubmit = document.getElementById('btn-alterar-senha-submit');

    const senhaAtual = inputAtual.value.trim();
    const senhaNova = inputNova.value.trim();
    const senhaConfirmar = inputConfirmar.value.trim();

    if (!senhaAtual || !senhaNova || !senhaConfirmar) {
        showToast("Preencha todos os campos do formulário.", "error");
        return;
    }

    if (senhaNova.length < 6) {
        showToast("A senha nova precisa ter pelo menos 6 caracteres.", "error");
        return;
    }

    if (senhaNova !== senhaConfirmar) {
        showToast("A confirmação da nova senha está inconsistente.", "error");
        return;
    }

    btnSubmit.disabled = true;
    btnSubmit.innerHTML = '<span>Salvando Nova Senha...</span>';

    try {
        // 1. Confere a senha atual direto na tabela usuarios
        const { data: usuario, error: errSelect } = await supabaseClient
            .from('usuarios')
            .select('*')
            .eq('id', usuarioLogado.id)
            .eq('senha', senhaAtual)
            .single();

        if (errSelect || !usuario) {
            showToast("A senha atual informada está incorreta.", "error");
            return;
        }

        // 2. Realiza o update no Supabase
        const { error: errUpdate } = await supabaseClient
            .from('usuarios')
            .update({ senha: senhaNova })
            .eq('id', usuarioLogado.id);

        if (errUpdate) throw errUpdate;

        showToast("Senha de acesso alterada com sucesso!", "success");

        // Limpa formulário
        inputAtual.value = '';
        inputNova.value = '';
        inputConfirmar.value = '';

    } catch (err) {
        console.error("Erro ao alterar senha acadêmica:", err);
        showToast("Erro ao processar alteração de senha.", "error");
    } finally {
        btnSubmit.disabled = false;
        btnSubmit.innerHTML = '<span>Salvar Nova Senha</span>';
    }
};

// --- LOGOUT ---
window.logout = () => {
    window.location.reload();
};

// --- ATALHO DE DESENVOLVIMENTO (BYPASS PROTEGIDO) ---
// --- FUNÇÃO DE CRIAÇÃO DE NOVOS ALUNOS (Acesso Professor) ---
window.criarAluno = async () => {
    // Campos de formulário (IDs devem existir na UI)
    const nomeInput = document.getElementById('c-aluno-nome');
    const loginInput = document.getElementById('c-aluno-login');
    const senhaInput = document.getElementById('c-aluno-senha');
    const btn = document.getElementById('btn-criar-aluno');

    if (!nomeInput || !loginInput || !senhaInput) {
        showToast("Campos de criação de aluno não encontrados.", "error");
        return;
    }

    const nome = nomeInput.value.trim();
    const login = loginInput.value.trim();
    const senha = senhaInput.value.trim();

    if (!nome || !login || !senha) {
        showToast("Preencha todos os campos para criar o aluno.", "error");
        return;
    }

    if (senha.length < 6) {
        showToast("A senha deve ter ao menos 6 caracteres.", "error");
        return;
    }

    if (btn) {
        btn.disabled = true;
        btn.innerHTML = '<span>Criando aluno...</span>';
    }

    try {
        const { error } = await supabaseClient
            .from('usuarios')
            .insert([{ nome, login, senha, tipo: 'aluno' }]);
        if (error) throw error;
        showToast(`Aluno ${nome} criado com sucesso!`, "success");
        // Limpar campos
        nomeInput.value = '';
        loginInput.value = '';
        senhaInput.value = '';
        // Invalida cache de alunos para recarregamento futuro
        cacheAlunos = [];

        // Fecha o modal de criação de aluno
        const modal = document.getElementById('criar-aluno-section');
        if (modal) {
            modal.style.display = 'none';
        }
    } catch (err) {
        console.error("Erro ao criar aluno:", err);
        showToast("Falha ao criar aluno.", "error");
    } finally {
        if (btn) {
            btn.disabled = false;
            btn.innerHTML = '<span>Criar Aluno</span>';
        }
    }
};