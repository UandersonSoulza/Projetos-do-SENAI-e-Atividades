
const readline = require('readline-sync')

const idade = readline.questionInt ('Digite sua idade: ')

if (idade < 16) {
    console.log ('Ainda não pode votar!')
} else if (idade == 16 || idade == 17 ) {
    console.log ('Voto opcional!')
} else if (idade == 18 ) {
    console.log ('Voto obrigatório!')
} else {
    console.log ('Não são obrigados a votar!')
}









