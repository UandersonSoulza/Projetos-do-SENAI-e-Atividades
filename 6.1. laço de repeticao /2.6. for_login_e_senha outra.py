import os
os.system ("cls|clear")


login = "Souza"
senha = "1234"
erros = 3
tentativas = 0
cor = 2
 
for i in range (erros):
    login1 = input("Login: ")
    senha1 = input("Senha: ")
    tentativas = erros
    
    if login == login1 and senha == senha1:
        print ("Acesso concedido")
        break
    else:
        print (f"Você tem {cor - i} tentativas.")
        
        


    
    










