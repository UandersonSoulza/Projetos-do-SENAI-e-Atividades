import os 
os.system ("clear")

# Questão 1:Faça um algoritmo que leia os valores A, B, C e imprima na tela se a 
# soma de A + B é menor que C, caso contrário, imprima que a A + B é maior que C. 

a = float(input("Digite um número: "))
b = float(input("Digite um número: "))
c = float(input("Digite um número: "))

if a + b < c:
    print(f"{a} + {b} é menor que {c}")
else:
    print(f"{a} + {b} é maior que {c}")










