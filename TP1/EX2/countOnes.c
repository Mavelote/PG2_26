#include <limits.h>

int countOnes( unsigned value, int *high) {
    int count = 0;
    int highest = -1;
    unsigned int totalBits = sizeof (unsigned int) * CHAR_BIT;
    

    for (unsigned i = 0; i < totalBits; ++i) {
        if (((value >> i) & 1u) != 0u) {
            ++count;
            highest = (int)i;
        }
    }

    if (count > 0 && high != 0) {
        *high = highest;
    }

    return count;
}
