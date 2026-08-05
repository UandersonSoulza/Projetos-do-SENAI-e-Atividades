import os
os.system ("cls|clear")

# Apresentar um menu com opções de pratos para o usuário escolher. 
# Perguntar ao usuário se deseja escolher outro prato após cada seleção 
# Quando o usuário decidir parar de pedir, calcular o valor total dos pedidos.
# Perguntar se o usuário deseja incluir uma gorjeta de 10% para o garçom.
# Exibir o nome e o valor dos pratos escolhidos, o valor total dos pedidos, o valor da gorjeta (se houver) e o valor final a pagar.
# termino dps


print(""" 
------------ MENU -------------
Código|\tPrato         |\tValor  
1     |\tPicanha       | 25,00  
2     |\tLasanha       | 20,00  
3     |\tStrongonoff   | 18,00  
4     |\tBIfe acebolado| 15,00  
5     |\tPão com ovo   | 5,00  
""")

gorjeta = 0


while True:
    opcao = input("Digite a opção desejada: ")

    match opcao:
        case "1":
            prato = "Picanha"
            valor = 25
            desconto = valor * 0.10
            resul = opcao * valor
            opcao2 = input("Deseja algo a mais? (S ou N): ")
            if opcao2 == "N":
                gorjeta = input("Deseja pagar os 10% para os garçons? ")
            
            if gorjeta == "N":
                break
            
            if gorjeta == "S":
                resul = (desconto + valor)
                break
            
            if opcao2 == "S":
                continue

        case "2":
            prato = "Lasanha"
            valor = 20
        case "3":
            prato = "Strongonoff"
            valor = 18
        case "4":
            prato = "Bife acebolado"
            valor = 15
        case "5":
            prato = "Pão com ovo"
            valor = 5
        case _:
            prato = "opção invalida"
            valor = print ("0")



print (f"Valor: R$ {resul}")
print (f"\nPrato: {prato}")