// Laço de repetição: While

const readline = require('readline-sync')

let nu = 0
while (  nu < 0 || nu  > 10) {
    console.log(nu) 
    const nu = readline.questionInt ('Digite um numero: ')
}





