import time
import os
os.system ("cls || clear")

contador = 0
soma_salario = 0
maior_idade = 0
menor_idade = 9999
idade = 0
while True:
    print("""
    ----- TABELA -----
    Código |  Descrição  
        1    |  Adicionar pessoa       
        2    |  Exibir resultados
        3    |  Sair        
    """)
    opcao = int(input("Digite a opção desejada: "))
     
    match opcao:
        case 1:
            idade = int(input("Digite a opção desejada: "))
            sexo = int(input("Digite a opção desejada: "))
            salario = float(input("Digite a opção desejada: "))
            contrador += 1
            soma_salario += salario
        
            if idade > maior_idade:
                maior_idade = idade
            if idade < menor_idade:
                menor_idade = idade
            
            if sexo == "F" and salario >= 5000:
                mulheres5k += 1
                
            os.system ("cls || clear")
        case 2:
            if contador > 0:
                media_salario_grupo = soma_salario / contador
                print ("\n= Exibindo resultados = ")
                print (f"Média de salário do grupo: {media_salario_grupo}")
                print (f"Maior idade do grupo: {maior_idade}")
                print (f"Menor idade do grupo: {menor_idade}")
                print (f"Quantidades mulheres com salário a partir de 5 mil: {mulheres5k}")
            else:
                print ("Não ecistem dados exibir.")
            
            time.sleep(3)
            os.system ("cls || clear")
        case 3:
            print ("\n Fim do programa.") 
            break
        case _:
            print ("\nOpção inválida.") 
            time.sleep(3)
            os.system ("cls || clear")


    























