// DOM pt 1
//   ex  personagem
// EVENTO pt 2
//   ex  um click do mouse
// Funcoes pt 3
//   ex  executar a ação


// isso é o dom 

const x = document.querySelector('#x')
const btneymar = document.querySelector('#btneymar')
const btmessi = document.querySelector('#btmessi')
const btcr7 = document.querySelector('#btcr7')
const btyuri = document.querySelector('#btyuri')

// isso é um evento

btneymar.addEventListener('mouseover',neymar)
btmessi.addEventListener('mouseover',messi)
btcr7.addEventListener('mouseover',cr7)
btyuri.addEventListener('mouseover',yuri)

// isso é um funçao

function neymar (){
    x.src= 'images/NEYMAR.webp'
}
function messi (){
    x.src= 'images/MESSI.webp'
}
function cr7 (){
    x.src= 'images/CR7.jpg'
}
function yuri (){
    x.src= 'images/YURI.jpg'
}