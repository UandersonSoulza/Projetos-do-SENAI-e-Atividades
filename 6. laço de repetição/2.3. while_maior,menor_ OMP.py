import os
os.system ("cls|clear")

soma = 0

for i in range (2):
    while True:
        nota = float(input(f"Digite a sua {i+1}ª: "))

        if nota < 0 or nota > 10:
            print ("Inválido, tente novamente.")
        else:
            soma += nota
            break

media = soma / 2

print (f"\nMédia total: {media}")










