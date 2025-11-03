// DOM 
const saudacao = document.querySelectorry('#saudacao')

const dias = document.querySelector('#dias')
const meses = document.querySelector('#meses')
const anos = document.querySelector('#anos')

const horas = document.querySelector('#horas')
const minutos = document.querySelector('#minutos')
const segundos = document.querySelector('#segundos')

// EVENTO

setInterval(relogio,1000)

// FUNCAO

function relogio(){
    hoje = new Date()

    hr = hoje.getHours()
    min = hoje.getMinutes() 
    sec = hoje.getSeconds()

    d = hoje.getDate()
    m = hoje.getMonth()+1 
    a = hoje.getFullYear()

    if(d<10){
        d= "0"+ d
    }

    if(m<10){
        m= "0"+ m
    }

    if(a<10){
        a= "0"+ a
    }

    horas.textContent = hr
    minutos.textContent = min
    segundos.textContent = sec
    dias.textContent = d
    meses.textContent = m
    anos.textContent = a

    if (hr>=5 && h<12){
        saudacao.textContent = 'Bom Dia !!'
    }
        else if (hr>=12 && h<18){
            saudacao.textContent = 'Boa Tarde !!'
        }
        else {
            saudacao.textContent = 'Boa Noite !!'
        }
}