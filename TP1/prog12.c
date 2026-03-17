#include <stdio.h>

int calcParity(unsigned value);

int main(void) {
    unsigned value;
    printf("Digite os números para verificar a paridade dos bits:\n");
    
    while (scanf("%u", &value) == 1) {
        printf("Valor: %u Paridade dos bits: %d\n", value, calcParity(value));
    }
    
    return 0;
}
