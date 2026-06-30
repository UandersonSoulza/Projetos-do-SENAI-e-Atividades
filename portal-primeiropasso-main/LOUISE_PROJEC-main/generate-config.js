// generate-config.js
const fs = require('fs');
const path = require('path');

const supabaseUrl = process.env.SUPABASE_URL ||
    process.env.NEXT_PUBLIC_SUPABASE_URL || '';
const supabaseKey = process.env.SUPABASE_ANON_KEY ||
    process.env.SUPABASE_KEY || '';

const targetPath = path.join(__dirname, 'public', 'config.js');

if (supabaseUrl && supabaseKey) {
    const content = `window.SUPABASE_CONFIG = {
  URL: "${supabaseUrl}",
  KEY: "${supabaseKey}"
};`;
    
    fs.writeFileSync(targetPath, content, 'utf8');
    console.log('✅ config.js gerado com variáveis de ambiente');
} else {
    console.warn('⚠️  Variáveis de ambiente SUPABASE_URL/SUPABASE_KEY não definidas.');
    console.warn('💡 Configure-as no Vercel ou use o config.js local existente.');
}
