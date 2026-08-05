import os 
os.system ("clear")

# Questão 2:Faça um algoritmo que leia o nome, o sexo e o estado civil de uma pessoa. 
# Caso sexo seja “F” e estado civil seja “CASADA”, solicitar o tempo de casada (anos).
# Por fim, mostre os dados do usuário.

nome = str(input("Digite seu nome: "))
sexo = str(input("Digite seu sexo (M/F): "))
estado_civil = str(input("Digite seu estado civil: "))

if sexo == "F" and estado_civil == "Casada":
    anos = str(input("Quantos anos de casamento: "))

print ()
print (f"Nome: {nome}")
print (f"Sexo: {sexo}")
print (f"Estado civil: {estado_civil}")
print (f"Tempo de casada: {anos}")
    









