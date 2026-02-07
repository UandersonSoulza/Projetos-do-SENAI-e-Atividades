import os
os.system ("cls|clear")


while True:
    nota1 = float(input("Digite uma nota: "))
    nota2 = float(input("Digite uma nota: "))

    media = (nota1 + nota2) / 2

    if nota1 < 0 or nota1 > 10:
        print ("Inválido, tente novamente.")
    elif nota2 < 0 or nota2 > 10:
        print ("Inválido, tente novamente.")
    else:
        break


print (f"\nMédia total: {media}")










