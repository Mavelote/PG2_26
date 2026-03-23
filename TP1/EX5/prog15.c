#include <stdio.h>

int strcmp_ic(const char *str1, const char *str2 );

int main (int argc, char *argv[]){
    char word [256];

    if (argc != 2) {
        printf("Uso: prog15 <nome>\n");
        return -1;
    }
    while (scanf("%255s", word) == 1){
    int compar = strcmp_ic( word, argv[1]);
    printf("%s: %d\n", word, compar);
    }
    return 0;
    
}