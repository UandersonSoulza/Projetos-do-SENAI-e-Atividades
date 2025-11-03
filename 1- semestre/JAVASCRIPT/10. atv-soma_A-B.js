const readline = require('readline-sync')

const a = readline.questionInt ('Digite o valor de A: ')

const b = readline.questionInt ('Digite o valor de B: ')

if (a == b){
    c = a + b
console.log (c)
}

if (a !== b){
    c = a * b
console.log (c)
}



