#include <stdio.h>

int countOnes(unsigned value, int *high);

int main(void) {
    unsigned value;
    int high;
    int ones;
 
    while (scanf("%u", &value) == 1) {
        ones = countOnes(value, &high);
        printf("Valor: %u Número de bits a 1: %d Posição do bit mais alto: %d\n", value, ones, high);
    }

    return 0;
}
