//DOM
const peso = document.querySelector('#peso')
const altura = document.querySelector('#altura')
const butao = document.querySelector('#butao')
const resultado = document.querySelector('#resultado')
//EVENTO

butao.addEventListener('click',imc)

// funcoes

function imc(){

    p = Number (peso.value)
    a = Number (altura.value)
    calculo = p/(a*a)

    if (calculo<18.5){
        situacao = 'Magreza'
    } else if(calculo >=18.5 && calculo < 25){
        situacao = 'Uma GOSTOSA'
    } else if (calculo >= 25 && calculo <30){
        situacao = ' Sobrepeso'
    } else {
        situacao = 'Obesidade'
    }
    
    resultado.textContent = `O seu imc é ${calculo.toFixed(1)}, você É ${situacao}`
    
}



