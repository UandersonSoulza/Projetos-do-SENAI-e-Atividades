import os
os.system("cls || clear")

soma = 0
media = 0


for i in range(3):
    nota = float(input("Digite um número: "))
    soma += nota


media = soma / 3


if media >= 7:
    print("Parabéns! Está aprovado.")
elif media > 4:
    print("Você está de recuperação!")
else:
    print("Você foi reprovado!")


print(f"Média: {media}")








