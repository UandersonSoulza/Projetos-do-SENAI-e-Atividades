import os
os.system ("cls|clear")

soma = 0
contador = 0

while True:
    nota = int(input("Digite sua nota: "))
    soma += nota
    contador += 1

    if nota < 0:
        break
    resposta = input ("Deseja digitar mais uma nota? \n Responda com 'S' ou 'N': ").lower()
    if resposta == "n":
        break

media = soma / contador

print (f"\nQuantidade de notas informadas: {contador}")
print (f"Média: {media}")
    
    










