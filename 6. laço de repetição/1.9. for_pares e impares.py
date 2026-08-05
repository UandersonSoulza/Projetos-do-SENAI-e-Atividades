import os
os.system ("cls || clear")

par = 0
impar = 0

for i in range (5):
        nu = int(input("Digite um número: "))
        if nu % 2 == 0:
                par += 1
        else:
                impar += 1

        
print (f"\nPares: {par}")
print (f"Ìmpares: {impar}")











