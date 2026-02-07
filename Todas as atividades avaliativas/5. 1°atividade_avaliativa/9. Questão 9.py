import os 
os.system ("clear")

# Questão 9: Uma financeira usa o seguinte critério para conceder empréstimos: o valor total do 
# empréstimo deve ser até dez vezes o valor da renda mensal do solicitante e o valor da prestação 
# deve ser no máximo 30% da renda mensal do solicitante. Escreva um programa que leia a renda mensal
# de um solicitante, o valor total do empréstimo solicitado e o número de prestações que o 
# solicitante deseja pagar e informe se o empréstimo pode ou não ser concedido.

valor_emprestimo = float(input("Digite o valor do empréstimo solicitado: "))
renda_mensal = float(input("Digite a sua renda mensal: "))
num_prestacao = int(input("Digite o número de prestações desejadas: "))

valor_prestacao = valor_emprestimo / num_prestacao

match (valor_emprestimo <= 10 * renda_mensal, valor_prestacao <= 0.30 * renda_mensal):
    case (True, True):
        print("Empréstimo pode ser concedido.")
    case _:
        print("Empréstimo não irá ser concedido.")



