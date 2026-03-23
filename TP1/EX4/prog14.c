#include <stdio.h>

#define MAX_FIELDS 10
#define MAX_LINE 256

int splitFields(char *str, char *fields[], int max);
void separatorUnify(char str[]);

int main(){
    char linha[MAX_LINE];
    char *fields[MAX_FIELDS];

    while (fgets(linha, MAX_LINE, stdin) != NULL)
    {
        int n_campos = splitFields(linha, fields, MAX_FIELDS);

        for (int i = 0; i < n_campos && i < MAX_FIELDS; i++)
        {
            separatorUnify(fields[i]);   
            printf("{%s}", fields[i]);
        }
    }

    return 0;
}
