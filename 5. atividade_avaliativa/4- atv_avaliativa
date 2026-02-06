import os
from time import sleep

# Alunos: Uanderson Souza e Tailon Vasconcelos

os.system("cls || clear")

valor_credito = 0
preco_total = 0
pratos_solicitados = ""
total_pagar = 0
preco_total = 0
cnt = 0
cnt1 = ""
preco = 0
formaci = 0

while True:
    print("""
    ================= MENU =================
    1 \t Picanha \t|R$ 25,00
    2 \t Lasanha \t|R$ 20,00
    3 \t Strogonoff \t|R$ 18,00
    4 \t Bife acebolado |R$ 15,00
    5 \t Pão com ovo \t|R$ 5,00
    6 \t Feijoada \t|R$ 15,00
    7 \t Carbonara  \t|R$ 28,00
    0 \t Fechar menu \t|
    """)

    while True: 
        opcao = input("Digite o número da opção desejada: ")
        
        match opcao:
            case "1":
                cnt = "1"
                prato = "Picanha"
                preco = 25
            case "2":
                cnt = "2"
                prato = "Lasanha"
                preco = 20
            case "3":
                cnt = "3"
                prato = "Strogonoff"
                preco = 18
            case "4":
                cnt = "4"
                prato = "Bife acebolado"
                preco = 15
            case "5":
                cnt = "5"
                prato = "Pão com ovo"
                preco = 5
            case "6":
                cnt = "6"
                prato = "Feijoada"
                preco = 15
            case "7":
                cnt = "7"
                prato = "Carbonara"
                preco = 28
            case "0":
                os.system("cls || clear")
                print("\n=== Nota Fiscal ===")
                print(f"↓ Pedidos ↓ \n Código | {cnt1}\n Prato  | {pratos_solicitados}\n ----------------- \n↓ Valores cobrados e Descontos ↓")
                print(f" Subtotal: R$ {preco_total}")
                print(f" Forma de pagamento: {formaci}")
                print(f" Valor do desconto: R$ {valor_credito}")
                print(f" Valor total da compra: R$ {total_pagar}")
                exit()
            case _:
                print("Código inválido! Digite um número de 1 a 7.")
                continue  
        
        break  

    print("ADICIONANDO...")
    sleep(2)

    preco_total += preco
    pratos_solicitados += " | " + prato if pratos_solicitados else prato
    cnt1 += " | " + cnt if cnt1 else cnt

    os.system("cls || clear")
    mais_pedidos = input("Deseja fazer um novo pedido?\nUse 'S' ou 'N' para responder: ").lower()

    if mais_pedidos == "n":
        break 

if preco_total > 0:
    os.system("cls || clear")
    forma_pagamento = input("Qual a forma de pagamento? \n 1 para À vista \n 2 para Crédito \n ->  ").lower()
    if forma_pagamento == "2":
        formaci = "Crédito"
        valor_credito = preco_total * 0.10
    elif forma_pagamento == "1":
        formaci = "À vista"
        valor_credito = - (preco_total * 0.10) 

total_pagar = preco_total + valor_credito

os.system("cls || clear")
print("\n=== Nota Fiscal ===")
print(f"↓ Pedidos ↓ \n Código | {cnt1}\n Prato  | {pratos_solicitados}\n ----------------- \n↓ Valores cobrados e Descontos ↓")
print(f" Subtotal: R$ {preco_total:.2f}")
print(f" Forma de pagamento: {formaci}")
print(f" Valor do desconto/acréscimo: R$ {valor_credito:.2f}")
print(f" Valor total da compra: R$ {total_pagar:.2f}")
