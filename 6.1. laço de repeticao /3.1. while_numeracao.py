import os
os.system ("cls || clear")

# quantidade de numeros pares e ímpares,
# média de valores pares
# media geral dos numeros lidos 
par = 0
impar = 0
par1 = 0
impar1 = 0
qnt_geral = 0

while True:
    nu = int(input("Digite um número: "))
    if nu == 0:
        break
    if nu % 2 == 0:
        print (f"O número {nu} é par.")
        par1 += nu
        par += 1

    if nu % 2 == 1:
        print (f"O número {nu} é ímpar.")
        impar += 1

media = par1 / par
mida = impar + par
qnt_geral += 1

media = mida / qnt_geral 
print (f"\nPares: {par} e Ìmpares: {impar}")
print (f"\nMédia: {media}")
print (f"\nMédia Geral: {media}")
        






























