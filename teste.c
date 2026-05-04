#include <stdio.h>

int main() {
    int qtdwindowserver = 0;
    int qtdunix = 0;
    int qtdlinux = 0;
    int qtdnetware = 0;
    int qtdmacos = 0;
    int qtdoutro = 0;
    int voto = -1;
    int cont = 0;

    float per_windowserver, per_unix, per_linux, per_netware, per_macos, per_outro;

    while (voto != 0) {
        printf("\nPesquisa: Qual o melhor SO para servidores?\n");
        printf("1 - Windows Server\n");
        printf("2 - Unix\n");
        printf("3 - Linux\n");
        printf("4 - Netware\n");
        printf("5 - MacOs\n");
        printf("6 - Outro\n");
        printf("0 - Encerrar Votacao\n");

        scanf("%d", &voto);

        switch (voto) {
            case 1:
                qtdwindowserver++;
                cont++;
                break;
            case 2:
                qtdunix++;
                cont++;
                break;
            case 3:
                qtdlinux++;
                cont++;
                break;
            case 4:
                qtdnetware++;
                cont++;
                break;
            case 5:
                qtdmacos++;
                cont++;
                break;
            case 6:
                qtdoutro++;
                cont++;
                break;
            case 0:
                break;
            default:
                printf("Opcao invalida!\n");
        }
    }

    if (cont > 0) {
        per_windowserver = (float)qtdwindowserver / cont * 100;
        per_unix = (float)qtdunix / cont * 100;
        per_linux = (float)qtdlinux / cont * 100;
        per_netware = (float)qtdnetware / cont * 100;
        per_macos = (float)qtdmacos / cont * 100;
        per_outro = (float)qtdoutro / cont * 100;

        printf("\n*** Sistema Operacional\tVotos\tPercentual%% ***\n");
        printf("Windows Server\t\t%d\t%.2f%%\n", qtdwindowserver, per_windowserver);
        printf("Unix\t\t\t%d\t%.2f%%\n", qtdunix, per_unix);
        printf("Linux\t\t\t%d\t%.2f%%\n", qtdlinux, per_linux);
        printf("Netware\t\t\t%d\t%.2f%%\n", qtdnetware, per_netware);
        printf("MacOs\t\t\t%d\t%.2f%%\n", qtdmacos, per_macos);
        printf("Outro\t\t\t%d\t%.2f%%\n", qtdoutro, per_outro);
    } else {
        printf("\nNenhum voto foi computado.\n");
    }

    return 0;
}