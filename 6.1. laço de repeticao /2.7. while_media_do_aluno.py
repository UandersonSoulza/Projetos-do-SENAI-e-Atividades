import os
os.system ("cls|clear")

QN = 3
soma = 0

for i in range (QN):
    while True:
        nota = float(input(f"Digite a sua {i+1}ª: "))

        if nota < 0 or nota > 10:
            print ("Inválido, tente novamente.")
        else:
            soma += nota
            break
        os.system ("cls|clear")


media = soma / (QN)

if media >= 7:
    print ("Parabens! Você foi aprovado.")
elif media >= 5:
    print ("Você ficou em recu recu.")
else:
    print ("Reprovado.")

print (f"\nMédia total: {media}")



    
    










