const readline = require('readline-sync')
const somar = (a,b) => {
   return a + b
}

const subtrair = (a,b) => a-b

const multiplicar = (a,b) => {
    return a * b
}
const dividir = (a,b) => a / b

const soma = somar(2,3)
const subtracao = subtrair(2,3)
const multiplicacao = multiplicar(2,3)
const divisao = dividir(2,3)

console.log(`Soma: ${soma}`)
console.log(`Subtração: ${subtracao}`)
console.log(`Multiplicação: ${multiplicacao}`)
console.log(`Divisão: ${divisao.toFixed(2)}`)



// function positivo_negativo(){
//     const number = readline.questionInt ('Digite o numero a verificar: ')
//     if (number >=0 ){
//         console.log(`${number} é positivo`)

//     }else{
//         console.log(`${number} é negativo`)
//     }
// }
// positivo_negativo()