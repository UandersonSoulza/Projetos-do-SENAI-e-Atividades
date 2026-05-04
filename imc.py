import customtkinter as ctk

ctk.set_appearance_mode("light")
ctk.set_default_color_theme("blue")

def calcular():
    try:
            p = float(entry_peso.get().replace(',', '.'))
                    a = float(entry_altura.get().replace(',', '.'))
                            res = p / (a ** 2)
                                    label_resultado.configure(text=f"{res:.2f}", text_color="#1e4e9d")
                                        except:
                                                label_resultado.configure(text="Erro", text_color="red")

                                                janela = ctk.CTk()
                                                janela.title("Calculadora de IMC")
                                                janela.geometry("350x450")
                                                janela.configure(fg_color="#f8f9fa")

                                                ctk.CTkLabel(janela, text="Calculadora de IMC", font=("Arial", 22, "bold")).pack(pady=20)

                                                ctk.CTkLabel(janela, text="Peso (kg)", font=("Arial", 12)).pack(anchor="w", padx=40)
                                                entry_peso = ctk.CTkEntry(janela, placeholder_text="70.5", width=270)
                                                entry_peso.pack(pady=5)

                                                ctk.CTkLabel(janela, text="Altura (m)", font=("Arial", 12)).pack(anchor="w", padx=40)
                                                entry_altura = ctk.CTkEntry(janela, placeholder_text="1.75", width=270)
                                                entry_altura.pack(pady=5)

                                                btn = ctk.CTkButton(janela, text="Calcular", command=calcular, font=("Arial", 14, "bold"), height=40)
                                                btn.pack(pady=30, padx=40, fill="x")

                                                ctk.CTkLabel(janela, text="Seu IMC", font=("Arial", 12, "bold")).pack()
                                                label_resultado = ctk.CTkLabel(janela, text="--", font=("Arial", 40, "bold"))
                                                label_resultado.pack()

                                                janela.mainloop()
                                                