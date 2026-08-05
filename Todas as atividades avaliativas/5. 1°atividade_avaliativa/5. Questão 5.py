import os 
os.system ("clear")

# Questão 5: Faça um programa que leia um código de operação (+,-,* ou /), e também dois valores inteiros A e B. 
# O programa deve calcular o resultado da operação escolhida aplicado a A e B. 
# Por exemplo, se a operação escolhida foi * e A = 1 e B = 3, 
# o programa deve fornecer como resultado o valor de 1*3, que é 3.

op = (input("Digite a operação:(+ - * /) "))
a = int(input("Digite o primeiro número: "))
b = int(input("Digite o segundo número: "))

match op:
    case "+":
        print(f"Resultado: {a + b}")
    case "-":
        print(f"Resultado: {a - b}")
    case "/":
        print(f"Resultado: {a / b}")
    case "*":
        print(f"Resultado: {a * b}")
    case _:
        print("inválido!")





