import os 
os.system ("clear")

# Um posto está vendendo combustíveis com a seguinte tabela de descontos: Escreva um algoritmo que 
# leia o número de litros vendidos e o tipo de combustível (codificado da seguinte forma: A-álcool, 
# G-gasolina), calcule e imprima o valor a ser pago pelo cliente sabendo-se que o preço do litro da 
# gasolina é R$ 6,59 e o preço do litro do álcool é R$ 3,79.

preco_alcool = 3.79
preco_gasolina = 6.59
litros = float(input("Digite a quantidade de litros vendidos: "))

print(""""
(A-álcool, G-gasolina)
""")

combustivel = input("Digite o tipo de combustível: ")

if combustivel == 'A':
    desconto = 0.02 if litros <= 25 else 0.04
    preco_litro = preco_alcool
elif combustivel == 'G':
    desconto = 0.03 if litros <= 25 else 0.05
    preco_litro = preco_gasolina
else:
    print("Tipo de combustível inválido.")
    
valor_total = litros * preco_litro * (1 - desconto)
print(f"Valor a ser pago: R$ {valor_total:.2f}")
