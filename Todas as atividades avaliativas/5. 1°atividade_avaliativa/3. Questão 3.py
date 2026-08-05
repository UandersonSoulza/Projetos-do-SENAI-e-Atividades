import os 
os.system ("clear")

# Questão 3: Faça um algoritmo que leia dois valores inteiros A e B se os valores forem iguais deverá 
# se somar os dois, caso contrário multiplique A por B. Ao final de qualquer um dos cálculos deve-se
# atribuir o resultado para uma variável C e mostrar seu conteúdo na tela.

a = float(input("Digite um número: "))
b = float(input("Digite um número: "))

if a == b:
    c = a + b
else:
    c = a * b

print ()
print (f"Resultado: {c}")

