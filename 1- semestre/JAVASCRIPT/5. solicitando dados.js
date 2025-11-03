// instalar: npm install readline-sync

const readline = require('readline-sync')

const idade = readline.questionInt('Digite sua idade: ')

if (idade < 18) {
    console.log ('Menor de idade.')
} else {
    console.log ('Maior de idade.')
}