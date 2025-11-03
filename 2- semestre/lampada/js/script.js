// isso é um dom
const lampada = document.querySelector('#lampada')
const btligar = document.querySelector('#btligar')
const btdesligar = document.querySelector('#btdesligar')

// local das imagens
const lampada_acesa = "/lampada/imagens/acesa.gif"
const lampada_apagada = "/lampada/imagens/apagada.gif"

// isso é um evento

btligar.addEventListener('click',ligar)
btdesligar.addEventListener('click',desligar)

// isso é um funçao

function ligar (){
    lampada.src= lampada_acesa
}
function desligar (){
    lampada.src= lampada_apagada
}
