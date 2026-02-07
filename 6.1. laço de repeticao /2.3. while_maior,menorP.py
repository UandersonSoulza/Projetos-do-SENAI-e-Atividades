import os
os.system ("cls|clear")

while True:
    nota1 = float(input("Digite uma nota 1: "))

    if nota1 < 0 or nota1 > 10:
        print ("Inválido, tente novamente.")
    else:
        break
while True:
    nota2 = float(input("Digite uma nota 2: "))

    if nota2 < 0 or nota2 > 10:
        print ("Inválido, tente novamente.")
    else:
        break

media = (nota1 + nota2) / 2

print (f"\nMédia total: {media}")










