import os
os.system ("cls || clear")
media = 0
mue = 0
mais = 0
media_sal = 0
salario = 0
sal = 0
os.system ("cls || clear")

while True:
    print("""
    ----- TABELA -----
    Código |  Descrição  
        1    |  Adicionar pessoa       
        2    |  Exibir resultados
        3    |  Sair        
    """)
    mais = input("Use a tabela acima para responder!")
    match mais:
        case "1":
            os.system ("cls || clear")
            id = input("Digite sua idade: ")
            sexo = input("Digite sua sexualidade (M/F): ")
            salario += int(input("Digite seu salário: "))
            sal += 1
            maior_valor = max (id)
            menor_valor = min (id)
        case "2":
           os.system ("cls || clear")
           print (f"Resultados ↓ \nIdade: {id}\nSexo: {sexo}\nSalário: {salario}")
           print (f"Mulheres com salário acima de 5k: {mue}")
           print (f"Idade {max} e {min}.")
           print (f"Média salarial: {media_sal}")
        case "3":
            break
        case _:
            break


if sexo == "f" and salario >= 5000:
    mue += 1

media_sal = salario / sal

print (f"Resultados ↓ \nIdade: {id}\nSexo: {sexo}\nSalário: {salario}")
print (f"Mulheres com salário acima de 5k: {mue}")
print (f"Idade {max} e {min}.")
print (f"Média salarial: {media_sal}")
























