import os 
os.system ("clear")

# Questão 7:Faça um algoritmo para ler: a descrição do produto (nome), a quantidade adquirida e o 
# preço unitário. Calcular e escrever o total (total = quantidade adquirida * preço unitário), o 
# desconto e o total a pagar (total a pagar = total - desconto), sabendo-se que: - Se quantidade 
# <= 5 o desconto será de 2% - Se quantidade > 5 e quantidade <= 10 o desconto será de 3% - 
# Sequantidade > 10 o desconto será de 5%.

nome_produto = input("Digite o nome do produto: ")
quantidade = int(input("Digite a quantidade adquirida: "))
preco_unitario = float(input("Digite o preço unitário: R$ "))

total = quantidade * preco_unitario

if quantidade <= 5:
    desconto = total * 0.02  
elif quantidade <= 10:
    desconto = total * 0.03  
else:
    desconto = total * 0.05  

total_a_pagar = total - desconto

print(f"\nProduto: {nome_produto}")
print(f"Quantidade adquirida: {quantidade}")
print(f"Preço unitário: {preco_unitario:.2f}")
print(f"Total: {total:.2f}")
print(f"Desconto: {desconto:.2f}")
print(f"Total a pagar: {total_a_pagar:.2f}")










