const readline = require('readline-sync')

const notas = []

const nota1 = readline.questionFloat('Digite a primeira nota');
notas.push(nota1)

const nota2 = readline.questionFloat('Digite a segunda nota');
notas.push(nota2)

const nota3 = readline.questionFloat('Digite a terceira nota');
notas.push(nota3)

console.log(notas)
const soma = notas.reduce((total, nota) => total + nota, 0);
const media = soma / notas.length;
console.log (` A media é ${media}`)