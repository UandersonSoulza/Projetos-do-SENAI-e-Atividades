import os 
os.system ("clear")

# Questão 4: Uma fruteira está vendendo frutas com a seguinte tabela de preços:
# Se o cliente comprar a partir de 10 Kg em frutas ou o valor total da compra ultrapassar R$ 15,00, 
# receberá ainda um desconto de 10% sobre este total. Escreva um algoritmo para ler a quantidade (em Kg) 
# de morangos e a quantidade (em Kg) de maçãs adquiridas e escreva o valor a ser pago pelo cliente.

print("""
------------- MENU -------------
Frutas    até 5 kg    acima 5 kg
Maçã        2,50         2,20
Morango     1,80         1,50
""")

morango_kg = float(input("Digite a quantidade de morangos: "))
maca_kg = float(input("Digite a quantidade de maçãs: "))

if morango_kg <= 5:
    preco_morango = 2.50
else:
    preco_morango = 2.20

if maca_kg <= 5:
    preco_maca = 1.80
else:
    preco_maca = 1.50

valor_morango = morango_kg * preco_morango
valor_maca = maca_kg * preco_maca
valor_total = valor_morango + valor_maca

peso_total = morango_kg + maca_kg

if peso_total > 10 or valor_total > 15:
    valor_total *= 0.90 

print(f"Valor das maçãs: {valor_maca}")
print(f"Valor dos morangos: {valor_morango:.2f}")
print(f"Peso total: {peso_total}")
print(f"Valor total a pagar: {valor_total:.2f}")







