#include <stdio.h>

int countOnes(unsigned value, int *high);

int main(void) {
    unsigned value;
    int high;
    int ones;
 
    while (scanf("%u", &value) == 1) {
        ones = countOnes(value, &high);
        printf("%u %d %d\n", value, ones, high);
    }

    return 0;
}
