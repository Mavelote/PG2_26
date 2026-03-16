# include <stdio.h>


int countOnes(unsigned value, int *high);

// Função que calcula a paridade
int calcParity(unsigned value) {
    int high;                            // variável necessária para countOnes, mas não usada aqui
    int ones = countOnes(value, &high);
    return ones % 2;                     // 0 se par, 1 se ímpar
}

