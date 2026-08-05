import os
os.system ("cls|clear")

nota1 = False
nota2 = False

# Não concluido

while not nota1 or nota2:
    nota1 = float(input("Digite uma nota: "))
    nota2 = float(input("Digite uma nota: "))

    media = (nota1 + nota2) / 2

    if nota1 < 0 or nota1 > 10:
        print ("Inválido, tente novamente.")
    elif nota2 < 0 or nota2 > 10:
        print ("Inválido, tente novamente.")
    else:
        nota1 = True
        nota2 = True
    

print (f"\nMédia total: {media}")










