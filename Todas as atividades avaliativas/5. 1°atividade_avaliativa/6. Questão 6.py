import os 
os.system ("clear")

# Questão 6: Escreva um programa que leia do teclado as duas notas de um aluno, calcule e exiba a 
# média aritmética das notas. O programa deve, adicionalmente, exibir uma mensagem de parabéns caso
# o aluno esteja aprovado (média superior ou igual a 6,0), média até 4,0, o aluno está em recuperação, 
# caso a média seja inferior a 4,0 o aluno será reprovado.

no1 = int(input("Digite a sua nota: "))
no2 = int(input("Digite a sua nota: "))

media = (no1 + no2) / 2
print (f"\nMédia: {media}") 

if media >= 6:
    print ("Parabéns! Está aprovado.")
elif media >= 4:
    print ("Está na recu recu!")  
else:
    print ("Você foi reprovado!")












