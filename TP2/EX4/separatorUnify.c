#include <ctype.h>

void separatorUnify(char str[]) {
    int i = 0;
    int j = 0;
    int in_space = 1;

    while (str[i] != '\0') {

        if (isspace((unsigned char)str[i])) { 
            if (!in_space) {
                str[j++] = ' ';  // coloca apenas um espaço
                in_space = 1;
            }
        } else {
            str[j++] = str[i]; // copiar carácter normal
            in_space = 0;
        }

        i++;
    }

    if (j > 0 && str[j - 1] == ' ') {     // remover espaço final, se existir
        j--;
    }

    str[j] = '\0';
}
