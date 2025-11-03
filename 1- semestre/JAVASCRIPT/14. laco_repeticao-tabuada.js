const readline = require('readline-sync')

const nu = readline.questionInt ('Digite um numero: ')

for (let i = nu; i <= 10 ; i++){
    console.log(i)
}
