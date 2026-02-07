import os
os.system ("cls|clear")

login_cadastrado = "Souza"
senha_cadastrada = "1234"

 
for i in range (3):
    login = input("Login: ")
    senha = input("Senha: ")
    
    if login == login_cadastrado and senha == senha_cadastrada:
        print ("Acesso concedido")
        break
    else:
        print ("Acesso negado. \nTente novamente\n")
        if i == 2:
            print ("Número de tentativas acima do permitido.\n")
        
        


    
    










