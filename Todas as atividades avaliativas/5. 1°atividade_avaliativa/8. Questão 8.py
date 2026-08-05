import os 
os.system ("clear")

# Em uma loja de CD´s existem apenas quatro tipos de preços que estão associados a cores. Assim os 
# CD´s que ficam na loja não são marcados por preços e sim por cores. Desenvolva o algoritmo que a 
# partir da entrada da cor o software mostre o preço. A loja está atualmente com a seguinte 
# tabela de preços. 

print("""
-- TABELA --
    Cor    |  
   verde   | 
   azul    | 
  amarelo  |
 vermelho  |              
""")

cd = str(input("Escreva uma cor: "))
os.system ("clear")

match cd:
    case "verde":
        print ("O Preço da cor verde é: R$ 10,00")
    case "azul":
        print ("O Preço da cor azul é: R$ 20,00")
    case "amarelo":
        print ("O Preço da cor amarela é: R$ 30,00")
    case "vermelho":
        print ("O Preço da cor vermelho é: R$ 40,00")
    case _:
        print ("Inválido")










