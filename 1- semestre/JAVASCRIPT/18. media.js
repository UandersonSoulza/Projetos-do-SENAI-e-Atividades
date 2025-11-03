const readline = require('readline-sync')

function entrada(){
const num1 = readline.questionFloat('Digite o primeiro numero')
const num2 = readline.questionFloat('Digite o segundo numero')
return [num1, num2];
}

function media(){
    media = (num1 + num2) / 2
    console.log (`A media é ${media}`)

}

const[num1,num2] = entrada();
media(num1,num2)